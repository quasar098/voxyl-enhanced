package com.quasar.voxylenhanced.obstacles;

import java.util.EnumMap;

@SuppressWarnings("SpellCheckingInspection")
public class VoxylObstaclesSegments {
    enum Segment {
        THREE_BLOCKS,
        THREE_JAGGED_ISLANDS,
        THREE_PILLARS,
        THREE_PLATFORMS,
        ARCH,
        ASCENTING_PILLARS,
        BRIDGE_START,
        BRIDGE_WALL_START,
        BROKEN_PILLARS,
        BROKEN_WALL,
        CENTRAL_RAVINE,
        CLAW,
        CRACKED_WALLS,
        DENSE_PILLARS,
        DENSE_HORIZONTAL_PILLARS,
        DENSE_VERTICAL_PILLARS,
        DESCENDING_WALLS,
        DIAGONAL_RAVINE,
        DIAGONAL_WALLS,
        DOUBLE_RAVINE,
        DRAGON,
        FLOATING_PILLARS,
        FLOWER_TOWER,
        HEART_PLATFORMS,
        HORIZONTAL_AND_VERTICAL_PILLARS,
        HORIZONTAL_AND_VERTICAL_PILLARS_TWO,
        HORIZONTAL_PILLARS,
        IMPOSSIBLE,
        JUMP_AROUND,
        JUMP_GAP,
        JUMP_UP_TWO,
        LADDER_PILLAR,
        LARGE_HORIZONTAL_PILLARS,
        LARGE_PILLARS,
        ONLY_HORIZONTAL_PILLARS,
        RAILS,
        RANDOM_DENSE,
        RANDOM_ISLANDS,
        RANDOM_NORMAL,
        RANDOM_SPACED,
        STAIRS,
        SWORD,
        TERRAIN,
        TINY_PILLARS,
        TWO_ISLANDS,
        TWO_WIDE_PILLARS,
        UNIFORM_HORIZONTAL_PILLARS,
        WALL,
        WALL_JUMP_DOWN,
        WALL_PILLAR_PILLAR,
        WALL_RUN,
        WALL_RUN_PILLARS,
        WALL_TO_PILLARS,
        WIGGLE_RAVINE,

        UNKNOWN
    }

    public static EnumMap<Segment, Float> speedScores = new EnumMap<>(Segment.class);
    public static EnumMap<Segment, String> hashes = new EnumMap<>(Segment.class);

    public static void init() {
        speedScores.put(Segment.THREE_BLOCKS, 1.7f);
        speedScores.put(Segment.THREE_JAGGED_ISLANDS, 2.6f);
        speedScores.put(Segment.THREE_PILLARS, 1.8f);
        speedScores.put(Segment.THREE_PLATFORMS, 1.5f);
        speedScores.put(Segment.ARCH, 2f);
        speedScores.put(Segment.ASCENTING_PILLARS, 3.5f);
        speedScores.put(Segment.BRIDGE_START, 2.7f);
        speedScores.put(Segment.BRIDGE_WALL_START, 2.5f);
        speedScores.put(Segment.BROKEN_PILLARS, 3f);
        speedScores.put(Segment.BROKEN_WALL, 2.5f);
        speedScores.put(Segment.CENTRAL_RAVINE, 2.2f);
        speedScores.put(Segment.CLAW, 2.4f);
        speedScores.put(Segment.CRACKED_WALLS, 2.1f);
        speedScores.put(Segment.DENSE_HORIZONTAL_PILLARS, 1.3f);
        speedScores.put(Segment.DENSE_PILLARS, 2f);
        speedScores.put(Segment.DENSE_VERTICAL_PILLARS, 3.6f);
        speedScores.put(Segment.DESCENDING_WALLS, 1.8f);
        speedScores.put(Segment.DIAGONAL_RAVINE, 2.4f);
        speedScores.put(Segment.DIAGONAL_WALLS, 2.6f);
        speedScores.put(Segment.DOUBLE_RAVINE, 1.9f);
        speedScores.put(Segment.DRAGON, 2.6f);
        speedScores.put(Segment.FLOATING_PILLARS, 2.6f);
        speedScores.put(Segment.FLOWER_TOWER, 3.8f);
        speedScores.put(Segment.HEART_PLATFORMS, 2.8f);
        speedScores.put(Segment.HORIZONTAL_AND_VERTICAL_PILLARS, 1.7f);
        speedScores.put(Segment.HORIZONTAL_AND_VERTICAL_PILLARS_TWO, 1.9f);
        speedScores.put(Segment.HORIZONTAL_PILLARS, 3.6f);
        speedScores.put(Segment.IMPOSSIBLE, 4f);
        speedScores.put(Segment.JUMP_AROUND, 4f);
        speedScores.put(Segment.JUMP_GAP, 1.4f);
        speedScores.put(Segment.JUMP_UP_TWO, 1.5f);
        speedScores.put(Segment.LADDER_PILLAR, 2.3f);
        speedScores.put(Segment.LARGE_HORIZONTAL_PILLARS, 1.7f);
        speedScores.put(Segment.LARGE_PILLARS, 2.2f);
        speedScores.put(Segment.ONLY_HORIZONTAL_PILLARS, 2.7f);
        speedScores.put(Segment.RAILS, 2.6f);
        speedScores.put(Segment.RANDOM_DENSE, 2.0f);
        speedScores.put(Segment.RANDOM_ISLANDS, 2.3f);
        speedScores.put(Segment.RANDOM_NORMAL, 2.4f);
        speedScores.put(Segment.RANDOM_SPACED, 2.7f);
        speedScores.put(Segment.STAIRS, 2.9f);
        speedScores.put(Segment.SWORD, 2.0f);
        speedScores.put(Segment.TERRAIN, 2.1f);
        speedScores.put(Segment.TINY_PILLARS, 2.4f);
        speedScores.put(Segment.TWO_ISLANDS, 2.9f);
        speedScores.put(Segment.TWO_WIDE_PILLARS, 2.4f);
        speedScores.put(Segment.UNIFORM_HORIZONTAL_PILLARS, 2.9f);
        speedScores.put(Segment.WALL, 4f);
        speedScores.put(Segment.WALL_JUMP_DOWN, 3.2f);
        speedScores.put(Segment.WALL_PILLAR_PILLAR, 2.7f);
        speedScores.put(Segment.WALL_RUN, 1.7f);
        speedScores.put(Segment.WALL_RUN_PILLARS, 1.8f);
        speedScores.put(Segment.WALL_TO_PILLARS, 2.5f);
        speedScores.put(Segment.WIGGLE_RAVINE, 2f);
        speedScores.put(Segment.UNKNOWN, 2.4f);

        hashes.put(Segment.LADDER_PILLAR, "91dbf01205af94fa3ac56ce668061c1b");
        hashes.put(Segment.TERRAIN, "572cad60d223d7cfa8b49c5f222ca23e");
        hashes.put(Segment.ONLY_HORIZONTAL_PILLARS, "983bd5c3dd118985eb2566a254fcdc07");
    }
}
