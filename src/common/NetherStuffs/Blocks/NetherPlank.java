package NetherStuffs.Blocks;

import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class NetherPlank extends Block {

	public NetherPlank(int par1, int par2) {
		super(par1, par2, Material.wood);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setRequiresSelfNotify();
	}

	public String getTextureFile() {
		return "/blocks.png";
	}

	public int getMetadataSize() {
		return NetherPlankItemBlock.blockNames.length;
	}
	
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		int nRowDiff = 48;
		switch (meta) {
		case 0:
			return 0+nRowDiff;
		case 1:
			return 1+nRowDiff;
		case 2:
			return 2+nRowDiff;
		default:
			return 0+nRowDiff;
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