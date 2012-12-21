package bstramke.NetherStuffs.Blocks;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import bstramke.NetherStuffs.Common.CommonProxy;

public class SoulSiphonItemBlock extends ItemBlock {

	public static String[] blockNames = new String[] { "SoulSiphonMK1" };
	public static String[] blockDisplayNames = new String[] { "Soul Siphon MK 1" };

	public SoulSiphonItemBlock(int id) {
		super(id);
		setHasSubtypes(true);
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.BLOCKS_PNG;
	}

	public static int getMetadataSize() {
		return blockNames.length;
	}
	
	@Override
	public String getItemNameIS(ItemStack is) {
		String name = "";
		if (is.getItemDamage() < getMetadataSize() && is.getItemDamage() >= 0)
			name = blockNames[is.getItemDamage()];
		else
			name = blockNames[0];

		return getItemName() + "." + name;
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}
}