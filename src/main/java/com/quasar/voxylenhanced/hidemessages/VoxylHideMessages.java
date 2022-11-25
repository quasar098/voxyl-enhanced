package com.quasar.voxylenhanced.hidemessages;

import com.quasar.voxylenhanced.VoxylEnhanced;
import com.quasar.voxylenhanced.VoxylFeature;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class VoxylHideMessages extends VoxylFeature {

    static Pattern pReport = Pattern.compile("^If a player is cheating, please use /report to report them!$");
    static Pattern pExpertColor = Pattern.compile("^\\(!\\) Expert rank can change their preferred team colour to non-standard teams\\.$");
    static Pattern pMasterColor = Pattern.compile("^\\(!\\) Master rank after picking a preferred team colour can make the opposite team its complementary colour\\.$");
    static Pattern pLeave = Pattern.compile("^\\(!\\) Leave your current session using /leave\\.$");
    static Pattern pObstaclesMomentum = Pattern.compile("^\\(!\\) When placing blocks on the side of an obstacle, consider jumping before placing a block - continuing the momentum\\.$");
    static Pattern pNoPlayers = Pattern.compile("^There appears to be no players in your game\\. Please report this in the discord!$");
    static Pattern pMessageInvites = Pattern.compile("^> \\[.*] .* has invited all lobby players to join .*!$");
    static Pattern pTools = Pattern.compile("^\\(!\\) You can change your tool positions with /tools for better practice!$");
    static Pattern pRank = Pattern.compile("^\\(!\\) If you enjoy the server consider buying a rank! store\\.bedwarspractice\\.club$");
    static Pattern pCompass = Pattern.compile("^\\(!\\) Join a game from listed from the compass!$");
    static Pattern pOutOfBounds = Pattern.compile("^You cannot travel out of bounds! You have been sent back to spawn\\.$");
    static Pattern pTwitter = Pattern.compile("^\\(!\\) Keep up with the twitter account for updates and giveaways at twitter\\.com/bedwarspractice$");

    // the settings for these are in VoxylSettingsPage

    @SubscribeEvent
    public void chatEvent(ClientChatReceivedEvent event) {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getCurrentServerData() == null) {
            return;
        }
        if (!Minecraft.getMinecraft().getCurrentServerData().serverIP.equals("bedwarspractice.club")) {
            return;
        }
        String m = event.message.getUnformattedText();

        if (VoxylEnhanced.settings.hideMessageReport) {
            if (pReport.matcher(m).find()) {
                event.setCanceled(true);
            }
        }
        if (VoxylEnhanced.settings.hideMessageExpertColor) {
            if (pExpertColor.matcher(m).find()) {
                event.setCanceled(true);
            }
        }
        if (VoxylEnhanced.settings.hideMessageMasterColor) {
            if (pMasterColor.matcher(m).find()) {
                event.setCanceled(true);
            }
        }
        if (VoxylEnhanced.settings.hideMessageLeave) {
            if (pLeave.matcher(m).find()) {
                event.setCanceled(true);
            }
        }
        if (VoxylEnhanced.settings.hideMessageObstaclesMomentum) {
            if (pObstaclesMomentum.matcher(m).find()) {
                event.setCanceled(true);
            }
        }
        if (VoxylEnhanced.settings.hideMessageNoPlayers) {
            if (pNoPlayers.matcher(m).find()) {
                event.setCanceled(true);
            }
        }
        if (VoxylEnhanced.settings.hideMessageInvites) {
            if (pMessageInvites.matcher(m).find()) {
                event.setCanceled(true);
            }
        }
        if (VoxylEnhanced.settings.hideMessageTools) {
            if (pTools.matcher(m).find()) {
                event.setCanceled(true);
            }
        }
        if (VoxylEnhanced.settings.hideMessageRank) {
            if (pReport.matcher(m).find()) {
                event.setCanceled(true);
            }
        }
        if (VoxylEnhanced.settings.hideMessageCompass) {
            if (pCompass.matcher(m).find()) {
                event.setCanceled(true);
            }
        }
        if (VoxylEnhanced.settings.hideMessageOutOfBounds) {
            if (pOutOfBounds.matcher(m).find()) {
                event.setCanceled(true);
            }
        }
        if (VoxylEnhanced.settings.hideMessageTwitter) {
            if (pTwitter.matcher(m).find()) {
                event.setCanceled(true);
            }
        }

    }
}
