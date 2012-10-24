package NetherStuffs.Blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class NetherOre extends Block {
	
	//public static String[] blockNames = NetherOreItemBlock.blockNames; //basically wastes ram 
	
	public NetherOre(int par1, int par2) {
		super(par1, par2, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setRequiresSelfNotify();
	}

	public String getTextureFile() {
		return "/blocks.png";
	}

	public int getMetadataSize() {
		return NetherOreItemBlock.blockNames.length;
	}
	
	
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		switch (meta) {
		case 0:
			return 0;
		default:
			return 0;
		}
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}
