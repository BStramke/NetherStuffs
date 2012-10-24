package NetherStuffs.Blocks;

import net.minecraft.src.Block;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class NetherOreItemBlock extends ItemBlock {
	public NetherOreItemBlock(int id, Block block) {
		super(id);
		setHasSubtypes(true);
	}

	public String getItemNameIS(ItemStack is) {
		String name = "";
		switch (is.getItemDamage()) {
		case 0:
			name = "DemonicOre";
			break;
		default:
			name = "DemonicOre";
		}
		
		return getItemName() + "." + name;
	}

	public int getMetadata(int meta) {
		return meta;
	}
}
