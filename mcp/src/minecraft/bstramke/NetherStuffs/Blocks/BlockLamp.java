package bstramke.NetherStuffs.Blocks;

import bstramke.NetherStuffs.Common.BlockNotifyType;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLamp extends BlockBase {
	private Icon transparentIcon;

	public BlockLamp(int par1, Material par2Material) {
		super(par1, par2Material);
		setLightValue(1.0f);
	}

	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1World, int x, int y, int z) {
		if (par1World.getBlockMetadata(x, y, z) == 0)
			setBlockBounds(0, 0, 0, 1, 1, 1);
		else
			setBlockBounds(0.25f, 0.25f, 0.25f, 0.75f, 0.75f, 0.75f);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon(CommonProxy.getIconLocation("FurnaceBottom"));
		transparentIcon = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulGlass"));
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int x, int y, int z) {
		if (par1World.getBlockMetadata(x, y, z) != 0)
			return null;

		return super.getCollisionBoundingBoxFromPool(par1World, x, y, z);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if (meta != 0)
			return transparentIcon;
		else
			return blockIcon;
	}

	@Override
	public boolean isAirBlock(World world, int x, int y, int z) {
		if (world.getBlockMetadata(x, y, z) == 0)
			return false;
		else
			return true;
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int par5, float par6, float par7, float par8, int meta) {
		if (meta == 0) {

			int maxX = 3;
			for (int i = 1; i <= 3; i++) {
				if (world.getBlockId(x + i, y, z) != 0) {
					maxX = i - 1;
					break;
				}
			}

			int minX = -3;
			for (int i = -1; i >= -3; i--) {
				if (world.getBlockId(x + i, y, z) != 0) {
					minX = i + 1;
					break;
				}
			}

			int maxY = 3;
			for (int i = 1; i <= 3; i++) {
				if (world.getBlockId(x, y + i, z) != 0) {
					maxY = i - 1;
					break;
				}
			}

			int minY = -3;
			for (int i = -1; i >= -3; i--) {
				if (world.getBlockId(x, y + i, z) != 0) {
					minY = i + 1;
					break;
				}
			}

			int maxZ = 3;
			for (int i = 1; i <= 3; i++) {
				if (world.getBlockId(x, y, z + i) != 0) {
					maxZ = i - 1;
					break;
				}
			}

			int minZ = -3;
			for (int i = -1; i >= -3; i--) {
				if (world.getBlockId(x, y, z + i) != 0) {
					minZ = i + 1;
					break;
				}
			}

			for (int i = minX; i <= maxX; i++) { // creates a 5x5x5 cube of lighting blocks
				for (int j = minY; j <= maxY; j++) {
					for (int k = minZ; k <= maxZ; k++) {
						if ((k == 3 || k == -3) && i <= 1 && j <= 1 && i >= -1 && j >= -1)
							setLightingBlock(world, x + i, y + j, z + k);
						else if ((i == 3 || i == -3) && k <= 1 && j <= 1 && k >= -1 && j >= -1)
							setLightingBlock(world, x + i, y + j, z + k);
						else if ((j == 3 || j == -3) && k <= 1 && i <= 1 && k >= -1 && i >= -1)
							setLightingBlock(world, x + i, y + j, z + k);
						else if (j >= -2 && j <= 2 && k >= -2 && k <= 2 && i >= -2 && i <= 2) {
							setLightingBlock(world, x + i, y + j, z + k);
						}
					}
				}
			}

			/*
			 * for (int i = minX; i <= maxX; i++) { // creates a 5x5x5 cube of lighting blocks for (int j = minY; j <= maxY; j++) { for (int k = minZ; k <= maxZ; k++) {
			 * setLightingBlock(world, x + i, y + j, z + k); } } }
			 * 
			 * for (int i = -1; i <= 1; i++) { // makes the outer 3x3 lighting block segments for (int j = -1; j <= 1; j++) { if (maxX == 2) setLightingBlock(world, x + 3, y + i, z +
			 * j); if (minX == -2) setLightingBlock(world, x - 3, y + i, z + j);
			 * 
			 * if (maxY == 2) setLightingBlock(world, x + i, y + 3, z + j); if (minY == -2) setLightingBlock(world, x + i, y - 3, z + j);
			 * 
			 * if (maxZ == 2) setLightingBlock(world, x + i, y + j, z + 3); if (minZ == -2) setLightingBlock(world, x + i, y + j, z - 3); } }
			 */
		}
		return meta;
	}

	private void setLightingBlock(World world, int x, int y, int z) {
		if (world.getBlockId(x, y, z) == this.blockID) {
			int nMeta = world.getBlockMetadata(x, y, z);
			if (nMeta > 0 && nMeta < 15) {
				nMeta++;
				world.setBlockMetadataWithNotify(x, y, z, nMeta, BlockNotifyType.ALL);
			}
		} else if (world.isAirBlock(x, y, z))
			world.setBlock(x, y, z, this.blockID, 1, BlockNotifyType.ALL);
	}

	private void removeLightingBlock(World world, int x, int y, int z) {
		if (world.getBlockId(x, y, z) == this.blockID) {
			int nMeta = world.getBlockMetadata(x, y, z);
			if (nMeta == 0)
				return;
			else if (nMeta == 1) {
				world.setBlockToAir(x, y, z);
			} else {
				nMeta--;
				world.setBlockMetadataWithNotify(x, y, z, nMeta, BlockNotifyType.ALL);
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighborBlockId) {
		if (world.getBlockMetadata(x, y, z) == 0)
			return;
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
		if (meta != 0)
			return;

		for (int i = -2; i <= 2; i++) {
			for (int j = -2; j <= 2; j++) {
				for (int k = -2; k <= 2; k++) {
					removeLightingBlock(world, x + i, y + j, z + k);
				}
			}
		}

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				removeLightingBlock(world, x + 3, y + i, z + j);
				removeLightingBlock(world, x - 3, y + i, z + j);

				removeLightingBlock(world, x + i, y + 3, z + j);
				removeLightingBlock(world, x + i, y - 3, z + j);

				removeLightingBlock(world, x + i, y + j, z + 3);
				removeLightingBlock(world, x + i, y + j, z - 3);
			}
		}
	}

}
