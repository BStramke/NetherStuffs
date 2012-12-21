package bstramke.NetherStuffs.Blocks;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class SoulBombItemBlock extends ItemBlock {
	public SoulBombItemBlock(int par1) {
		super(par1);
		setHasSubtypes(true);
	}

	/*
	 * public static int getMetadataSize() { return blockNames.length; }
	 * 
	 * public String getItemNameIS(ItemStack is) { String name = ""; if(is.getItemDamage()<getMetadataSize() && is.getItemDamage()>=0) name = blockNames[is.getItemDamage()]; else name = blockNames[0];
	 * 
	 * return getItemName() + "." + name; }
	 * 
	 * public String getTextureFile() { return CommonProxy.BLOCKS_PNG; }
	 */
	@Override
	public String getItemNameIS(ItemStack is) {
		String name = "NetherSoulBomb";
		return getItemName() + "." + name;
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}
}
