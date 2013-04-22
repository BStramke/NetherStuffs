package bstramke.NetherStuffs.Blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class OreItemBlock extends ItemBlock {

	public static String[] blockNames = new String[] { "DemonicOre", "NetherStone", "NetherOreIron", "NetherOreGold", "NetherOreDiamond", "NetherOreRedstone", "NetherOreEmerald", "NetherOreCoal", "NetherOreObsidian", "NetherOreLapis", "NetherCobblestone"};
	public static String[] blockDisplayNames = new String[] { "Demonic Ore", "Nether Stone", "Nether Iron Ore", "Nether Gold Ore", "Nether Diamond Ore", "Nether Redstone Ore", "Nether Emerald Ore", "Nether Coal Ore", "Nether Obsidian Ore", "Nether Lapis Ore", "Encrusted Cobblestone" };
	
	public OreItemBlock(int id) {
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
