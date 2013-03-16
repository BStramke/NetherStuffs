package bstramke.NetherStuffs.WorldGen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.NetherWood;
import bstramke.NetherStuffs.Common.BlockNotifyType;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenNetherStuffsTrees extends WorldGenerator implements IWorldGenerator {
	/** The minimum height of a generated tree. */
	private final int minTreeHeight;

	/** True if this tree should grow Vines. */
	private final boolean vinesGrow;

	/** The metadata value of the wood to use in tree generation. */
	private int metaWood;

	/** The metadata value of the leaves to use in tree generation. */
	private int metaLeaves;

	public WorldGenNetherStuffsTrees(boolean par1, int metaWood) {
		this(par1, 4, false);
		this.metaWood = metaWood;
		this.metaLeaves = metaWood;
	}

	public WorldGenNetherStuffsTrees(boolean par1, int par2, boolean par5) {
		super(par1);
		this.minTreeHeight = par2;
		this.vinesGrow = par5;
	}

	@Override
	public boolean generate(World par1World, Random par2Random, int xCoord, int yCoord, int zCoord) {
		int nMaxHeight = par2Random.nextInt(3) + this.minTreeHeight;
		boolean validTreePos = true;

		if (yCoord >= 1 && yCoord + nMaxHeight + 1 <= 256) {
			byte var9;
			int var11;
			int var12;

			for (int yPosUp = yCoord; yPosUp <= yCoord + 1 + nMaxHeight; ++yPosUp) {
				var9 = 1; // check surroundings on x-pos?

				if (yPosUp == yCoord) {
					var9 = 0;
				}

				if (yPosUp >= yCoord + 1 + nMaxHeight - 2) {
					var9 = 2;
				}

				for (int xPosAround = xCoord - var9; xPosAround <= xCoord + var9 && validTreePos; ++xPosAround) {
					for (int zPosAround = zCoord - var9; zPosAround <= zCoord + var9 && validTreePos; ++zPosAround) {
						if (yPosUp >= 0 && yPosUp < 256) {
							int blockIdAtPos = par1World.getBlockId(xPosAround, yPosUp, zPosAround);

							Block block = Block.blocksList[blockIdAtPos];

							if (blockIdAtPos != 0 && !block.isLeaves(par1World, xPosAround, yPosUp, zPosAround) && blockIdAtPos != Block.netherrack.blockID &&
							// blockIdAtPos != Block.dirt.blockID &&
									!block.isWood(par1World, xPosAround, yPosUp, zPosAround)) {
								validTreePos = false;
							}
						} else {
							validTreePos = false;
						}
					}
				}
			}

			if (!validTreePos) {
				return false;
			} else {
				int var8 = par1World.getBlockId(xCoord, yCoord - 1, zCoord);

				if (yCoord < 128 - nMaxHeight - 1 && var8 == Block.netherrack.blockID) {
					// this.setBlock(par1World, xCoord, yCoord - 1, zCoord, Block.dirt.blockID);
					var9 = 3;
					byte var18 = 0;
					int var13;
					int var14;
					int var15;

					ArrayList leavePositions = new ArrayList();

					for (var11 = yCoord - var9 + nMaxHeight; var11 <= yCoord + nMaxHeight; ++var11) {
						var12 = var11 - (yCoord + nMaxHeight);
						var13 = var18 + 1 - var12 / 2;

						for (var14 = xCoord - var13; var14 <= xCoord + var13; ++var14) {
							var15 = var14 - xCoord;

							for (int var16 = zCoord - var13; var16 <= zCoord + var13; ++var16) {
								int var17 = var16 - zCoord;

								Block block = Block.blocksList[par1World.getBlockId(var14, var11, var16)];

								if ((Math.abs(var15) != var13 || Math.abs(var17) != var13 || par2Random.nextInt(2) != 0 && var12 != 0)
										&& (block == null || block.canBeReplacedByLeaves(par1World, var14, var11, var16))) {
									this.setBlockAndMetadata(par1World, var14, var11, var16, NetherStuffs.NetherLeavesBlockId, this.metaLeaves);
									if (this.metaWood == NetherWood.hellfire)
										leavePositions.add(Arrays.asList(var14, var11, var16));
								}
							}
						}
					}

					int nCountFirespawn = 0;
					int nFirespawnMax = 4;
					for (int index = 0; index < leavePositions.size() && nCountFirespawn < nFirespawnMax; index++) {
						int x = (Integer) ((List) leavePositions.get(index)).get(0);
						int y = (Integer) ((List) leavePositions.get(index)).get(1);
						int z = (Integer) ((List) leavePositions.get(index)).get(2);

						if (par1World.getBlockId(x, y + 1, z) != 0)
							continue;

						if (par2Random.nextInt(5) == 0) {
							par1World.setBlockAndMetadataWithNotify(x, y + 1, z, Block.fire.blockID, 0, BlockNotifyType.ALL);
							nCountFirespawn++;
						}
					}

					for (var11 = 0; var11 < nMaxHeight; ++var11) {
						var12 = par1World.getBlockId(xCoord, yCoord + var11, zCoord);

						Block block = Block.blocksList[var12];

						if (var12 == 0 || block == null || block.isLeaves(par1World, xCoord, yCoord + var11, zCoord)) {

							this.setBlockAndMetadata(par1World, xCoord, yCoord + var11, zCoord, NetherStuffs.NetherWoodBlockId, this.metaWood);

							/*
							 * if (this.vinesGrow && var11 > 0) { if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(xcoord - 1, yCoord + var11, zCoord)) { this.setBlockAndMetadata(par1World, xcoord - 1,
							 * yCoord + var11, zCoord, Block.vine.blockID, 8); }
							 * 
							 * if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(xcoord + 1, yCoord + var11, zCoord)) { this.setBlockAndMetadata(par1World, xcoord + 1, yCoord + var11, zCoord,
							 * Block.vine.blockID, 2); }
							 * 
							 * if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(xcoord, yCoord + var11, zCoord - 1)) { this.setBlockAndMetadata(par1World, xcoord, yCoord + var11, zCoord - 1,
							 * Block.vine.blockID, 1); }
							 * 
							 * if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(xcoord, yCoord + var11, zCoord + 1)) { this.setBlockAndMetadata(par1World, xcoord, yCoord + var11, zCoord + 1,
							 * Block.vine.blockID, 4); } }
							 */
						}
					}

					/*
					 * if (this.vinesGrow) { for (var11 = yCoord - 3 + var6; var11 <= yCoord + var6; ++var11) { var12 = var11 - (yCoord + var6); var13 = 2 - var12 / 2;
					 * 
					 * for (var14 = xcoord - var13; var14 <= xcoord + var13; ++var14) { for (var15 = zCoord - var13; var15 <= zCoord + var13; ++var15) { Block block =
					 * Block.blocksList[par1World.getBlockId(var14, var11, var15)]; if (block != null && block.isLeaves(par1World, var14, var11, var15)) { if (par2Random.nextInt(4) == 0 &&
					 * par1World.getBlockId(var14 - 1, var11, var15) == 0) { this.growVines(par1World, var14 - 1, var11, var15, 8); }
					 * 
					 * if (par2Random.nextInt(4) == 0 && par1World.getBlockId(var14 + 1, var11, var15) == 0) { this.growVines(par1World, var14 + 1, var11, var15, 2); }
					 * 
					 * if (par2Random.nextInt(4) == 0 && par1World.getBlockId(var14, var11, var15 - 1) == 0) { this.growVines(par1World, var14, var11, var15 - 1, 1); }
					 * 
					 * if (par2Random.nextInt(4) == 0 && par1World.getBlockId(var14, var11, var15 + 1) == 0) { this.growVines(par1World, var14, var11, var15 + 1, 4); } } } } }
					 * 
					 * if (par2Random.nextInt(5) == 0 && var6 > 5) { for (var11 = 0; var11 < 2; ++var11) { for (var12 = 0; var12 < 4; ++var12) { if (par2Random.nextInt(4 - var11) == 0) { var13 =
					 * par2Random.nextInt(3); this.setBlockAndMetadata(par1World, xcoord + Direction.offsetX[Direction.footInvisibleFaceRemap[var12]], yCoord + var6 - 5 + var11, zCoord +
					 * Direction.offsetZ[Direction.footInvisibleFaceRemap[var12]], Block.cocoaPlant.blockID, var13 << 2 | var12); } } } } }
					 */

					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.isHellWorld) {
			if (random.nextInt(9) < 2) // chance of 20% to spawn a tree group in that chunk at all
			{
				for (int j1 = 0; j1 < 2; j1++) { // groups of 2 trees (may spawn if appropriate!)
					int Xcoordinate = chunkX * 16 + random.nextInt(16);
					int Zcoordinate = chunkZ * 16 + random.nextInt(16);
					for (int y = 1; y < 128; y++) // will determine the lowest block in the height level that is capable of holding the tree
					{
						if (world.getBlockId(Xcoordinate, y - 1, Zcoordinate) == Block.netherrack.blockID && world.getBlockId(Xcoordinate, y, Zcoordinate) == 0) {
							boolean bValid = true;
							for (int y1 = y + 1; y1 < y + this.minTreeHeight && bValid && y1 < 128; y1++) {
								if (world.getBlockId(Xcoordinate, y1, Zcoordinate) != 0)
									bValid = false;
							}

							if (bValid) {
								this.metaWood = random.nextInt(3); // random guess what wood we will place
								this.metaLeaves = this.metaWood; // this should contain the matching Leaves Damage Value
								// System.out.println(Xcoordinate+","+ y+","+ Zcoordinate);
								generate(world, random, Xcoordinate, y, Zcoordinate);
								y = 256; // exit the loop
							}
						}
					}
				}
			}
		}
	}
}
