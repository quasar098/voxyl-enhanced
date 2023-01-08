package com.quasar.voxylenhanced.misc;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import com.quasar.voxylenhanced.VoxylEnhanced;
import com.quasar.voxylenhanced.VoxylFeature;
import com.quasar.voxylenhanced.VoxylUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class VoxylDiscordRichPresence extends VoxylFeature implements IPCListener {

    static VoxylDiscordRichPresence INSTANCE = null;
    static long APP_ID = 1061398229767426180L;
    static long UPDATE_INTERAL = 420L;

    static IPCClient client = new IPCClient(APP_ID);
    static RichPresence.Builder builder;
    static boolean connected = false;

    Timer updateTimer;

    void cancelTimer() {
        if(updateTimer != null) {
            updateTimer.cancel();
            updateTimer = null;
        }
    }

    public static void updatePresence() {
        if (connected) {
            setStateBasedOnGame();
            client.sendRichPresence(builder.build());
        }
    }

    public static void setStateBasedOnGame() {
        String sidebar = VoxylUtils.getScoreboardSidebarName();
        sidebar = sidebar.replaceAll("\\u00a7.", "");
        if (!sidebar.toLowerCase().contains("bedwars")) {
            VoxylDiscordRichPresence.setState("Playing " + sidebar);
        } else {
            VoxylDiscordRichPresence.setState("Playing on voxyl.net");
        }
    }

    @SubscribeEvent
    public void serverJoin(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if (VoxylUtils.isInVoxylNetwork() && VoxylEnhanced.settings.enableRichPresence) {
            connect();
        }
    }

    @SubscribeEvent
    public void serverLeave(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        close();
    }

    public static void connect() {
        if (connected) {
            return;
        }
        try {
            client.connect();
        } catch (IllegalStateException | NoDiscordClientException ignored) { }
    }

    public static void close() {
        if (!connected) {
            return;
        }
        try {
            client.close();
        } catch (IllegalStateException ignored) { }
    }

    public static void resetStartTime() {
        builder.setStartTimestamp(OffsetDateTime.now());
    }

    public static void setState(String detail) {
        builder.setState(detail);
    }

    @Override
    public void onReady(IPCClient client) {
        System.out.println("Discord RPC Opened");
        updatePresence();
        connected = true;

        updateTimer = new Timer();
        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                updatePresence();
            }
        }, 1L, UPDATE_INTERAL);
    }

    @Override
    public void onClose(IPCClient client, JSONObject json) {
        System.out.println("Discord RPC Closed");
        connected = false;
        cancelTimer();
    }

    @Override
    public void onDisconnect(IPCClient client, Throwable t) {
        System.out.println("Discord RPC Closed Forcefully");
        connected = false;
        cancelTimer();
    }

    public static void initialize() throws NoDiscordClientException {
        INSTANCE = new VoxylDiscordRichPresence();

        builder = new RichPresence.Builder();
        builder.setState("Playing on voxyl.net")
                .setLargeImage("waltuh", "Voxyl Enhanced Mod");

        client.setListener(VoxylDiscordRichPresence.INSTANCE);
        resetStartTime();

        Runtime.getRuntime().addShutdownHook(new Thread(VoxylDiscordRichPresence::close));
    }
}
