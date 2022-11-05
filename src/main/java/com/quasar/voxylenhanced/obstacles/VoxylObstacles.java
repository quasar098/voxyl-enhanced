package com.quasar.voxylenhanced.obstacles;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mb3364.http.AsyncHttpClient;
import com.mb3364.http.StringHttpResponseHandler;
import com.quasar.voxylenhanced.VoxylEnhanced;
import com.quasar.voxylenhanced.VoxylFeature;
import com.quasar.voxylenhanced.VoxylSettingsPage;
import com.quasar.voxylenhanced.VoxylUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class VoxylObstacles extends VoxylFeature {

    // opponent win stats
    public static AsyncHttpClient client = new AsyncHttpClient();
    public static String opponentName = null;
    public static boolean opponentWinsHasLoaded = false;
    public static String opponentWins = "???";

    // time related
    public static Long time = null;
    public static Long newTime = null;
    public static boolean stopGrowingTime = false;

    // quick reset
    public static boolean qrOpeningFirst = false;
    public static int qrOpenFirst = 0;
    public static boolean qrOpeningSecond = false;
    public static int qrOpenSecond = 0;

    // other
    public static boolean isInObstacles = false;
    public static int inObstaclesTickDelay = -1;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    public static Integer startingX = null;
    public static int deathCount = 0;
    public static HashMap<String, Integer> winsMap = new HashMap<>();

    public static String getStringUsername() {
        // this will ALWAYS return a string
        try {
            if (Minecraft.getMinecraft().thePlayer.getName() != null) {
                return Minecraft.getMinecraft().thePlayer.getName();
            } else {
                return "";
            }
        } catch (NullPointerException e) {
            return "";
        }
    }

    @SubscribeEvent
    public void chatEvent(ClientChatReceivedEvent event) {

        // death count
        Pattern pattern = Pattern.compile("%username% fell into the void\\.$".replace("%username%", getStringUsername()));
        if (pattern.matcher(event.message.getUnformattedText()).find()) {
            deathCount += 1;
            if (VoxylEnhanced.settings.obstaclesAutoRequeue) {
                restartPrivateGame();
            }
        }

        // check to see if player in obstacles
        if (!isInObstacles) {
            if (event.message.getUnformattedText().contains("Traverse 10 procedurally generated\n")) {
                isInObstacles = true;
                inObstaclesTickDelay = 40;
            }
        }

        // get the opponent username
        if (inObstaclesTickDelay >= 0 && opponentName == null) {
            Pattern gameNamesMessage = Pattern.compile("^Players in this game:");
            if (gameNamesMessage.matcher(event.message.getUnformattedText()).find()) {
                opponentName = event.message.getUnformattedText()
                        .replaceAll("Players in this game: ", "")
                        .replaceAll(getStringUsername(), "")
                        .replaceAll("\\s", "");
            }
        }

        // start the timer
        if (event.message.getUnformattedText().contains("Game starting in 5 seconds!")) {
            time = System.currentTimeMillis()+5000L;
        }

        // stop the timer
        Pattern stopGrowPattern = Pattern.compile("^Total time was.*:.*$");
        if (stopGrowPattern.matcher(event.message.getUnformattedText()).find()) {
            stopGrowingTime = true;

            long calcNewTime = time;
            calcNewTime += (long) VoxylUtils.getIntBetween(event.message.getUnformattedText(), "was ", ":")*60000;
            calcNewTime += (long) VoxylUtils.getIntBetween(event.message.getUnformattedText(), ":", ".")*1000;
            calcNewTime += (long) VoxylUtils.getIntAfter(event.message.getUnformattedText(), ".");
            newTime = calcNewTime;
        }
    }
    public void attemptMakeApiRequest() {
        // opponent must have name
        if (opponentName == null) {
            return;
        }
        // retrieve opponent win stats from cache
        if (winsMap.containsKey(opponentName)) {
            opponentWins = winsMap.get(opponentName).toString();
            return;
        }

        // if cache doesn't have opponent win stats, make api request
        System.setProperty("http.agent", "Chrome");
        try {

            // if api key is not there, ask player to set it
            if (VoxylEnhanced.settings.apiKey.equals("")) {
                VoxylUtils.informPlayer("You need to set your api key for the Voxyl Enhanced mod to work properly!");
                opponentWins = "API Key missing!";
                return;
            }

            try {
                // get player in the world
                UUID id = Minecraft.getMinecraft().theWorld.getPlayerEntityByName(opponentName).getUniqueID();

                // make async api request
                String url = "http://api.voxyl.net/player/stats/game/" + id.toString() + "/?api=" + VoxylEnhanced.settings.apiKey;
                client.setUserAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
                client.setConnectionTimeout(3000);
                client.get(url, new StringHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Map<String, List<String>> map, String content) {
                        JsonParser parser = new JsonParser();
                        JsonObject obj = (JsonObject) parser.parse(content);
                        JsonObject stats = (JsonObject) obj.get("stats");
                        if (stats == null) {
                            opponentWins = "ERROR!";
                            return;
                        }
                        JsonObject obstacles = (JsonObject) stats.get("obstacleSingle");
                        if (obstacles == null) {
                            opponentWins = "0";
                            return;
                        }
                        opponentWins = obstacles.get("wins").getAsInt() + "";
                        winsMap.put(opponentName, obstacles.get("wins").getAsInt());
                    }
                    @Override
                    public void onFailure(int i, Map<String, List<String>> map, String s) {
                        opponentWins = "ERROR: " + i;
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                        opponentWins = "ERROR!";
                    }
                });
            } catch (NullPointerException e) {
                opponentWins = "No opponent";
            }
        } catch (Exception err) { err.printStackTrace(); }
    }

    public static boolean isInSingleplayer() {
        return opponentWins.equals("No opponent");
    }

    public static void restartPrivateGame() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc == null) {
            return;
        }
        if (!isInSingleplayer()) {
            return;
        }
        mc.thePlayer.sendChatMessage("/pg");
        qrOpeningFirst = true;
    }

    @SubscribeEvent
    public void tickEvent(TickEvent.PlayerTickEvent event) {
        inObstaclesTickDelay -= 1;
        if (inObstaclesTickDelay >= 0) {
            isInObstacles = true;
        }
        if (inObstaclesTickDelay == 0) {
            attemptMakeApiRequest();
            startingX = Math.toIntExact(Math.round(Minecraft.getMinecraft().thePlayer.posX));
        }

        Minecraft mc = Minecraft.getMinecraft();
        if (mc == null) {
            return;
        }
        if (mc.thePlayer == null) {
            return;
        }
        if (mc.thePlayer.openContainer == null) {
            qrOpeningSecond = false;
            return;
        }

        if (qrOpenFirst != 0) {
            qrOpenFirst += 1;
            if (qrOpenFirst > 4) {
                mc.playerController.windowClick(
                        mc.thePlayer.openContainer.windowId, 24,
                        0,
                        0,
                        mc.thePlayer
                );
                qrOpenFirst = 0;
                qrOpeningSecond = true;
            }
        }
        if (qrOpeningSecond) {
            if (mc.thePlayer.openContainer.getSlot(25) != null) {
                if (mc.thePlayer.openContainer.getSlot(25).getHasStack()) {
                    qrOpenSecond = 1;
                    qrOpeningSecond = false;
                }
            }
        }
        if (qrOpenSecond != 0) {
            qrOpenSecond += 1;
            if (qrOpenSecond > 2) {
                mc.playerController.windowClick(
                        mc.thePlayer.openContainer.windowId, 25,
                        0,
                        0,
                        mc.thePlayer
                );
                qrOpenSecond = 0;
            }
        }
    }

    @SubscribeEvent
    public void guiOpenEvent(GuiOpenEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc == null) {
            return;
        }
        if (mc.thePlayer == null) {
            return;
        }
        if (mc.thePlayer.openContainer == null) {
            return;
            // null safety is a pain
        }
        if (qrOpeningFirst) {
            System.out.println("First init");
            qrOpenFirst = 1;
            qrOpeningFirst = false;
        }
    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Text event) {
        if (!stopGrowingTime) {
            newTime = System.currentTimeMillis();
        }
        if (isInObstacles && VoxylEnhanced.settings.obstaclesToggled) {
            boolean leftAligned = VoxylEnhanced.settings.obstaclesLeftAligned;

            // death count
            VoxylUtils.drawText("Death count: " + deathCount, leftAligned, 5);

            // opponent wins
            VoxylUtils.drawText("Opponent win stats: " + opponentWins, leftAligned, 20);

            if (time != null && newTime != null) {

                // time elapsed
                double timeElapsed = (newTime - time) / 1000.0;
                VoxylUtils.drawText("Time elapsed: " + df.format(timeElapsed), leftAligned, 35);

                if (startingX != null) {

                    // percentage done
                    double diff = Math.abs(startingX - Minecraft.getMinecraft().thePlayer.posX);
                    double pDone = VoxylUtils.clamp(diff / 175 * 100, 0, 100);
                    VoxylUtils.drawText("Percentage done: " + df.format(stopGrowingTime ? 100 : pDone), leftAligned, 50);

                    // estimated arrival
                    double estimatedArrival = timeElapsed/(Math.abs(startingX - Minecraft.getMinecraft().thePlayer.posX)/175);
                    if (!(0 >= estimatedArrival || Double.isInfinite(estimatedArrival)) && (!stopGrowingTime)) {
                        VoxylUtils.drawText("Estimated arrival: " + df.format(estimatedArrival), leftAligned, 65);
                    }
                }
            }
        }
    }

    public void reset() {
        deathCount = 0;
        opponentName = null;
        opponentWins = "???";
        time = null;
        newTime = null;
        isInObstacles = false;
        stopGrowingTime = false;
        opponentWinsHasLoaded = false;
        startingX = null;
    }
}
