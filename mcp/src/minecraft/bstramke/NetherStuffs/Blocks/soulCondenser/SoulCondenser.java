package bstramke.NetherStuffs.Blocks.soulCondenser;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.BlockRegistry;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoulCondenser extends BlockContainer {
	public static int HSSoulKeeperItemId;
	public static int HSSoulVesselItemId;
	public static int HSEssenceKeeperItemId;
	public static int HSEssenceVesselItemId;
	private Icon icoTop;
	private Icon icoBottom;

	private Icon icoSmelterTop;
	private Icon icoSmelterBottom;
	private Icon icoSmelterSides;

	public SoulCondenser(int par1, int SoulKeeperItemId, int SoulVesselItemId, int EssenceKeeperItemId, int EssenceVesselItemId) {
		super(par1, Material.iron);

		HSSoulKeeperItemId = SoulKeeperItemId;
		HSSoulVesselItemId = SoulVesselItemId;
		HSEssenceKeeperItemId = EssenceKeeperItemId;
		HSEssenceVesselItemId = EssenceVesselItemId;

		this.setCreativeTab(NetherStuffs.tabNetherStuffs);

		for (int i = 0; i < SoulCondenserItemBlock.getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("tile.SoulCondenser." + SoulCondenserItemBlock.blockNames[i] + ".name", SoulCondenserItemBlock.blockNames[i]);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulCondenser_Side"));
		icoTop = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulCondenser_Top"));
		icoBottom = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulCondenser_Bottom"));

		icoSmelterSides = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulSmelter_Side"));
		icoSmelterTop = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulSmelter_Top"));
		icoSmelterBottom = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulSmelter_Bottom"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if (meta == 0) {
			if (side == BlockRegistry.sideBottom)
				return icoBottom;
			else if (side == BlockRegistry.sideTop)
				return icoTop;
			else
				return super.getIcon(side, meta);
		} else {
			if (side == BlockRegistry.sideBottom)
				return icoSmelterBottom;
			else if (side == BlockRegistry.sideTop)
				return icoSmelterTop;
			else
				return icoSmelterSides;
		}
	}

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
	public TileEntity createNewTileEntity(World world) {
		return new TileSoulCondenser();
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
		TileSoulCondenser var7 = (TileSoulCondenser) par1World.getBlockTileEntity(par2, par3, par4);
		Random rand = var7.worldObj.rand;
		if (var7 != null) {
			for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8) {
				ItemStack var9 = var7.getStackInSlot(var8);

				if (var9 != null) {
					float var10 = rand.nextFloat() * 0.8F + 0.1F;
					float var11 = rand.nextFloat() * 0.8F + 0.1F;
					float var12 = rand.nextFloat() * 0.8F + 0.1F;

					while (var9.stackSize > 0) {
						int var13 = rand.nextInt(21) + 10;

						if (var13 > var9.stackSize) {
							var13 = var9.stackSize;
						}

						var9.stackSize -= var13;
						EntityItem var14 = new EntityItem(par1World, (double) ((float) par2 + var10), (double) ((float) par3 + var11), (double) ((float) par4 + var12), new ItemStack(
								var9.itemID, var13, var9.getItemDamage()));

						if (var9.hasTagCompound())
							var14.getEntityItem().setTagCompound((NBTTagCompound) var9.getTagCompound().copy());

						float var15 = 0.05F;
						var14.motionX = (double) ((float) rand.nextGaussian() * var15);
						var14.motionY = (double) ((float) rand.nextGaussian() * var15 + 0.2F);
						var14.motionZ = (double) ((float) rand.nextGaussian() * var15);
						par1World.spawnEntityInWorld(var14);
					}
				}
			}
		}
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	public int getMetadataSize() {
		return SoulCondenserItemBlock.blockNames.length;
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}
