package NetherStuffs.Blocks;

import net.minecraft.src.Block;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class SoulDetectorItemBlock extends ItemBlock {

	public static String[] blockNames = new String[] { "SoulDetectorMK1", "SoulDetectorMK2", "SoulDetectorMK3", "SoulDetectorMK4" };
	public static String[] blockDisplayNames = new String[] { "SoulDetector MK1", "SoulDetector MK2", "SoulDetector MK3", "SoulDetector MK4" };

	public SoulDetectorItemBlock(int par1) {
		super(par1);
		setHasSubtypes(true);
	}

	public static int getMetadataSize() {
		return blockNames.length;
	}

	public String getItemNameIS(ItemStack is) {
		String name = "";
		if (is.getItemDamage() < getMetadataSize() && is.getItemDamage() >= 0)
			name = blockNames[is.getItemDamage()];
		else
			name = blockNames[0];

		return getItemName() + "." + name;
	}

	public String getTextureFile() {
		return "/block_detector.png";
	}
	
	public int getMetadata(int meta) {
		return meta;
	}
}
