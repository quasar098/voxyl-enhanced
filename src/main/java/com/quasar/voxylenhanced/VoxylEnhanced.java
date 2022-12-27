package com.quasar.voxylenhanced;

import com.google.common.collect.Lists;
import com.quasar.voxylenhanced.autogg.VoxylAutoGG;
import com.quasar.voxylenhanced.hidemessages.VoxylHideMessages;
import com.quasar.voxylenhanced.misc.VoxylMisc;
import com.quasar.voxylenhanced.obstacles.VoxylObstacles;
import com.quasar.voxylenhanced.obstacles.VoxylObstaclesSegments;
import com.quasar.voxylenhanced.statsviewer.VoxylStatsViewer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;
import java.util.List;

@Mod(modid = VoxylEnhanced.MODID, version = VoxylEnhanced.VERSION)
public class VoxylEnhanced
{
    public static final String MODID = "voxylenhanced";
    public static final String VERSION = "0.4.0";

    public static boolean willOpenSettings = false;

    public static VoxylSettingsPage settings;

    static List<VoxylFeature> listeners = Lists.newArrayList(
            new VoxylObstacles(),
            new VoxylAutoGG(),
            new VoxylStatsViewer(),
            new VoxylHideMessages(),
            new VoxylMisc()
    );
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        VoxylInputHandler.register();
        VoxylObstaclesSegments.init();
        MinecraftForge.EVENT_BUS.register(new VoxylInputHandler());

        for (VoxylFeature listener : listeners) {
            MinecraftForge.EVENT_BUS.register(listener);
        }

        MinecraftForge.EVENT_BUS.register(new VoxylEnhancedMainListener());
        ClientCommandHandler.instance.registerCommand(new VoxylEnhancedCommand());
    }

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        settings = new VoxylSettingsPage(new File("./config/voxylenhanced.toml"));
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    public class VoxylEnhancedMainListener {
        @SubscribeEvent
        public void worldLoad(WorldEvent.Load event) {
            for (VoxylFeature listener : listeners) {
                listener.reset();
            }
        }

        @SubscribeEvent
        public void renderTick(TickEvent.RenderTickEvent e) {
            if (willOpenSettings) {
                Minecraft.getMinecraft().displayGuiScreen(settings.gui());
                willOpenSettings = false;
            }
        }
    }
}
