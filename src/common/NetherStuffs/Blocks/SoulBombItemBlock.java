package NetherStuffs.Blocks;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class SoulBombItemBlock extends ItemBlock {
	public SoulBombItemBlock(int par1) {
		super(par1);
		setHasSubtypes(true);
	}
	
	/*public static int getMetadataSize() {
		return blockNames.length;
	}
	
	public String getItemNameIS(ItemStack is) {
		String name = "";
		if(is.getItemDamage()<getMetadataSize() && is.getItemDamage()>=0)
			name = blockNames[is.getItemDamage()];
		else
			name = blockNames[0];
		
		return getItemName() + "." + name;
	}
	
	public String getTextureFile() {
		return "/blocks.png";
	}*/

	public int getMetadata(int meta) {
		return meta;
	}
}
