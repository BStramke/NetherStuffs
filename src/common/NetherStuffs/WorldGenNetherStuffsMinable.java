package NetherStuffs;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenNetherStuffsMinable implements IWorldGenerator {

	/** The block ID of the ore to be placed using this generator. */
	private int minableBlockId;
	private int minableBlockMeta = 0;

	/** The number of blocks to generate. */
	private int numberOfBlocks;

	public WorldGenNetherStuffsMinable(int blockId, int numBlocks) {
		this.minableBlockId = blockId;
		this.numberOfBlocks = numBlocks;
	}

	public WorldGenNetherStuffsMinable(int blockId, int meta, int numBlocks) {
		this(blockId, numBlocks);
		minableBlockMeta = meta;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.dimensionId) {
		case -1:
			generateNether(world, random, chunkX * 16, chunkZ * 16);
		}

	}

	private void generateNether(World world, Random random, int i, int j) {
		for (int j1 = 0; j1 < 90; j1++) {
			int Xcoordinate = i + random.nextInt(16);
			int Zcoordinate = j + random.nextInt(16);
			int Ycoordinate = random.nextInt(256);
			new GenerateNetherStuffsMinable(minableBlockId, minableBlockMeta,
					numberOfBlocks).generate(world, random, Xcoordinate,
					Ycoordinate, Zcoordinate);
		}

	}

}
