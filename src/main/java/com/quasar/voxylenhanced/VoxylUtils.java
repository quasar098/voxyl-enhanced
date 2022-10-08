package com.quasar.voxylenhanced;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class VoxylUtils {
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
}
