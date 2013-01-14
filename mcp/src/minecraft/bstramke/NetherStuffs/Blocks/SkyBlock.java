package bstramke.NetherStuffs.Blocks;

import java.util.List;

import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class SkyBlock extends Block {

	public static String[] blockNames = new String[] { "SkyBlock" };
	public static String[] blockDisplayNames = new String[] { "Sky Block" };

	public SkyBlock(int par1) {
		super(par1, Material.air);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setStepSound(soundPowderFootstep);
		this.setRequiresSelfNotify();
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.BLOCKS_PNG;
	}

	public static int getMetadataSize() {
		return blockNames.length;
	}

	public String getItemNameIS(ItemStack is) {
		String name = "NetherSkyBlock";
		return getBlockName() + "." + name;
	}

	
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		return 144;
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
