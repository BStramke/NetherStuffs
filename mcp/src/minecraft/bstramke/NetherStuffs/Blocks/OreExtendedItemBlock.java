package bstramke.NetherStuffs.Blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class OreExtendedItemBlock extends ItemBlock {

	public static String[] blockNames = new String[] { "CertusQuartz", "Ferrous", "Apatite", "Uranium"};
	public static String[] blockDisplayNames = new String[] { "Nether Certus Quartz Ore", "Nether Ferrous Ore", "Nether Apatite Ore", "Nether Uranium Ore"};
	
	public OreExtendedItemBlock(int id) {
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
