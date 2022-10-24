package com.quasar.voxylenhanced;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class VoxylKeybinds {
    public static ArrayList<KeyBinding> keyBindings;

    public static void register() {
        keyBindings = new ArrayList<>();

        keyBindings.add(new KeyBinding(
                "voxylenhanced.gotohub",
                Keyboard.KEY_NONE,
                "voxylenhanced.vekeybinds"
        ));  // 0, gotohub

        keyBindings.add(new KeyBinding(
                "voxylenhanced.obstaclesautorequeue",
                Keyboard.KEY_NONE,
                "voxylenhanced.vekeybinds"
        ));  // 1, obstaclesautorequeue

        for (KeyBinding keyBinding : keyBindings) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
    }
}
