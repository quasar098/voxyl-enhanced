package com.quasar.voxylenhanced;

import com.quasar.voxylenhanced.obstacles.VoxylObstacles;
import com.quasar.voxylenhanced.obstacles.VoxylObstaclesKeybindList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class VoxylInputHandler {

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        if (VoxylObstaclesKeybindList.keyBindings[0].isPressed()) {
            VoxylObstacles.restartPrivateGame();
        }
    }
}
