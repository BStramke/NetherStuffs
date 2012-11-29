package NetherStuffs.Blocks;

import net.minecraft.src.Block;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class NetherOreItemBlock extends ItemBlock {

	public static String[] blockNames = new String[] { "DemonicOre", "NetherStone", "NetherOreIron", "NetherOreGold", "NetherOreDiamond", "NetherOreRedstone", "NetherOreEmerald", "NetherOreCoal", "NetherOreObsidian", "NetherOreLapis" };
	public static String[] blockDisplayNames = new String[] { "Demonic Ore", "Nether Stone", "Nether Iron Ore", "Nether Gold Ore", "Nether Diamond Ore", "Nether Redstone Ore", "Nether Emerald Ore", "Nether Coal Ore", "Nether Obsidian Ore", "Nether Lapis Ore" };
	
	public NetherOreItemBlock(int id, Block block) {
		super(id);
		setHasSubtypes(true);
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
