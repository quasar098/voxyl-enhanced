package com.quasar.voxylenhanced;

import com.quasar.voxylenhanced.misc.VoxylMisc;
import com.quasar.voxylenhanced.obstacles.VoxylObstacles;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class VoxylInputHandler {

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        if (VoxylKeybinds.keyBindings.get(0).isPressed()) {
            VoxylMisc.goToHub();
        }
        if (VoxylKeybinds.keyBindings.get(1).isPressed()) {
            VoxylObstacles.restartPrivateGame();
        }
    }
}
