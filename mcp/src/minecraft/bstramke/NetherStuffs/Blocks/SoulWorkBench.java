package bstramke.NetherStuffs.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.SoulWorkBench.TileSoulWorkBench;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoulWorkBench extends BlockContainer {
	private static final int METADATA_BITMASK = 0x7;
	private static final int METADATA_ACTIVEBIT = 0x8;
	private static final int METADATA_CLEARACTIVEBIT = -METADATA_ACTIVEBIT - 1;
	private Random rand = new Random();
	private Icon icoSoulWorkbenchTop;
	private Icon icoSoulWorkbenchBottom;
	private Icon icoSoulWorkbenchSideWE;
	private Icon icoSoulWorkbenchSideNS;
	private static boolean keepFurnaceInventory = false;

	public static int clearActiveOnMetadata(int metadata) {
		return metadata & METADATA_CLEARACTIVEBIT;
	}

	public static boolean isActiveSet(int metadata) {
		return (metadata & METADATA_ACTIVEBIT) != 0;
	}

	public static int setActiveOnMetadata(int metadata) {
		return metadata | METADATA_ACTIVEBIT;
	}

	public static int unmarkedMetadata(int metadata) {
		return metadata & METADATA_BITMASK;
	}

	protected SoulWorkBench(int par1) {
		super(par1, Material.rock);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setStepSound(soundStoneFootstep);
		this.setRequiresSelfNotify();
		this.setTickRandomly(true);
	}

	/*@Override
	public String getItemNameIS(ItemStack is) {
		String name = "NetherSoulWorkBench";
		return getBlockName() + "." + name;
	}*/

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return NetherStuffs.SoulWorkBenchBlockId;
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		super.onBlockAdded(par1World, par2, par3, par4);
		this.setDefaultDirection(par1World, par2, par3, par4);
	}

	/**
	 * set a blocks direction
	 */
	private void setDefaultDirection(World par1World, int par2, int par3, int par4) {
		if (!par1World.isRemote) {
			int var5 = par1World.getBlockId(par2, par3, par4 - 1);
			int var6 = par1World.getBlockId(par2, par3, par4 + 1);
			int var7 = par1World.getBlockId(par2 - 1, par3, par4);
			int var8 = par1World.getBlockId(par2 + 1, par3, par4);
			byte var9 = 3;

			if (Block.opaqueCubeLookup[var5] && !Block.opaqueCubeLookup[var6]) {
				var9 = 3;
			}

			if (Block.opaqueCubeLookup[var6] && !Block.opaqueCubeLookup[var5]) {
				var9 = 2;
			}

			if (Block.opaqueCubeLookup[var7] && !Block.opaqueCubeLookup[var8]) {
				var9 = 5;
			}

			if (Block.opaqueCubeLookup[var8] && !Block.opaqueCubeLookup[var7]) {
				var9 = 4;
			}

			par1World.setBlockMetadataWithNotify(par2, par3, par4, var9, 2);
		}
	}

	private boolean isActive(World par1World, int par2, int par3, int par4) {
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		if (isActiveSet(meta))
			return true;
		else
			return false;
	}

	private boolean isActive(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		int meta = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		if (isActiveSet(meta))
			return true;
		else
			return false;
	}

	public void func_94332_a(IconRegister par1IconRegister)
	{
		icoSoulWorkbenchTop = par1IconRegister.func_94245_a(CommonProxy.getIconLocation("SoulWorkbenchTop"));
		icoSoulWorkbenchBottom = par1IconRegister.func_94245_a(CommonProxy.getIconLocation("SoulWorkbenchBottom"));
		icoSoulWorkbenchSideWE = par1IconRegister.func_94245_a(CommonProxy.getIconLocation("SoulWorkbenchSideWE"));
		icoSoulWorkbenchSideNS = par1IconRegister.func_94245_a(CommonProxy.getIconLocation("SoulWorkbenchSideNS"));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int side) {

		switch (side) {
		case NetherBlocks.sideBottom:
			return icoSoulWorkbenchBottom; // bottom
		case NetherBlocks.sideTop:
			return icoSoulWorkbenchTop; // top
		case NetherBlocks.sideEast:
		case NetherBlocks.sideWest:
			return icoSoulWorkbenchSideWE;
		default:
			return icoSoulWorkbenchSideNS;
		}
	}

	@SideOnly(Side.CLIENT)
	/**
	 * A randomly called display update to be able to add particles or other items for display
	 */
	@Override
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (this.isActive(par1World, par2, par3, par4)) {
			int var6 = unmarkedMetadata(par1World.getBlockMetadata(par2, par3, par4));
			float var7 = (float) par2 + 0.5F;
			float var8 = (float) par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F;
			float var9 = (float) par4 + 0.5F;
			float var10 = 0.52F;
			float var11 = par5Random.nextFloat() * 0.6F - 0.3F;

			if (var6 == 4) {
				par1World.spawnParticle("smoke", (double) (var7 - var10), (double) var8, (double) (var9 + var11), 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle("flame", (double) (var7 - var10), (double) var8, (double) (var9 + var11), 0.0D, 0.0D, 0.0D);
			} else if (var6 == 5) {
				par1World.spawnParticle("smoke", (double) (var7 + var10), (double) var8, (double) (var9 + var11), 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle("flame", (double) (var7 + var10), (double) var8, (double) (var9 + var11), 0.0D, 0.0D, 0.0D);
			} else if (var6 == 2) {
				par1World.spawnParticle("smoke", (double) (var7 + var11), (double) var8, (double) (var9 - var10), 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle("flame", (double) (var7 + var11), (double) var8, (double) (var9 - var10), 0.0D, 0.0D, 0.0D);
			} else if (var6 == 3) {
				par1World.spawnParticle("smoke", (double) (var7 + var11), (double) var8, (double) (var9 + var10), 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle("flame", (double) (var7 + var11), (double) var8, (double) (var9 + var10), 0.0D, 0.0D, 0.0D);
			}
		}
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g, float t) {
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);

		if (tile_entity == null || player.isSneaking()) {
			return false;
		}

		player.openGui(NetherStuffs.instance, 0, world, x, y, z);
		return true;
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack is) {
		int var6 = MathHelper.floor_double((double) (par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (var6 == 0) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
		}

		if (var6 == 1) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
		}

		if (var6 == 2) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
		}

		if (var6 == 3) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
		}
	}

	@Override
	// public void breakBlock(World world, int x, int y, int z, int i, int j)
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
		if (!keepFurnaceInventory) {
			TileSoulWorkBench var7 = (TileSoulWorkBench) par1World.getBlockTileEntity(par2, par3, par4);

			if (var7 != null) {
				for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8) {
					ItemStack var9 = var7.getStackInSlot(var8);

					if (var9 != null) {
						float var10 = this.rand.nextFloat() * 0.8F + 0.1F;
						float var11 = this.rand.nextFloat() * 0.8F + 0.1F;
						float var12 = this.rand.nextFloat() * 0.8F + 0.1F;

						while (var9.stackSize > 0) {
							int var13 = this.rand.nextInt(21) + 10;

							if (var13 > var9.stackSize) {
								var13 = var9.stackSize;
							}

							var9.stackSize -= var13;
							EntityItem var14 = new EntityItem(par1World, (double) ((float) par2 + var10), (double) ((float) par3 + var11), (double) ((float) par4 + var12), new ItemStack(var9.itemID,
									var13, var9.getItemDamage()));

							if (var9.hasTagCompound()) {
								//func_92014_d() gets back an Item
                                var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
                            }

							float var15 = 0.05F;
							var14.motionX = (double) ((float) this.rand.nextGaussian() * var15);
							var14.motionY = (double) ((float) this.rand.nextGaussian() * var15 + 0.2F);
							var14.motionZ = (double) ((float) this.rand.nextGaussian() * var15);
							par1World.spawnEntityInWorld(var14);
						}
					}
				}
			}
		}

		super.breakBlock(par1World, par2, par3, par4, par5, par6);

	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileSoulWorkBench();
	}

}
