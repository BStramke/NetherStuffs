package bstramke.NetherStuffs.Blocks.soulFurnace;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
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
import bstramke.NetherStuffs.Blocks.BlockContainerBase;
import bstramke.NetherStuffs.Blocks.BlockRegistry;
import bstramke.NetherStuffs.Common.BlockNotifyType;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherSoulFurnace extends BlockContainerBase {

	/** True if this is an active furnace, false if idle */
	private Random furnaceRand = new Random();

	private Icon icoFurnaceTop;
	private Icon icoFurnaceBottom;
	private Icon icoFurnaceSide;
	private Icon icoFurnaceFrontInactive;
	private Icon icoFurnaceFrontActive;

	private static final int METADATA_BITMASK = 0x7;
	private static final int METADATA_ACTIVEBIT = 0x8;
	private static final int METADATA_CLEARACTIVEBIT = -METADATA_ACTIVEBIT - 1;

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

	public NetherSoulFurnace(int par1) {
		super(par1, Material.rock);
		this.setCreativeTab(NetherStuffs.tabNetherStuffs);
		this.setStepSound(soundStoneFootstep);
		this.setTickRandomly(true);
		setUnlocalizedName("NetherSoulFurnace");
		LanguageRegistry.instance().addStringLocalization("tile.NetherSoulFurnace.name", "Soul Furnace");
	}

	public String getUnlocalizedName(ItemStack is) {
		String name = "NetherSoulFurnace";
		return getUnlocalizedName() + "." + name;
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return NetherStuffs.NetherSoulFurnaceBlockId;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		icoFurnaceTop = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulFurnaceTop"));
		icoFurnaceBottom = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulFurnaceBottom"));
		icoFurnaceSide = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulFurnaceSide"));
		icoFurnaceFrontInactive = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulFurnaceFrontInactive"));
		icoFurnaceFrontActive = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulFurnaceFrontActive"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int x, int y, int z, int side) {
		int meta = par1IBlockAccess.getBlockMetadata(x, y, z);
		switch (side) {
		case BlockRegistry.sideBottom:
			return icoFurnaceBottom; // bottom
		case BlockRegistry.sideTop:
			return icoFurnaceTop; // top
		default: {
				if(side == unmarkedMetadata(meta))
				{
					if (this.isActiveSet(meta))
						return icoFurnaceFrontActive;
					else
						return icoFurnaceFrontInactive;
				}
				else
					return icoFurnaceSide;
			}
		}
	};
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		switch (side) {
		case BlockRegistry.sideBottom:
			return icoFurnaceBottom; // bottom
		case BlockRegistry.sideTop:
			return icoFurnaceTop; // top
		case BlockRegistry.sideSouth:
			return icoFurnaceFrontInactive;
		default:
			return icoFurnaceSide;
		}
	};

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

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
		TileSoulFurnace var7 = (TileSoulFurnace) par1World.getBlockTileEntity(par2, par3, par4);

		if (var7 != null) {
			for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8) {
				ItemStack var9 = var7.getStackInSlot(var8);

				if (var9 != null) {
					float var10 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
					float var11 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
					float var12 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;

					while (var9.stackSize > 0) {
						int var13 = this.furnaceRand.nextInt(21) + 10;

						if (var13 > var9.stackSize) {
							var13 = var9.stackSize;
						}

						var9.stackSize -= var13;
						EntityItem var14 = new EntityItem(par1World, (double) ((float) par2 + var10), (double) ((float) par3 + var11), (double) ((float) par4 + var12), new ItemStack(
								var9.itemID, var13, var9.getItemDamage()));

						if (var9.hasTagCompound()) {
							// func_92014_d() gets back an Item
							var14.getEntityItem().setTagCompound((NBTTagCompound) var9.getTagCompound().copy());
						}

						float var15 = 0.05F;
						var14.motionX = (double) ((float) this.furnaceRand.nextGaussian() * var15);
						var14.motionY = (double) ((float) this.furnaceRand.nextGaussian() * var15 + 0.2F);
						var14.motionZ = (double) ((float) this.furnaceRand.nextGaussian() * var15);
						par1World.spawnEntityInWorld(var14);
					}
				}
			}
		}
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
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

	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		super.onBlockAdded(par1World, par2, par3, par4);
		this.setDefaultDirection(par1World, par2, par3, par4);
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack) {
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

			par1World.setBlockMetadataWithNotify(par2, par3, par4, var9, BlockNotifyType.ALL);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileSoulFurnace();
	}
}
