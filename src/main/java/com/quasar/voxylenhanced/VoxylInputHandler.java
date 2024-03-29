package com.quasar.voxylenhanced;

import com.quasar.voxylenhanced.misc.VoxylMisc;
import com.quasar.voxylenhanced.obstacles.VoxylObstacles;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class VoxylInputHandler {

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

        keyBindings.add(new KeyBinding(
                "voxylenhanced.inviteabunch",
                Keyboard.KEY_NONE,
                "voxylenhanced.vekeybinds"
        ));  // 2, inviteabunch

        for (KeyBinding keyBinding : keyBindings) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
    }

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        if (keyBindings.get(0).isPressed()) {
            VoxylMisc.goToHub();
        }
        if (keyBindings.get(1).isPressed()) {
            VoxylObstacles.restartPrivateGame();
        }
        if (keyBindings.get(2).isPressed()) {
            VoxylEnhanced.settings.inviteABunch = !VoxylEnhanced.settings.inviteABunch;
            if (VoxylEnhanced.settings.inviteABunch) {
                VoxylUtils.informPlayer(EnumChatFormatting.GREEN, "Invite-a-bunch enabled!");
            } else {
                VoxylUtils.informPlayer(EnumChatFormatting.RED, "Invite-a-bunch disabled!");
            }
        }
    }
}
