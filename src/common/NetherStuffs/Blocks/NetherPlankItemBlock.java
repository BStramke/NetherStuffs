package NetherStuffs.Blocks;

import net.minecraft.src.Block;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class NetherPlankItemBlock extends ItemBlock {
	
	public static String[] blockNames = new String[] {"Hellfire", "Acid", "Death"};
	public static String[] blockDisplayNames = new String[] {"Hellfire Log", "Acid Log", "Death Log"}; 
	
	public NetherPlankItemBlock(int id, Block block) {
		super(id);
		setHasSubtypes(true);
	}

	public String getTextureFile() {
		return "/blocks.png";
	}

	public static int getMetadataSize() {
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

	public int getMetadata(int meta) {
		return meta;
	}
}
