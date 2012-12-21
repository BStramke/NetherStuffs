package bstramke.NetherStuffs.WorldGen;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenNetherStuffsMinable implements IWorldGenerator {

	/** The block ID of the ore to be placed using this generator. */
	private int minableBlockId;
	private int minableBlockMeta;
	private int spawnchance = 100;

	/** The number of blocks to generate. */
	private int numberOfBlocks;

	public WorldGenNetherStuffsMinable(int blockId, int meta, int numBlocks, int spawnchance) {
		this.minableBlockId = blockId;
		this.numberOfBlocks = numBlocks;
		this.minableBlockMeta = meta;
		this.spawnchance = spawnchance;
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.isHellWorld)
			generateNether(world, random, chunkX * 16, chunkZ * 16);

	}

	private void generateNether(World world, Random random, int i, int j) {
		for (int j1 = 0; j1 < 50; j1++) {
			int Xcoordinate = i + random.nextInt(16);
			int Zcoordinate = j + random.nextInt(16);
			int Ycoordinate = random.nextInt(128);
			new GenerateNetherStuffsMinable(minableBlockId, minableBlockMeta, numberOfBlocks, spawnchance).generate(world, random, Xcoordinate, Ycoordinate, Zcoordinate);
		}

	}

}
