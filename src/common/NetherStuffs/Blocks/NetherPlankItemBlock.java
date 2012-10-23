package NetherStuffs.Blocks;

import net.minecraft.src.Block;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class NetherPlankItemBlock extends ItemBlock {
	public NetherPlankItemBlock(int id, Block block) {
		super(id);
		setHasSubtypes(true);
	}

	public String getTextureFile() {
		return "/blocks.png";
	}

	public String getItemNameIS(ItemStack is) {
		String name = "";
		switch (is.getItemDamage()) {
		case 0:
			name = "Hellfire";
			break;
		case 1:
			name = "Acid";
			break;
		case 2:
			name = "Death";
			break;
		default:
			name = "Hellfire";
		}
		return getItemName() + "." + name;
	}

	public int getMetadata(int meta) {
		return meta;
	}
}
