package com.quasar.bwpobstacles;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mb3364.http.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

@Mod(modid = BwpObstacles.MODID, version = BwpObstacles.VERSION)
public class BwpObstacles
{
    public static final String MODID = "bwpobstacles";
    public static final String VERSION = "1.1";

    public static AsyncHttpClient client = new AsyncHttpClient();

    public static boolean isInObstacles = false;
    public static int inObstaclesTickDelay = -1;
    public static String apiKey = "hehe you aren't getting this today :)";
    public static FontRenderer fontRenderer = null;

    public static int deathCount = 0;

    public static Long time = null;
    public static Long newTime = null;
    public static boolean stopGrowingTime = false;

    public static String opponentName = null;
    public static boolean opponentWinsHasLoaded = false;
    public static String opponentWins = "???";

    public static HashMap<String, Integer> winsMap = new HashMap<>();

    private static final DecimalFormat df = new DecimalFormat("0.00");
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new ListenerClass());
        ClientCommandHandler.instance.registerCommand(new BwpObstaclesCommand());
    }

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        File configFile = new File(Loader.instance().getConfigDir(), "bwpobstacles.cfg");
        config = new Configuration(configFile);
        configurate(true);
    }

    public static String getNonNullUsername() {
        try {
            if (Minecraft.getMinecraft().thePlayer.getName() != null) {
                return Minecraft.getMinecraft().thePlayer.getName();
            } else {
                return "username is null";
            }
        } catch (NullPointerException e) {
            return "there's an error ok";
        }
    }

    public static boolean isMessageObstaclesWarning(String str) {
        return str.contains("Traverse 10 procedurally generated\n");
    }

    private static Configuration config = null;

    public static void configurate(boolean loadFromFile) {
        if (loadFromFile) {
            config.load();
        }

        Property apiKeyProp = config.get(Configuration.CATEGORY_CLIENT, "apikey", "NOAPIKEY!", "api key for voxyl network");

        if (loadFromFile) {
            apiKey = apiKeyProp.getString();
        } else {
            apiKeyProp.set(apiKey);
        }

        if (config.hasChanged()) {
            config.save();
        }
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    public class ListenerClass {
        @SubscribeEvent
        public void chatEvent(ClientChatReceivedEvent event) {
            Pattern pattern = Pattern.compile("%username% fell into the void\\.$".replace("%username%", getNonNullUsername()));
            if (pattern.matcher(event.message.getUnformattedText()).find()) {
                deathCount += 1;
            }
            if (!isInObstacles) {
                if (isMessageObstaclesWarning(event.message.getUnformattedText())) {
                    isInObstacles = true;
                    inObstaclesTickDelay = 40;
                }
            }
            if (inObstaclesTickDelay >= 0 && opponentName == null) {
                Pattern gameNamesMessage = Pattern.compile("^Players in this game:");
                if (gameNamesMessage.matcher(event.message.getUnformattedText()).find()) {
                    opponentName = event.message.getUnformattedText()
                            .replaceAll("Players in this game: ", "")
                            .replaceAll(getNonNullUsername(), "")
                            .replaceAll("\\s", "");
                }
            }
            if (event.message.getUnformattedText().contains("Game starting in 5 seconds!")) {
                time = System.currentTimeMillis()+5000L;
            }
            Pattern stopGrowPattern = Pattern.compile("^Total time was.*:.*$");
            if (stopGrowPattern.matcher(event.message.getUnformattedText()).find()) {
                stopGrowingTime = true;
            }
        }
        public void attemptMakeApiRequest() {
            if (opponentName == null) {
                return;
            }
            if (winsMap.containsKey(opponentName)) {
                opponentWins = winsMap.get(opponentName).toString();
                return;
            }
            System.setProperty("http.agent", "Chrome");
            try {
                if (apiKey.equals("NOAPIKEY!")) {
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("[BWPObstacles]: pls set api key!"));
                    return;
                }
                UUID id = Minecraft.getMinecraft().theWorld.getPlayerEntityByName(opponentName).getUniqueID();

                String url = "http://api.voxyl.net/player/stats/game/" + id.toString() + "/?api=" + apiKey;
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
            } catch (Exception err) { err.printStackTrace(); }
        }
        @SubscribeEvent
        public void tickEvent(TickEvent.PlayerTickEvent event) {
            inObstaclesTickDelay -= 1;
            if (inObstaclesTickDelay >= 0) {
                isInObstacles = true;
            }
            if (inObstaclesTickDelay == 0) {
                attemptMakeApiRequest();
            }
        }

        public void updateFontRenderer() {
            if (fontRenderer == null) {
                try {
                    fontRenderer = Minecraft.getMinecraft().fontRendererObj;
                } catch (Exception ignored) {}
            }
            if (!stopGrowingTime) {
                newTime = System.currentTimeMillis();
            }
        }

        @SubscribeEvent
        public void render(RenderGameOverlayEvent.Text event) {
            updateFontRenderer();
            if (isInObstacles) {

                ScaledResolution var5 = new ScaledResolution(Minecraft.getMinecraft());
                int width = var5.getScaledWidth();

                // death count
                String dCountString = "Death count: " + deathCount;
                int dCountWidth = fontRenderer.getStringWidth(dCountString);
                fontRenderer.drawString(dCountString, width - (dCountWidth + 5), 5, 0xFFFFFF, true);

                // opponent wins
                String opponentWinsString = "Opponent win stats: " + opponentWins;
                int opponentWinsWidth = fontRenderer.getStringWidth(opponentWinsString);
                fontRenderer.drawString(opponentWinsString, width - (opponentWinsWidth + 5), 20, 0xFFFFFF, true);

                if (time != null && newTime != null) {
                    String timeString = "Time elapsed: " + df.format((newTime-time)/1000.0);
                    int timeWidth = fontRenderer.getStringWidth(timeString);
                    fontRenderer.drawString(timeString, width - (timeWidth + 5), 35, 0xFFFFFF, true);
                }
            }
        }
        @SubscribeEvent
        public void worldLoad(WorldEvent.Load event) {
            deathCount = 0;
            opponentName = null;
            opponentWins = "???";
            time = null;
            newTime = null;
            isInObstacles = false;
            stopGrowingTime = false;
            opponentWinsHasLoaded = false;
        }
        @SubscribeEvent
        public void worldUnload(WorldEvent.Unload event) {
        }
    }
}
