package com.quasar.voxylenhanced;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.mb3364.http.AsyncHttpClient;
import com.mb3364.http.StringHttpResponseHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VoxylUtils {

    public static AsyncHttpClient utilsAsyncClient = new AsyncHttpClient();
    public static boolean apiWarningGiven = false;

    public static void getLatestVersion(CallBack<Boolean, String> cb) {
        try {
            System.setProperty("http.agent", "Chrome");
            utilsAsyncClient.setUserAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            utilsAsyncClient.setConnectionTimeout(3000);
            String url = "https://api.github.com/repos/quasar098/voxyl-enhanced/releases/latest";
            utilsAsyncClient.get(url, new StringHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                    JsonParser parser = new JsonParser();
                    JsonObject obj = (JsonObject) parser.parse(content);
                    JsonPrimitive tname = obj.getAsJsonPrimitive("tag_name");
                    if (tname == null) {
                        cb.call(false, "N/A");
                        return;
                    }
                    boolean outdated =
                            getVersionNumberFromString(tname.getAsString()) > VoxylEnhanced.settings.latestVersionNumber;
                    cb.call(outdated, tname.getAsString());
                }

                @Override
                public void onFailure(int statusCode, Map<String, List<String>> headers, String content) {
                    System.out.println("Failure API request at getLatestVersion with code: " + statusCode);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    System.out.println("Failure API request at getLatestVersion");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract static class CallBack<ZArg, TArg> {
        public abstract void call(ZArg val, TArg val2);
    }

    // send chat message

    public static void informPlayer(String... strings) {
        if (Minecraft.getMinecraft() != null && Minecraft.getMinecraft().thePlayer != null) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(String.join("\n", strings)));
        }
    }
    public static void informPlayer(EnumChatFormatting color, String... strings) {
        if (Minecraft.getMinecraft() != null && Minecraft.getMinecraft().thePlayer != null) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(String.join("\n", strings))
                    .setChatStyle(new ChatStyle().setColor(color)));
        }
    }

    public static int getVersionNumberFromString(String idText) {
        try {
            String[] things = idText.split("\\.");
            int total = 0;
            total += Integer.parseInt(things[0]) * 1000000;
            total += Integer.parseInt(things[1]) * 1000;
            total += Integer.parseInt(things[1]);
            return total;
        } catch (IndexOutOfBoundsException e) {
            return -1;
        }
    }

    public static String getScoreboardSidebarName() {
        if (Minecraft.getMinecraft() == null) {
            return "";
        }
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) {
            return "";
        }
        if (mc.thePlayer.getWorldScoreboard() == null) {
            return "";
        }
        if (mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1) == null) {
            return "";
        }
        if (mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1).getDisplayName() == null) {
            return "";
        }
        return Minecraft.getMinecraft().thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1).getDisplayName();
    }

    public static void getStarsFromUUID(String name, UUID id, CallBack<String, Integer> callback) {
        String url = "http://api.voxyl.net/player/stats/overall/" + id.toString() + "?api=" + VoxylEnhanced.settings.apiKey;
        System.setProperty("http.agent", "Chrome");
        utilsAsyncClient.setUserAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
        utilsAsyncClient.setConnectionTimeout(3000);
        utilsAsyncClient.get(url, new StringHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Map<String, List<String>> map, String content) {
                JsonParser parser = new JsonParser();
                JsonObject obj = (JsonObject) parser.parse(content);
                try {
                    callback.call(name, obj.get("level").getAsInt());
                } catch (NullPointerException e) {
                    callback.call(name, 0);
                }
            }
            @Override
            public void onFailure(int i, Map<String, List<String>> map, String s) {
                if (i == 400) {
                    if (s.contains("Must provide an API key!")) {
                        if (!apiWarningGiven) {
                            informPlayer("You need to put an API key into Voxyl Enhanced mod! Run /ve for more info");
                            apiWarningGiven = true;
                        }
                    }
                    return;
                }
                System.out.println("Failure at getStarsFromUUID with code: " + i);
                if (i == 521) {
                    System.out.println("Api is down!");
                }
            }
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("Failure throwable at getStarsFromUUID");
            }
        });
    }

    public static boolean isInVoxylNetwork() {
        if (Minecraft.getMinecraft() == null) {
            return false;
        }
        if (Minecraft.getMinecraft().getCurrentServerData() == null) {
            return false;
        }
        if (Minecraft.getMinecraft().getCurrentServerData().serverIP == null) {
            return false;
        }
        String serverIP = Minecraft.getMinecraft().getCurrentServerData().serverIP;
        return serverIP.equals("bedwarspractice.club") || serverIP.equals("voxyl.net");
    }

    public static boolean isInBWPLobby() {
        if (!isInVoxylNetwork()) {
            return false;
        }
        if (Minecraft.getMinecraft().theWorld == null) {
            return false;
        }
        if (Minecraft.getMinecraft().theWorld.getScoreboard() == null) {
            return false;
        }
        Scoreboard sb = Minecraft.getMinecraft().theWorld.getScoreboard();
        List<Score> scores = new ArrayList<>(sb.getScores());
        return scores.size() == 10;  // bad way but whatever
    }

    public static UUID getUUIDfromStringWithoutDashes(String withoutDashes) {
        return java.util.UUID.fromString(
            withoutDashes.replaceFirst(
                "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                    "$1-$2-$3-$4-$5"
            )
        );
    }

    // integer extraction from string

    public static Integer getIntBetween(String str, String before, String after) {
        return Integer.parseInt(StringUtils.substringBetween(str, before, after));
    }

    public static Integer getIntAfter(String str, String before) {
        return Integer.parseInt(StringUtils.substringAfter(str, before));
    }

    // rendering text

    public static FontRenderer fr;

    public static void drawText(String str, boolean leftAlignment, int textY) {
        drawText(str, leftAlignment, 255, textY);
    }

    public static void drawText(String str, boolean leftAlignemnt, int alpha, int textY) {
        drawText(str, leftAlignemnt, alpha, 0xFFFFFF, textY);
    }

    public static void drawText(String str, boolean leftAlignment, int alpha, int col, int textY) {
        updateFontRenderer();
        ScaledResolution var5 = new ScaledResolution(Minecraft.getMinecraft());
        int width = var5.getScaledWidth();
        int tWidth = fr.getStringWidth(str);
        int tX = leftAlignment ? 5 : width - (tWidth + 5);
        fr.drawString(str, tX, textY, col | (alpha << 24), true);
    }

    public static void updateFontRenderer() {
        if (fr == null) {
            try {
                fr = Minecraft.getMinecraft().fontRendererObj;
            } catch (Exception ignored) {}
        }
    }

    // number manipulation

    public static int clamp(int num, int a, int b) {
        return Math.min(b, Math.max(num, a));
    }

    public static double clamp(double num, double a, double b) {
        return Math.min(b, Math.max(num, a));
    }

    public static double clamp01(double num) {
        return Math.min(1, Math.max(num, 0));
    }
}
