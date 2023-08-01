package com.quasar.voxylenhanced.misc;

import com.mb3364.http.AsyncHttpClient;
import com.mb3364.http.RequestParams;
import com.mb3364.http.StringHttpResponseHandler;
import com.quasar.voxylenhanced.VoxylEnhanced;
import com.quasar.voxylenhanced.VoxylFeature;
import com.quasar.voxylenhanced.VoxylUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VoxylGameLogger extends VoxylFeature {

    public static AsyncHttpClient LoggerAsyncClient = new AsyncHttpClient();
    static boolean willLogGame = false;
    static Integer delayTicks = 0;
    static Integer actionNumber = 0;
    static Integer xpEarned = -1;
    static boolean gameWon = false;

    boolean isToggled() {
        return VoxylEnhanced.settings.logFinishedGamesWebhookURL.startsWith("http");
    }

    public void logEvent(String loggedMessage) {
        if (!isToggled()) {
            return;
        }
        try {
            System.setProperty("http.agent", "Chrome");
            LoggerAsyncClient.setUserAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            LoggerAsyncClient.setConnectionTimeout(3000);
            String url = VoxylEnhanced.settings.logFinishedGamesWebhookURL;

            RequestParams requestParams = new RequestParams();
            requestParams.put("content", loggedMessage);
            requestParams.put("username", Minecraft.getMinecraft().thePlayer.getName());
            requestParams.put("avatar_url", "https://mc-heads.net/avatar/".concat(Minecraft.getMinecraft().thePlayer.getName()));

            LoggerAsyncClient.post(url, requestParams, new StringHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                    System.out.println("Successfully logged voxyl.net match");
                }

                @Override
                public void onFailure(int statusCode, Map<String, List<String>> headers, String content) {
                    System.out.println("Failure webhook request at logGame with code: " + statusCode);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    System.out.println("Failure webhook request at logGame");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void chatEvent(ClientChatReceivedEvent event) {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getCurrentServerData() == null) {
            return;
        }
        if (!VoxylUtils.isInVoxylNetwork()) {
            return;
        }

        if (isToggled()) {
            Pattern pattern = Pattern.compile("^You have gained (\\d+)XP from this game!$");
            Matcher matcher = pattern.matcher(event.message.getUnformattedText());
            if (matcher.find()) {
                xpEarned = Integer.parseInt(matcher.group(1));
                willLogGame = true;
            }
            Pattern pattern2 = Pattern.compile("^Click here to see the results$");
            if (pattern2.matcher(event.message.getUnformattedText()).find()) {
                if (willLogGame) {
                    actionNumber = 1;
                    willLogGame = false;
                    delayTicks = 1;
                }
            }
            // no solid way to do this sadly
            Pattern pattern3 = Pattern.compile("Game Won!");
            if (pattern3.matcher(event.message.getUnformattedText()).find()) {
                gameWon = true;
            }
        }
    }

    public String removeColorCodes(String inp) {
        return inp.replaceAll("§.", "");
    }

    @SubscribeEvent
    public void tickEvent(TickEvent.PlayerTickEvent event) {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().thePlayer == null) {
            return;
        }
        if (!VoxylUtils.isInVoxylNetwork()) {
            return;
        }
        if (delayTicks > 0) {
            attemptScrapeGUI: {
                if (actionNumber == 3) {
                    EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                    if (player.openContainer == null) {
                        VoxylUtils.informPlayer("Failed to log game: container is null");
                        System.out.println("container null");
                        break attemptScrapeGUI;
                    }
                    if (player.openContainer.getSlot(20) == null) {
                        VoxylUtils.informPlayer("Failed to log game: slot is null");
                        System.out.println("slot null");
                        break attemptScrapeGUI;
                    }
                    if (player.openContainer.getSlot(20).getStack() == null) {
                        System.out.println("stack null");
                        break attemptScrapeGUI;
                    }
                    System.out.println("printing tooltips");
                    StringBuilder builder = new StringBuilder();
                    List<String> tooltips = player.openContainer.getSlot(20).getStack().getTooltip(player, false);
                    for (String tooltip : tooltips) {
                        builder.append(removeColorCodes(tooltip));
                        builder.append("\n");
                    }
                    List<String> tooltips2 = player.openContainer.getSlot(22).getStack().getTooltip(player, false);
                    for (String tooltip : tooltips2) {
                        builder.append(removeColorCodes(tooltip));
                        builder.append("\n");
                    }
                    List<String> tooltips3 = player.openContainer.getSlot(24).getStack().getTooltip(player, false);
                    for (String tooltip : tooltips3) {
                        builder.append(removeColorCodes(tooltip));
                        builder.append("\n");
                    }
                    builder.append(gameWon ? "Game win state: WIN" : "Game win state: LOSE");
                    builder.append("\n");
                    String logThis = builder.toString().concat("XP gain: ").concat(Integer.toString(xpEarned));
                    logEvent(logThis);
                    VoxylUtils.informPlayer("Game successfully logged!");
                    Minecraft.getMinecraft().thePlayer.closeScreen();
                    actionNumber = 0;
                    delayTicks = 0;
                    return;
                }
            }
            delayTicks -= 1;
            if (delayTicks == 0) {
                if (actionNumber == 1) {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/lastgame");
                    actionNumber = 2;  // wait for gui to open
                } else if (actionNumber == 3) {
                    Minecraft.getMinecraft().thePlayer.closeScreen();
                    actionNumber = 0;
                }
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
        if (actionNumber == 2) {
            actionNumber = 3;
            delayTicks = 20;
        }
    }

    public void reset() {

    }
}
