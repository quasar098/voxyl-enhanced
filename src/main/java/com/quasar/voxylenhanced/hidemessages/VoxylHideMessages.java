package com.quasar.voxylenhanced.hidemessages;

import com.quasar.voxylenhanced.VoxylEnhanced;
import com.quasar.voxylenhanced.VoxylFeature;
import com.quasar.voxylenhanced.statsviewer.VoxylLevelhead;
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
    static Pattern pStats = Pattern.compile("^\\(!\\) Kills, beds broken, and wins are all added to your profile\\. Accessible via /stats\\.$");
    static Pattern pReportAtHub = Pattern.compile("^\\(!\\) If you believe a player is hacking use /report to report them!$");
    static Pattern pDiscord = Pattern.compile("^\\(!\\) Join the discord server for updates! discord\\.gg/TUeUnkqXnY$");
    static Pattern pPractice = Pattern.compile("^\\(!\\) Server doesn't track losses\\. Feel free to practice on a bad day to warm up\\.$");
    static Pattern pDiagonal = Pattern.compile("\\(!\\) It is recommended to practice diagonal bridging to complete the last segment of the course as fast as possible\\.$");
    static Pattern pHotbar = Pattern.compile("^\\(!\\) Use /hotbar to re-arrange your hotbar\\.$");
    static Pattern pSprintHits = Pattern.compile("^\\(!\\) Use /showfailed to see when you fail a sprint hit!$");
    static Pattern pAutomaticRearrange = Pattern.compile("^\\(!\\) Re-arrange your hotbar and it will automatically save\\.$");
    static Pattern pAvoidSame = Pattern.compile("^\\(!\\) Can avoid same players in a requeued game with 'avoid same players' option in /chatpref\\.$");
    static Pattern pDisconnected = Pattern.compile("^\\(!\\) If you are disconnected from your game, use /rejoin to reconnect\\.$");

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
        if (VoxylEnhanced.settings.hideMessageRank) {  // i feel guilty
            if (pRank.matcher(m).find()) {
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
        if (VoxylEnhanced.settings.hideMessageStats) {
            if (pStats.matcher(m).find()) {
                event.setCanceled(true);
            }
        }
        if (VoxylEnhanced.settings.hideMessageReportAtHub) {
            if (pReportAtHub.matcher(m).find()) {
                event.setCanceled(true);
            }
        }
        if (VoxylEnhanced.settings.hideMessageDiscord) {
            if (pDiscord.matcher(m).find()) {
                event.setCanceled(true);
            }
        }
        if (VoxylEnhanced.settings.hideMessageDiagonal) {
            if (pDiagonal.matcher(m).find()) {
                event.setCanceled(true);
            }
        }
        if (VoxylEnhanced.settings.hideMessagePractice) {
            if (pPractice.matcher(m).find()) {
                event.setCanceled(true);
            }
        }
        if (VoxylEnhanced.settings.hideMessageHotbar) {
            if (pHotbar.matcher(m).find()) {
                event.setCanceled(true);
            }
        }
        if (VoxylEnhanced.settings.hideMessageSprintHits) {
            if (pSprintHits.matcher(m).find()) {
                event.setCanceled(true);
            }
        }
        if (VoxylEnhanced.settings.hideMessageAutomaticRearrange) {
            if (pAutomaticRearrange.matcher(m).find()) {
                event.setCanceled(true);
            }
        }
        if (VoxylEnhanced.settings.hideMessageAvoidSame) {
            if (pAvoidSame.matcher(m).find()) {
                event.setCanceled(true);
            }
        }
        if (VoxylEnhanced.settings.hideMessageDisconnect) {
            if (pAvoidSame.matcher(m).find()) {
                event.setCanceled(true);
            }
        }
        if (VoxylEnhanced.settings.hideMessageCustomRegex.length() > 0) {
            if (Pattern.compile(VoxylEnhanced.settings.hideMessageCustomRegex).matcher(m).find()) {
                event.setCanceled(true);
            }
        }
    }
}
