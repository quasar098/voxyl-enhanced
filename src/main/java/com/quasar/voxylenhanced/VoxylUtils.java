package com.quasar.voxylenhanced;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mb3364.http.AsyncHttpClient;
import com.mb3364.http.StringHttpResponseHandler;
import com.quasar.voxylenhanced.obstacles.VoxylObstacles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

public class VoxylUtils {

    public static AsyncHttpClient utilsAsyncClient = new AsyncHttpClient();

    public abstract static class CallBack<TArg> {
        public abstract void call(TArg val);
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

    public static void getStarsFromUUID(UUID id, CallBack<Integer> callback) {
        System.setProperty("http.agent", "Chrome");
        String url = "http://api.voxyl.net/player/stats/game/" + id.toString() + "/?api=" + VoxylEnhanced.settings.apiKey;
        utilsAsyncClient.setUserAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
        utilsAsyncClient.setConnectionTimeout(3000);
        utilsAsyncClient.get(url, new StringHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Map<String, List<String>> map, String content) {
                JsonParser parser = new JsonParser();
                JsonObject obj = (JsonObject) parser.parse(content);
                JsonObject stats = (JsonObject) obj.get("stats");
                if (stats == null) {
                    System.out.println("Stats are null at getStarsFromUUID");
                    return;
                }
                JsonObject obstacles = (JsonObject) stats.get("obstacleSingle");
                if (obstacles == null) {
                    callback.call(0);
                    return;
                }
                try {
                    callback.call(obstacles.get("wins").getAsInt());
                } catch (NullPointerException e) {
                    callback.call(0);
                }
            }
            @Override
            public void onFailure(int i, Map<String, List<String>> map, String s) {
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

    public static int round(int number,int multiple) {

        int result = multiple;

        //If not already multiple of given number

        if (number % multiple != 0){

            int division = (number / multiple)+1;

            result = division * multiple;

        }

        return result;

    }

    public static UUID getUUIDfromStringWithoutDashes(String withoutDashes) {
        return java.util.UUID.fromString(
            withoutDashes.replaceFirst(
                "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"
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
        drawText(str, leftAlignment, 0xFFFFFF, textY);
    }

    public static void drawText(String str, boolean leftAlignment, int col, int textY) {
        updateFontRenderer();
        ScaledResolution var5 = new ScaledResolution(Minecraft.getMinecraft());
        int width = var5.getScaledWidth();
        int tWidth = fr.getStringWidth(str);
        int tX = leftAlignment ? 5 : width - (tWidth + 5);
        fr.drawString(str, tX, textY, col, true);
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
