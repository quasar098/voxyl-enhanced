package com.quasar.voxylenhanced.statsviewer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.mb3364.http.AsyncHttpClient;
import com.mb3364.http.StringHttpResponseHandler;
import com.quasar.voxylenhanced.VoxylEnhanced;
import com.quasar.voxylenhanced.VoxylFeature;
import com.quasar.voxylenhanced.VoxylUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Pattern;

public class VoxylStatsViewer extends VoxylFeature {

    public static AsyncHttpClient client = new AsyncHttpClient();
    List<VoxylStatsViewerSegment> stats = new ArrayList<>();
    public static Map<String, Integer> cachedStars = new HashMap<>();
    boolean currentlyInGame = false;
    boolean waitingForGameStart = false;
    boolean apiIsDown = false;

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Text event) {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getCurrentServerData() == null) {
            return;
        }
        if (!VoxylUtils.isInVoxylNetwork()) {
            return;
        }

        if (!VoxylEnhanced.settings.statsViewerToggled) return;

        if (stats == null) {
            return;
        }
        if (!currentlyInGame) {
            return;
        }
        if (!VoxylEnhanced.settings.statsViewerShowTable) {
            return;
        }
        int ty = 5;
        boolean lalign = true;
        if (VoxylEnhanced.settings.statsViewerShowFirstLine && (stats.size() != 0)) {
            VoxylUtils.drawText("Name | Stars | Weighted Wins", lalign, ty);
            ty += 10 + VoxylEnhanced.settings.statsViewerSpacing;
        }
        for (VoxylStatsViewerSegment stat : stats) {
            if (stat == null) {
                continue;
            }
            VoxylUtils.drawText(stat.formatted(), VoxylEnhanced.settings.statsViewerLeftAligned, ty);
            ty += 10 + VoxylEnhanced.settings.statsViewerSpacing;
        }
    }

    @SubscribeEvent
    public void renderOverlay(RenderLivingEvent.Specials.Post<EntityPlayer> event) {
        if (!VoxylEnhanced.settings.statsViewerShowBelowName) {
            return;
        }
        if (!VoxylUtils.isInVoxylNetwork()) {
            return;
        }

        String playerName = event.entity.getName();
        UUID playerUUID = event.entity.getUniqueID();

        // fetch lobby stuff idk
        if (VoxylEnhanced.settings.statsViewerScanLobby) {
            if (VoxylUtils.isInBWPLobby()) {
                if (!cachedStars.containsKey(playerName)) {
                    cachedStars.put(playerName, -1);
                    VoxylUtils.getStarsFromUUID(playerName, playerUUID, new VoxylUtils.CallBack<String, Integer>() {
                        @Override
                        public void call(String val, Integer val2) {
                            cachedStars.put(val, val2);
                        }
                    });
                }
            }
        }

        // remove self name if needed
        if (!VoxylEnhanced.settings.statsViewerShowOwnBelowName) {
            if (playerName.equals(Minecraft.getMinecraft().thePlayer.getName())) {
                return;
            }
        }

        // render the tag
        VoxylLevelhead.renderTag(event.entity, event.x, event.y+0.3, event.z, cachedStars.getOrDefault(playerName, -1));
    }

    @SubscribeEvent
    public void chatEvent(ClientChatReceivedEvent event) {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getCurrentServerData() == null) {
            return;
        }
        if (!VoxylUtils.isInVoxylNetwork()) {
            return;
        }
        if (apiIsDown) {
            return;
        }
        Pattern gameNamesMessage = Pattern.compile("^Players in this game:");
        if (gameNamesMessage.matcher(event.message.getUnformattedText()).find()) {

            String playerNames = StringUtils.substringAfter(event.message.getUnformattedText(), "Players in this game: ");
            playerNames = playerNames.substring(0, playerNames.length()-1);

            System.setProperty("http.agent", "Chrome");
            client.setUserAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            client.setConnectionTimeout(3000);
            for (String name : playerNames.split("\\s")) {
                VoxylStatsViewerSegment stat = new VoxylStatsViewerSegment(name);
                stats.add(stat);
            }
            waitingForGameStart = true;
        }

        Pattern doSort = Pattern.compile("^Game starting in 2 seconds!$");
        if (doSort.matcher(event.message.getUnformattedText()).find()) {
            if (VoxylEnhanced.settings.statsViewerSortOrder == 0) {
                stats.sort(Comparator.comparing(u -> u.name));
            } else if (VoxylEnhanced.settings.statsViewerSortOrder == 1) {
                stats.sort(Comparator.comparingInt(u -> -u.stars));
            } else {
                stats.sort(Comparator.comparingInt(u -> -u.weightedWins));
            }
        }

        Pattern gameStart = Pattern.compile("^Game starting in 4 seconds!$");
        if (gameStart.matcher(event.message.getUnformattedText()).find()) {
            currentlyInGame = true;
            for (int index = 0; index < stats.size(); index++) {
                int finalIndex = index;
                stats.get(finalIndex).useApi(client, new StringHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Map<String, List<String>> map, String content) {
                        try {
                            JsonParser parser = new JsonParser();
                            JsonObject obj = (JsonObject) parser.parse(content);

                            JsonPrimitive success = obj.getAsJsonPrimitive("success");

                            if (!success.getAsBoolean()) {
                                return;
                            }

                            JsonPrimitive level = obj.getAsJsonPrimitive("level");
                            JsonPrimitive weightedwins = obj.getAsJsonPrimitive("weightedwins");
                            if (!cachedStars.containsKey(stats.get(finalIndex).name)) {
                                cachedStars.put(stats.get(finalIndex).name, level.getAsInt());
                            }
                            if (level != null) {
                                stats.get(finalIndex).stars = level.getAsInt();
                            }
                            if (weightedwins != null) {
                                stats.get(finalIndex).weightedWins = weightedwins.getAsInt();
                            }

                        } catch (Exception e) { e.printStackTrace(); }
                    }
                    @Override
                    public void onFailure(int i, Map<String, List<String>> map, String s) {
                        System.out.println("ERR: " + i);
                        if (i == 521) {
                            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("The API is down! Tell Tom to fix it"));
                            apiIsDown = true;
                        }
                    }
                    @Override
                    public void onFailure(Throwable throwable) { throwable.printStackTrace(); }
                });
            }
        }
    }

    @Override
    public void reset() {
        stats = new ArrayList<>();
        currentlyInGame = false;
    }
}
