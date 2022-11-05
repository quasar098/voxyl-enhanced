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
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.Sys;

import java.util.*;
import java.util.regex.Pattern;

public class VoxylStatsViewer extends VoxylFeature {

    public static AsyncHttpClient client = new AsyncHttpClient();
    List<VoxylStatsViewerSegment> stats = new ArrayList<>();
    boolean currentlyInGame = false;
    boolean waitingForGameStart = false;

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Text event) {
        if (!VoxylEnhanced.settings.statsViewerToggled) return;

        if (stats == null) {
            return;
        }
        if (!currentlyInGame) {
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
    public void chatEvent(ClientChatReceivedEvent event) {
        Pattern gameNamesMessage = Pattern.compile("^Players in this game:");
        if (gameNamesMessage.matcher(event.message.getUnformattedText()).find()) {

            String playerNames = StringUtils.substringAfter(event.message.getUnformattedText(), "Players in this game: ");
            playerNames = playerNames.substring(0, playerNames.length()-1);

            System.setProperty("http.agent", "Chrome");
            client.setUserAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            client.setConnectionTimeout(3000);
            for (String name : playerNames.split("\\s")) {
                if (!VoxylEnhanced.settings.statsViewerShowOwnName) {
                    if (name.equals(Minecraft.getMinecraft().thePlayer.getName())) {
                        continue;
                    }
                }
                VoxylStatsViewerSegment stat = new VoxylStatsViewerSegment(name);
                stats.add(stat);
            }
            waitingForGameStart = true;
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
                            if (level != null) {
                                stats.get(finalIndex).stars = level.getAsInt();
                            }
                            if (weightedwins != null) {
                                stats.get(finalIndex).weightedWins = weightedwins.getAsInt();
                            }

                            if (VoxylEnhanced.settings.statsViewerSortOrder == 0) {
                                stats.sort(Comparator.comparing(u -> u.name));
                            } else if (VoxylEnhanced.settings.statsViewerSortOrder == 1) {
                                stats.sort(Comparator.comparingInt(u -> -u.stars));
                            } else {
                                stats.sort(Comparator.comparingInt(u -> -u.weightedWins));
                            }

                        } catch (Exception e) { e.printStackTrace(); }
                    }
                    @Override
                    public void onFailure(int i, Map<String, List<String>> map, String s) { System.out.println("ERR: " + i); }
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
