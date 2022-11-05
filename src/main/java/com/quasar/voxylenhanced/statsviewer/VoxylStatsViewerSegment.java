package com.quasar.voxylenhanced.statsviewer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mb3364.http.AsyncHttpClient;
import com.mb3364.http.StringHttpResponseHandler;
import com.quasar.voxylenhanced.VoxylEnhanced;
import com.quasar.voxylenhanced.VoxylUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VoxylStatsViewerSegment {
    public String name;

    public int stars = 0;
    public int weightedWins = 0;

    public static HashMap<String, UUID> uuidCache = new HashMap<>();

    public VoxylStatsViewerSegment(String nameOfPlayer) {
        name = nameOfPlayer;
    }

    public String formatted() {
        return name + " | " + stars + " | " + weightedWins;
    }

    public void useApi(AsyncHttpClient client, StringHttpResponseHandler handler) {
        try {
            System.out.println("2!");

            if (name == null) {
                name = "null";
                return;
            }

            final UUID[] uuid = new UUID[1];
            StringHttpResponseHandler uuidHandler = new StringHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {

                    if (content.length() != 0) {
                        JsonParser parser = new JsonParser();
                        JsonObject obj = (JsonObject) parser.parse(content);

                        uuid[0] = VoxylUtils.getUUIDfromStringWithoutDashes(obj.get("id").getAsString());
                        uuidCache.put(name, uuid[0]);
                    }

                    // make async api request
                    String url = "http://api.voxyl.net/player/stats/overall/" + uuid[0].toString() + "/?api=" + VoxylEnhanced.settings.apiKey;
                    client.get(url, handler);
                }

                @Override
                public void onFailure(int statusCode, Map<String, List<String>> headers, String content) {
                    System.out.println(statusCode);
                }

                @Override
                public void onFailure(Throwable throwable) { }
            };

            if (uuidCache.containsKey(name) && VoxylEnhanced.settings.statsViewerCacheUUIDS) {
                uuid[0] = uuidCache.get(name);
                uuidHandler.onSuccess(200, new HashMap<>(), "");
            } else {
                client.get("https://api.mojang.com/users/profiles/minecraft/" + name, uuidHandler);
            }
        } catch (Exception ignored) { }
    }
}
