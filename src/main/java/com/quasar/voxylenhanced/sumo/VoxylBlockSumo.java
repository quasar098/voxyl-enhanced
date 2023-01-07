package com.quasar.voxylenhanced.sumo;

import com.quasar.voxylenhanced.VoxylEnhanced;
import com.quasar.voxylenhanced.VoxylFeature;
import com.quasar.voxylenhanced.VoxylSettingsPage;
import com.quasar.voxylenhanced.VoxylUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.Sys;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class VoxylBlockSumo extends VoxylFeature {

    static Pattern pOpItemSpawning = Pattern.compile("^An OP Item is spawning on the gold block in 5 seconds!.*$");
    static Pattern pAllPlayerItems = Pattern.compile("^All players have recieved.*$");
    static Long nextPowerItem = null;
    static Long nextRegularItem = null;
    static DecimalFormat df = new DecimalFormat("0.0");

    @SubscribeEvent
    public void chatMessage(ClientChatReceivedEvent event) {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getCurrentServerData() == null) {
            return;
        }
        if (!VoxylUtils.isInVoxylNetwork()) {
            return;
        }
        if (!VoxylEnhanced.settings.blockSumoEnabled) {
            return;
        }
        if (pOpItemSpawning.matcher(event.message.getUnformattedText()).find()) {
            nextPowerItem = System.currentTimeMillis() + 5000L;
        }
        if (pAllPlayerItems.matcher(event.message.getUnformattedText()).find()) {
            nextRegularItem = System.currentTimeMillis() + 25000L;
        }
    }

    public String formatTime(Long stime, Long etime) {
        return df.format(((double) (stime - etime))/1000L);
    }

    @SubscribeEvent
    public void drawText(RenderGameOverlayEvent.Text event) {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getCurrentServerData() == null) {
            return;
        }

        if (nextPowerItem != null) {
            if (nextPowerItem < System.currentTimeMillis()) {
                nextPowerItem = System.currentTimeMillis() + 30000L;
            }
            VoxylUtils.drawText("OP Item: " + formatTime(nextPowerItem,  System.currentTimeMillis()), false, 5);
        }
        if (nextRegularItem != null) {
            VoxylUtils.drawText("Normal Item: " + formatTime(nextRegularItem, System.currentTimeMillis()), false, 15);
        }
    }

    public void reset() {
        nextPowerItem = null;
        nextRegularItem = null;
    }
}
