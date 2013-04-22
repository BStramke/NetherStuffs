package bstramke.NetherStuffs.Blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import bstramke.NetherStuffs.Common.CommonProxy;

public class WoodItemBlock extends ItemBlock {

	public static String[] blockNames = new String[] { "Hellfire", "Acid", "Death" };
	public static String[] blockDisplayNames = new String[] { "Hellfire Wood", "Acid Wood", "Death Wood" };

	public WoodItemBlock(int id) {
		super(id);
		setHasSubtypes(true);
	}

	public static int getMetadataSize() {
		return blockNames.length;
	}

	@Override
	public String getUnlocalizedName(ItemStack is) {
		String name = "";
		if (is.getItemDamage() < getMetadataSize() && is.getItemDamage() >= 0)
			name = blockNames[is.getItemDamage()];
		else
			name = blockNames[0];

		return getUnlocalizedName() + "." + name;
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}
}
