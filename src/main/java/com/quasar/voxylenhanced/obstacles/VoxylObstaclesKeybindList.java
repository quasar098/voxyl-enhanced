package com.quasar.voxylenhanced.obstacles;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class VoxylObstaclesKeybindList {
    public static KeyBinding[] keyBindings;

    public static void register() {
        keyBindings = new KeyBinding[1];

        keyBindings[0] = new KeyBinding(
                "KEY.voxylenhanced.obstaclesautorequeue",
                Keyboard.KEY_GRAVE,
                "KEY.voxylenhanced.vekeybinds"
        );

        for (KeyBinding keyBinding : keyBindings) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
    }
}
