package com.quasar.voxylenhanced.misc;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.StringJoiner;

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

    public static void obstaclesReadSegment(int offsetX) {
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
        BlockPos startPos = mc.thePlayer.getPosition().add(-25+offsetX, -2, -6);
        BlockPos endPos = startPos.add(10, 10, 10);
        Iterable<BlockPos> positions = BlockPos.getAllInBox(startPos, endPos);

        HashMap<BlockPos, Integer> blockTypes = new HashMap<>();
        // 0=air, 1=andesite, 2=stone, 3=cobblestone, 4=clay, 99=unknown

        System.out.println(startPos.toString());
        System.out.println(endPos.toString());

        for (BlockPos position : positions) {
            IBlockState state = mc.theWorld.getBlockState(position);
            if (state == null) {
                continue;
            }
            Block block = state.getBlock();
            BlockPos offset = position.subtract(startPos);
            if (block == Blocks.air) {
                blockTypes.put(offset, 0);
            }
            else if (block == Blocks.stone) {
                if (Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE).getBlock() == block) {
                    blockTypes.put(offset, 1);
                } else {
                    blockTypes.put(offset, 2);
                }
            } else if (block == Blocks.cobblestone) {
                blockTypes.put(offset, 3);
            } else if (block == Blocks.stained_hardened_clay) {
                blockTypes.put(offset, 4);
            } else {
                blockTypes.put(offset, 99);
            }
        }

        StringJoiner joiner = new StringJoiner(",");
        for (BlockPos pos : blockTypes.keySet()) {
            String thing = convInteger(pos.getX());
            thing += (convInteger(pos.getY()));
            thing += (convInteger(pos.getZ()));
            thing += (convInteger(blockTypes.getOrDefault(pos, 99)));
            joiner.add(thing);
        }

        System.out.println(DigestUtils.md5Hex(joiner.toString()));
    }
}
