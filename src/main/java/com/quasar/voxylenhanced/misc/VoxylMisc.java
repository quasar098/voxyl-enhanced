package com.quasar.voxylenhanced.misc;

import com.quasar.voxylenhanced.VoxylEnhanced;
import com.quasar.voxylenhanced.VoxylFeature;
import com.quasar.voxylenhanced.VoxylUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class VoxylMisc extends VoxylFeature {
    public static void goToHub() {
        if (Minecraft.getMinecraft() == null) {
            return;
        }
        if (Minecraft.getMinecraft().thePlayer == null) {
            return;
        }
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/hub");
    }

    public static String convInteger(Integer integer) {
        switch (integer) {
            case (0): return "A";
            case (1): return "B";
            case (2): return "C";
            case (3): return "D";
            case (4): return "E";
            case (5): return "F";
            case (6): return "G";
            case (7): return "H";
            case (8): return "I";
            case (9): return "J";
            case (10): return "K";
            case (11): return "L";
            case (12): return "M";
            case (13): return "N";
            case (14): return "O";
            case (15): return "P";
            default: return "X";
        }
    }

    public static void obstaclesReadSegments() {
        if (Minecraft.getMinecraft() == null) {
            return;
        }
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) {
            return;
        }
        if (mc.theWorld == null) {
            return;
        }
        EntityPlayerSP player = mc.thePlayer;

        BlockPos playerPos = player.getPosition();
        BlockPos startPosition = new BlockPos(
                (Math.round((float) playerPos.getX() / 10000) * 10000) - 8,
                100,
                1983
        );
        if (startPosition.getX() > 0) {
            startPosition = startPosition.add(new BlockPos(-1, 0, 0));
        }
        BlockPos endPosition = new BlockPos(
                (Math.round((float) playerPos.getX() / 10000) * 10000) - 18,
                110,
                1993
        );
        if (endPosition.getX() > 0) {
            endPosition = endPosition.add(new BlockPos(-1, 0, 0));
        }

        for (int iteration = 0; iteration < 6; iteration++) {
            if (iteration != 0) {
                endPosition = endPosition.add(new BlockPos(-15, 0, 0));
                startPosition = startPosition.add(new BlockPos(-15, 0, 0));
            }

            VoxylUtils.informPlayer(Integer.toString(iteration));
            VoxylUtils.informPlayer(startPosition.toString());
            VoxylUtils.informPlayer(endPosition.toString());

            StringBuilder builder = new StringBuilder();

            // x+, y+, z+
            for (int x = endPosition.getX(); x<=startPosition.getX(); x++) {
                for (int y = startPosition.getY(); y<=endPosition.getY(); y++) {
                    for (int z = startPosition.getZ(); z<=endPosition.getZ(); z++) {
                        IBlockState state = mc.theWorld.getBlockState(new BlockPos(x, y, z));
                        if (state == null) {
                            continue;
                        }
                        Block block = state.getBlock();
                        if (block == Blocks.air) {
                            builder.append("a");  // air
                        } else if (block == Blocks.stone) {
                            if (state.getValue(BlockStone.VARIANT) == BlockStone.EnumType.ANDESITE) {
                                builder.append("b");  // andesite
                            } else {
                                builder.append("c");  // stone
                            }
                        } else if (block == Blocks.cobblestone) {
                            builder.append("d");  // cobblestone
                        } else if (block == Blocks.stained_hardened_clay) {
                            builder.append("e");  // green hardened clay
                        } else {
                            builder.append("u");  // unknown
                        }
                    }
                }
            }
            System.out.println(builder);
        }
    }

    public Long lastLactateTime = 0L;  // this is cursed

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!VoxylUtils.isInBWPLobby()) {
            return;
        }
        if (VoxylEnhanced.settings.autoLactateInterval == 0) {
            return;
        }
        if (lastLactateTime == 0 || lastLactateTime < System.currentTimeMillis()-(VoxylEnhanced.settings.autoLactateInterval * 1000L)) {
            lastLactateTime = System.currentTimeMillis();
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/lactate");
        }
    }

    public void reset() {

    }
}
