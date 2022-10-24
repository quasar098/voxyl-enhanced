package com.quasar.voxylenhanced.misc;

import net.minecraft.client.Minecraft;

public class VoxylMisc {
    public static void goToHub() {
        if (Minecraft.getMinecraft() == null) {
            return;
        }
        if (Minecraft.getMinecraft().thePlayer == null) {
            return;
        }
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/hub");
    }
}
