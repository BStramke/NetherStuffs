package NetherStuffs.Blocks;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.Block;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class NetherPlankItemBlock extends ItemBlock {

	public static String[] blockNames = new String[] { "Hellfire", "Acid", "Death" };
	public static String[] blockDisplayNames = new String[] { "Hellfire Log", "Acid Log", "Death Log" };

	public NetherPlankItemBlock(int id, Block block) {
		super(id);
		setHasSubtypes(true);
	}

	@Override
	public String getTextureFile() {
		return "/blocks.png";
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
