package com.quasar.voxylenhanced.autogg;

import com.quasar.voxylenhanced.VoxylEnhanced;
import com.quasar.voxylenhanced.VoxylFeature;
import com.quasar.voxylenhanced.VoxylUtils;
import com.quasar.voxylenhanced.VoxylSettingsPage;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VoxylAutoGG extends VoxylFeature {

    // other
    public static int lastNumberOfKills = 0;
    public static Integer timeAtLastGG = null;

    public static boolean readyToSayGG() {
        if (timeAtLastGG==null || timeAtLastGG+4000 <= System.currentTimeMillis()) {
            timeAtLastGG = (int) System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public static void sayGG() {
        if (readyToSayGG()) {
            String newGGMessage = "gg";
            if (VoxylEnhanced.settings.autoggSayKillCount
                    && lastNumberOfKills >= VoxylEnhanced.settings.autoggMinKillsForKillCount) {
                newGGMessage = newGGMessage.concat(
                        " ".concat(Integer.toString(lastNumberOfKills).concat(" kills"))
                );
            }
            Minecraft.getMinecraft().thePlayer.sendChatMessage(newGGMessage);
        }
    }

    @SubscribeEvent
    public void chatEvent(ClientChatReceivedEvent event) {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getCurrentServerData() == null) {
            return;
        }
        if (!VoxylUtils.isInVoxylNetwork()) {
            return;
        }

        if (VoxylEnhanced.settings.autoggToggled) {
            Pattern pattern2 = Pattern.compile("^\\+(\\d+) kills.?.?.?$");
            Matcher matcher = pattern2.matcher(event.message.getUnformattedText());
            if (matcher.find()) {
                lastNumberOfKills = Integer.parseInt(matcher.group(1));
            }
            Pattern pattern3 = Pattern.compile("^\\+\\d+ beds \\+(\\d+) kills.?.?.?$");
            Matcher matcher2 = pattern3.matcher(event.message.getUnformattedText());
            if (matcher2.find()) {
                lastNumberOfKills = Integer.parseInt(matcher2.group(1));
            }
            Pattern pattern = Pattern.compile("^You have gained .*XP from this game!$");
            if (pattern.matcher(event.message.getUnformattedText()).find()) {
                sayGG();
            }
        }
    }

    public void reset() {
        lastNumberOfKills = 0;
        timeAtLastGG = null;
    }
}
