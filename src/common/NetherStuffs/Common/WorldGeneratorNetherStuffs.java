package NetherStuffs.Common;

import java.util.Random;

import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenGlowStone1;
import net.minecraft.src.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGeneratorNetherStuffs implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.dimensionId) {
			case -1: generateNether(world, random, chunkX*16, chunkZ*16);
			//case 0: generateSurface(world, random, chunkX*16, chunkZ*16);
		}

	}

	private void generateNether(World world, Random random, int BlockX, int BlockZ) {
		for(int i = 0; i < 30; i++) { //30 veins for each chunk
			int Xcoord = BlockX+random.nextInt(16);
			int Zcoord = BlockZ+random.nextInt(16);
			int Ycoord = random.nextInt(100); // Y from 0 to 16
			
			(new WorldGenMinable(mod_NetherStuffs.DemonicOre.blockID, 20)).generate(world, random, Xcoord, Ycoord, Zcoord); //TODO: Basically this does not work cause it checks for Stone to be replaced. cant work...
			System.out.println("called");
		}
	}

}
