package bstramke.NetherStuffs.Blocks.soulRipper;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.BlockContainerBase;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoulRipper extends BlockContainerBase {

	public static final int mk1 = 0;
	public static final int mk2 = 1;
	public static final int mk3 = 2;
	public static final int mk4 = 3;

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

	private Icon icoSoulSiphonInactive;
	private Icon icoSoulSiphonActive;

	public SoulRipper(int par1) {
		super(par1, Material.iron);
		this.setCreativeTab(NetherStuffs.tabNetherStuffs);
		setUnlocalizedName("NetherSoulSiphon");
		for (int i = 0; i < SoulRipperItemBlock.getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherSoulSiphon." + SoulRipperItemBlock.blockNames[i] + ".name", SoulRipperItemBlock.blockDisplayNames[i]);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		icoSoulSiphonInactive = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulRipperInactive"));
		icoSoulSiphonActive = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulRipperActive"));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int side, int meta) {
		if (this.isActiveSet(meta))
			return icoSoulSiphonActive;
		else
			return icoSoulSiphonInactive;
	}

	public int getMetadataSize() {
		return SoulRipperItemBlock.blockNames.length;
	}

	@Override
	public int damageDropped(int meta) {
		return unmarkedMetadata(meta);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
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

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileSoulRipper();
	}
	
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
		TileSoulRipper var7 = (TileSoulRipper) par1World.getBlockTileEntity(par2, par3, par4);
		Random rand = var7.worldObj.rand;
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
}
