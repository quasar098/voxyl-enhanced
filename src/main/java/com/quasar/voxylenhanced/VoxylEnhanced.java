package com.quasar.voxylenhanced;

import com.google.common.collect.Lists;
import com.quasar.voxylenhanced.autogg.VoxylAutoGG;
import com.quasar.voxylenhanced.obstacles.VoxylObstacles;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.util.List;

@Mod(modid = VoxylEnhanced.MODID, version = VoxylEnhanced.VERSION)
public class VoxylEnhanced
{
    public static final String MODID = "voxylenhanced";
    public static final String VERSION = "1.3";

    public static String apiKey = "missing";

    static List<VoxylFeature> listeners = Lists.newArrayList(
            new VoxylObstacles(),
            new VoxylAutoGG()
    );
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        for (VoxylFeature listener : listeners) {
            MinecraftForge.EVENT_BUS.register(listener);
        }

        ClientCommandHandler.instance.registerCommand(new VoxylEnhancedCommand());
    }

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        File configFile = new File(Loader.instance().getConfigDir(), "voxylenhanced.cfg");
        config = new Configuration(configFile);
        configurate(true);
    }

    private static Configuration config = null;

    public static void configurate(boolean loadFromFile) {
        if (loadFromFile) {
            config.load();
        }

        Property apiKeyProp = config.get(Configuration.CATEGORY_CLIENT, "apiKey", "missing", "api key of bwp");

        if (loadFromFile) {
            apiKey = apiKeyProp.getString();
        } else {
            apiKeyProp.set(apiKey);
        }

        for (VoxylFeature listener : listeners) {
            listener.configurate(config, loadFromFile);
        }

        if (config.hasChanged()) {
            config.save();
        }
    }
}
