package dartcraftReloaded.world;

import dartcraftReloaded.blocks.ModBlocks;
import dartcraftReloaded.config.ConfigHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class DCRWorldGen implements IWorldGenerator {

    //World Generator
    private final WorldGenerator forceOre;

    public DCRWorldGen(){
        forceOre = new WorldGenMinable(ModBlocks.orePower.getDefaultState(), 6);
    }

    private void runGenerator(WorldGenerator generator, World world, Random rand, int chunk_X, int chunk_Z, int chancesToSpawn, int minHeight, int maxHeight) {
        if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
            throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");

        int heightDiff = maxHeight - minHeight + 1;
        for (int i = 0; i < chancesToSpawn; i ++) {
            int x = chunk_X * 16 + rand.nextInt(8) + 8;
            int y = minHeight + rand.nextInt(heightDiff);
            int z = chunk_Z * 16 + rand.nextInt(8) + 8;
            generator.generate(world, rand, new BlockPos(x, y, z));
        }
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider){
        if (ConfigHandler.genChances > 0) {
            //Overworld
            if (world.provider.getDimension() == 0) {
                if (!(world.getWorldType() == WorldType.FLAT)) {
                    this.runGenerator(forceOre, world, random, chunkX, chunkZ, ConfigHandler.genChances, 0, 64);
                }
            }
        }

    }
}