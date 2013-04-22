package bstramke.NetherStuffs.Blocks.soulBlocker;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class SoulBlockerItemBlock extends ItemBlock {
	public static String[] blockNames = new String[] { "NetherSoulBlocker" };
	public static String[] blockDisplayNames = new String[] { "Nether SoulBlocker" };

	public SoulBlockerItemBlock(int id) {
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
