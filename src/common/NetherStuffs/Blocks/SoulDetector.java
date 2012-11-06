package NetherStuffs.Blocks;

import java.util.List;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import NetherStuffs.Common.NetherWoodMaterial;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class SoulDetector extends Block {
	public SoulDetector(int par1, int par2) {
		super(par1, par2, Material.rock);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setRequiresSelfNotify();	
	}
	
	/*public String getTextureFile() {
		return "/blocks.png";
	}

	public int getMetadataSize() {
		return SoulDetectorItemBlock.blockNames.length;
	}*/

	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		return 59;
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}
/*
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}*/
}
