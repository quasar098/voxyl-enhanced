package com.quasar.voxylenhanced.autogg;

import com.quasar.voxylenhanced.VoxylFeature;
import com.quasar.voxylenhanced.VoxylUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class VoxylAutoGG extends VoxylFeature {

    // settings
    public static boolean toggled = true;
    public static boolean sayKillCount = false;

    // other
    public static int lastNumberOfKills = 0;
    public static Integer timeAtLastGG = null;

    public void configurate(Configuration config, boolean loadFromFile) {
        Property toggledProp = config.get(Configuration.CATEGORY_CLIENT, "autogg-toggled", true, "Say autogg after game");
        Property sayKillCountProp = config.get(Configuration.CATEGORY_CLIENT, "autogg-sayKillCount", false, "Say kill count along with gg (only if you get a lot of kills)");

        if (loadFromFile) {
            toggled = toggledProp.getBoolean();
            sayKillCount = sayKillCountProp.getBoolean();
        } else {
            toggledProp.set(toggled);
            sayKillCountProp.set(sayKillCount);
        }
    }

    public static void handleCommand(String[] args) {
        if (args[1].equals("toggle")) {
            toggled = !toggled;
            VoxylUtils.informPlayer("Toggled obstacles functionality " + (toggled ? "on" : "off"));
            return;
        }
        if (args[1].equals("togglekills")) {
            sayKillCount = !sayKillCount;
            VoxylUtils.informPlayer(sayKillCount ? "Will say kill count with gg" : "Will not say kill count with gg");
            return;
        }
        VoxylUtils.informPlayer("/ve autogg <toggle|togglekills>");
    }

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
            if (sayKillCount && lastNumberOfKills > 5) {
                newGGMessage = newGGMessage.concat(" ".concat(Integer.toString(lastNumberOfKills).concat(" kills")));
            }
            Minecraft.getMinecraft().thePlayer.sendChatMessage(newGGMessage);
        }
    }

    @SubscribeEvent
    public void chatEvent(ClientChatReceivedEvent event) {
        if (toggled) {
            Pattern pattern2 = Pattern.compile("^\\+.* kills");
            if (pattern2.matcher(event.message.getUnformattedText()).find()) {
                lastNumberOfKills = VoxylUtils.getIntBetween(event.message.getUnformattedText(), "+", " k");
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
