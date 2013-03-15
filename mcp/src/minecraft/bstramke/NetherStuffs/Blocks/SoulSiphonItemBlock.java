package bstramke.NetherStuffs.Blocks;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import bstramke.NetherStuffs.Common.CommonProxy;

public class SoulSiphonItemBlock extends ItemBlock {

	public static String[] blockNames = new String[] { "SoulSiphonMK1", "SoulSiphonMK2", "SoulSiphonMK3", "SoulSiphonMK4" };
	public static String[] blockDisplayNames = new String[] { "Soul Siphon MK 1", "Soul Siphon MK 2", "Soul Siphon MK 3", "Soul Siphon MK 4" };

	public SoulSiphonItemBlock(int id) {
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
