package com.quasar.voxylenhanced.obstacles;

import java.util.EnumMap;
import java.util.HashMap;

@SuppressWarnings("SpellCheckingInspection")
public class VoxylObstaclesSegments {
    enum Segment {
        THREE_BLOCKS,
        THREE_JAGGED_ISLANDS,
        THREE_PILLARS,
        THREE_PLATFORMS,
        ARCH,
        ASCENDING_PILLARS,
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
        GET_UP_VIA_PILLARS,  // not in image dataset
        HEART_PLATFORMS,
        HOLE_IN_THE_WALL,  // not in image dataset
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
        RANDOM_ISLANDS_TWO,  // not in image dataset
        RANDOM_NORMAL,
        RANDOM_SPACED,
        STAIRS,
        SWORD,
        TERRAIN,
        TINY_PILLARS,
        TWO_ISLANDS,
        TWO_WIDE_PILLARS,
        UNIFORM_HORIZONTAL_PILLARS,
        VERTICAL_PILLARS,  // not in image dataset
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
    public static HashMap<String, Segment> hashes = new HashMap<>();

    public static void init() {
        speedScores.put(Segment.THREE_BLOCKS, 1.7f);
        speedScores.put(Segment.THREE_JAGGED_ISLANDS, 2.6f);
        speedScores.put(Segment.THREE_PILLARS, 1.8f);
        speedScores.put(Segment.THREE_PLATFORMS, 1.5f);
        speedScores.put(Segment.ARCH, 2f);
        speedScores.put(Segment.ASCENDING_PILLARS, 3.5f);
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
        speedScores.put(Segment.GET_UP_VIA_PILLARS, 2.7f);
        speedScores.put(Segment.HEART_PLATFORMS, 2.8f);
        speedScores.put(Segment.HOLE_IN_THE_WALL, 5f);
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
        speedScores.put(Segment.RANDOM_ISLANDS_TWO, 3f);
        speedScores.put(Segment.RANDOM_NORMAL, 2.4f);
        speedScores.put(Segment.RANDOM_SPACED, 2.7f);
        speedScores.put(Segment.STAIRS, 2.9f);
        speedScores.put(Segment.SWORD, 2.0f);
        speedScores.put(Segment.TERRAIN, 2.1f);
        speedScores.put(Segment.TINY_PILLARS, 2.4f);
        speedScores.put(Segment.TWO_ISLANDS, 2.9f);
        speedScores.put(Segment.TWO_WIDE_PILLARS, 2.4f);
        speedScores.put(Segment.UNIFORM_HORIZONTAL_PILLARS, 2.9f);
        speedScores.put(Segment.VERTICAL_PILLARS, 2.5f);
        speedScores.put(Segment.WALL, 4f);
        speedScores.put(Segment.WALL_JUMP_DOWN, 3.2f);
        speedScores.put(Segment.WALL_PILLAR_PILLAR, 2.7f);
        speedScores.put(Segment.WALL_RUN, 1.7f);
        speedScores.put(Segment.WALL_RUN_PILLARS, 1.8f);
        speedScores.put(Segment.WALL_TO_PILLARS, 2.5f);
        speedScores.put(Segment.WIGGLE_RAVINE, 2f);
        speedScores.put(Segment.UNKNOWN, 1000f);

        hashes.put("91dbf01205af94fa3ac56ce668061c1b", Segment.LADDER_PILLAR);
        hashes.put("983bd5c3dd118985eb2566a254fcdc07", Segment.ONLY_HORIZONTAL_PILLARS);  // this
        hashes.put("e780feb9f12fa1375eb32d66f0f42f1d", Segment.RAILS);
        hashes.put("0b54dd47f9fcdceef2cbe2b80d2123e3", Segment.RAILS);
        hashes.put("b0afb1bbb849f423dfd5bb256d684a25", Segment.DOUBLE_RAVINE);
        hashes.put("c565859487eccbe5f8ee08d77a325b61", Segment.DOUBLE_RAVINE);
        hashes.put("c098d9027dd128744ab28036927bba66", Segment.HORIZONTAL_PILLARS);
        hashes.put("871c3361c62abb36e47187e39ca59c8d", Segment.HORIZONTAL_PILLARS);
        hashes.put("8a3a13bf84f7a843f86bb9b85abdad37", Segment.DENSE_HORIZONTAL_PILLARS);
        hashes.put("5d4243f8089cb419c156d28ee6268666", Segment.DENSE_HORIZONTAL_PILLARS);
        hashes.put("5e309810820bf74cd9403698ed5e4a91", Segment.BROKEN_PILLARS);
        hashes.put("5667eb95c7211e025d35a3047a57566b", Segment.BROKEN_PILLARS);
        hashes.put("3e8e5cae9756886a332e2e0d2f0a858d", Segment.THREE_BLOCKS);
        hashes.put("1c96f879aa67e2f8bf10d448daf06733", Segment.RANDOM_SPACED);
        hashes.put("17c03b6f6aa2c9453d906ee93cc5b452", Segment.RANDOM_SPACED);
        hashes.put("153cb2fa1eebba19de254b397d5b3d29", Segment.JUMP_GAP);
        hashes.put("a6d6d71fdc369cff7a73587a20d7712b", Segment.JUMP_GAP);
        hashes.put("190f9d5f521e9e09bc4e85a474451301", Segment.WALL_RUN_PILLARS);
        hashes.put("a2d9bd6bf5865f8fcf523e473b90c29e", Segment.WALL_RUN);
        hashes.put("aca7033e7d70542925a3f1f1fb8b5bdb", Segment.WALL_RUN);
        hashes.put("6ff5bee78b1899bb8229b451ffe8450b", Segment.DIAGONAL_RAVINE);
        hashes.put("b558c2a5e3890c404307650002eb4509", Segment.DIAGONAL_RAVINE);
        hashes.put("5ca6be8b94ec7234a19cb2b9ccab7a38", Segment.TINY_PILLARS);
        hashes.put("6958d9a6b143bdac2152ec09fcd25ea3", Segment.TINY_PILLARS);
        hashes.put("3f434005d7fbc34f862aa4c81ddfe8e9", Segment.WIGGLE_RAVINE);  // this
        hashes.put("64e669f7c73fec24285f4a11dc38e56c", Segment.WALL);
        hashes.put("6f992b716fcc3df867cf89b53490d8fc", Segment.WALL);
        hashes.put("3f65bd836a6d7869558f44a9a67f9808", Segment.HEART_PLATFORMS);
        hashes.put("66162aa85ae63d75df7aeb8cfb2c250b", Segment.HEART_PLATFORMS);
        hashes.put("3d25b65efe35c09dcd75945f5cee0aee", Segment.DESCENDING_WALLS);
        hashes.put("3c36e74811dffc06ebb8211543762d19", Segment.DESCENDING_WALLS);
        hashes.put("ff3b17501a32d8563fb0100788e82f75", Segment.WALL_TO_PILLARS);
        hashes.put("d0f313f3e12016d4e2ece21c0883c6b6", Segment.WALL_TO_PILLARS);
        hashes.put("68a1c38a65c6e12190647bda4d181172", Segment.TERRAIN);
        hashes.put("572cad60d223d7cfa8b49c5f222ca23e", Segment.TERRAIN);
        hashes.put("3bf4dec6b661039e590c17706a155073", Segment.THREE_JAGGED_ISLANDS);
        hashes.put("f8dca463e7b5ad76460e636c3adac624", Segment.THREE_JAGGED_ISLANDS);
        hashes.put("11b6f32b6d808e0d6d3b348340a72c4c", Segment.STAIRS);
        hashes.put("98894b1b8908ab2bf62d0fbbb3a820ad", Segment.STAIRS);
        hashes.put("fe9414b7ab74f0ac2cae39715b0311e1", Segment.THREE_PILLARS);
        hashes.put("56fd2f66279b39a5676a1169b07071f8", Segment.THREE_PILLARS);
        hashes.put("894be322dad85bfea17b35296d191c5c", Segment.IMPOSSIBLE);
        hashes.put("722acd5ef8162ba3631e9b35cd0a1b62", Segment.IMPOSSIBLE);
        hashes.put("b31a9a56681463db38a1fe5138dc46d5", Segment.BROKEN_WALL);
        hashes.put("d5137114d9c2695e4478d5a79b50985b", Segment.CENTRAL_RAVINE);
        hashes.put("9f982857d0e4a41151be3fab868c2264", Segment.CENTRAL_RAVINE);
        hashes.put("d69162c83ee828054347454253ee31f0", Segment.SWORD);
        hashes.put("3bdaa53a57d28e0e7bbc1048a9eefa90", Segment.SWORD);
        hashes.put("2e263f45ef23ec77b8d3ede4a0fd97fe", Segment.ARCH);
        hashes.put("c687e54fc95bb13f5d4d7fcde1584064", Segment.ARCH);
        hashes.put("8e1d7867b95c6cbf3249e56bb3338848", Segment.JUMP_UP_TWO);
        hashes.put("6d27280f4fa0d45f67648311851dabfc", Segment.JUMP_UP_TWO);
        hashes.put("65184c5653023d7b18fb18bc7ead9155", Segment.DRAGON);
        hashes.put("ff5d9504c3766ce7962bd5be256e824d", Segment.DRAGON);
        hashes.put("073b8039b60394be493dc2a39b101683", Segment.DENSE_PILLARS);
        hashes.put("ef51905a2e0ed40acea2fe6a238fdb5e", Segment.DENSE_PILLARS);
        hashes.put("f7e99faedb0ace120ad5de191c2edf44", Segment.RANDOM_DENSE);
        hashes.put("c0593948611e8c660450f9c80a8b3657", Segment.RANDOM_DENSE);
        hashes.put("6ea5876c9a89abeacc2ff6882bcc8558", Segment.LARGE_HORIZONTAL_PILLARS);
        hashes.put("526b9cbc9820e9ceaea7ed406731a226", Segment.LARGE_HORIZONTAL_PILLARS);
        hashes.put("b55e02745bbbd40bd06c0e55309887de", Segment.TWO_ISLANDS);
        hashes.put("fc87fe3651f50c9a176099c550213b29", Segment.TWO_ISLANDS);
        hashes.put("5c827a17012c67a39a0c298f9e0e17ce", Segment.TWO_ISLANDS);
        hashes.put("0f82f1c537b5fe7576c67eadd7c114a3", Segment.HOLE_IN_THE_WALL);
        hashes.put("e46b698c2dd9a9bee1025309e3437854", Segment.HOLE_IN_THE_WALL);
        hashes.put("da797cf553c600d9abe629415354cf2c", Segment.HORIZONTAL_AND_VERTICAL_PILLARS_TWO);
        hashes.put("ee59009072bf6818c44edd36538575a8", Segment.HORIZONTAL_AND_VERTICAL_PILLARS_TWO);
        hashes.put("ebf36cda88512527a12911f4aef25c6b", Segment.BRIDGE_WALL_START);
        hashes.put("26cbd195591622ebf43387ee4af4f0e4", Segment.BRIDGE_WALL_START);
        hashes.put("589a02ae497adc4c048898b71119a939", Segment.JUMP_AROUND);  // this
        hashes.put("a6cb00d0ed0ee1044d4be37ef2c9d44f", Segment.WALL_JUMP_DOWN);
        hashes.put("c02e8c4822ca18234e63080fef9c8a4f", Segment.WALL_JUMP_DOWN);
        hashes.put("3a1be2f621a5db6ff76052920dcf0f96", Segment.WALL_JUMP_DOWN);
        hashes.put("b6f7075e5d444f1c4bf6a087a72eb9a4", Segment.RANDOM_NORMAL);
        hashes.put("636545e0a43d870810cdd06e8f324ce2", Segment.RANDOM_NORMAL);
        hashes.put("fe9795c860ab5e9afe089bc917de8612", Segment.RANDOM_ISLANDS);  // this
        hashes.put("fcde0848ada41fcacad03a7967087f91", Segment.BRIDGE_START);  // this
        hashes.put("29972cebd703fd2ed86ab74974e2e19f", Segment.THREE_PLATFORMS);
        hashes.put("b2d5cacd2479ab301ef3d17221e568d2", Segment.THREE_PLATFORMS);
        hashes.put("3cb7515a26cc9b5c0bc785c6f15a9890", Segment.VERTICAL_PILLARS);  // this
        hashes.put("a9e329f0780e865259c38c657ae6d16e", Segment.GET_UP_VIA_PILLARS);
        hashes.put("77e78cdb522aa07ec7155fab0169b30e", Segment.GET_UP_VIA_PILLARS);
        hashes.put("7d9b5a73820af84e8c38bddda6d0c032", Segment.FLOATING_PILLARS);
        hashes.put("f760d303e6cbf7e5659b8dbfac7e9372", Segment.FLOATING_PILLARS);
        hashes.put("285cf6d20753046f563e56e6e0f68226", Segment.HORIZONTAL_AND_VERTICAL_PILLARS);
        hashes.put("ae6f5d68262fc90c34829e0131f4051b", Segment.HORIZONTAL_AND_VERTICAL_PILLARS);
        hashes.put("196505e95a0ca0f0ed869c0ffad28069", Segment.HORIZONTAL_AND_VERTICAL_PILLARS);
        hashes.put("d84069f95f295a2125895d5b02de23ed", Segment.ASCENDING_PILLARS);
        hashes.put("ad0a471e50acad0e8caf2ea46efdad0d", Segment.ASCENDING_PILLARS);
        hashes.put("0506c04d497f060d3f2cee98098a6957", Segment.CRACKED_WALLS);
        hashes.put("1571dc48f3f7abaa30b3a546f1f23d8b", Segment.CRACKED_WALLS);
        hashes.put("84143ba676ee161c6d912e3367a02ba3", Segment.WALL_PILLAR_PILLAR);
        hashes.put("88daad86894a16d7398d3a3c92e40980", Segment.WALL_PILLAR_PILLAR);
        hashes.put("c65799f23a34db732ecc603b91093b6f", Segment.UNIFORM_HORIZONTAL_PILLARS);
        hashes.put("eb41f6e5b18f4638a0632e56506de367", Segment.RANDOM_ISLANDS_TWO);
        hashes.put("9981063b908fc787d8903a5d86c71d76", Segment.RANDOM_ISLANDS_TWO);
        hashes.put("e2331b791205a72b1b5b2add2029064e", Segment.RANDOM_ISLANDS_TWO);
        hashes.put("2c84d5115e7a47c9815a0c565498f202", Segment.TWO_WIDE_PILLARS);
        hashes.put("ee1beb6ae76294ca4c75300fd4a09805", Segment.TWO_WIDE_PILLARS);
        hashes.put("3037d07f2643b3afc44d792180cb1bfb", Segment.LARGE_PILLARS);
        hashes.put("58df7ce48a8443a251feb386ea37e953", Segment.LARGE_PILLARS);
        hashes.put("b0f76c3d0dbd0f77b7a6155ae72b1ab1", Segment.DIAGONAL_WALLS);
        hashes.put("7ad0ad5152c95839b06aeb774c0f0d1f", Segment.DIAGONAL_WALLS);
        hashes.put("5ba794ae43e6f45ab9ce3e9d8a201c8c", Segment.DENSE_VERTICAL_PILLARS);
        hashes.put("8b9eba6f1be924da61433ea2f1da9a59", Segment.DENSE_VERTICAL_PILLARS);
        hashes.put("faf31d12567e4eb1eb785e08195c5d98", Segment.WIGGLE_RAVINE);  // this
        hashes.put("78c6c01fb0563282d8eb1760267ec07c", Segment.CLAW);
        hashes.put("d46e6ff53718bfa012f34e233793817c", Segment.CLAW);
        hashes.put("49b27c59ad241b95446770952cf418a3", Segment.FLOWER_TOWER);
        hashes.put("c979e7244b7e7c78fbac6f3e221c1137", Segment.FLOWER_TOWER);
    }

    public static Segment getSegmentFromHash(String hash) {
        return hashes.getOrDefault(hash, Segment.UNKNOWN);
    }
}
