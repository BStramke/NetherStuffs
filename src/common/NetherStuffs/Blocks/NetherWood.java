package NetherStuffs.Blocks;

import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class NetherWood extends Block {

	public static final int hellfire = 0;
	public static final int acid = 1;
	public static final int death = 2;

	public NetherWood(int par1, int par2) {
		super(par1, par2, Material.wood);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setRequiresSelfNotify();
	}

	@Override
   public boolean isWood(World world, int x, int y, int z)
   {
       return true;
   }
	
   /**
    * The type of render function that is called for this block
    */
   public int getRenderType()
   {
       return 31;
   }
	
	@Override
	public boolean canSustainLeaves(World world, int x, int y, int z) {
		return true;
	}

	public String getTextureFile() {
		return "/blocks.png";
	}

	public int getMetadataSize() {
		return NetherWoodItemBlock.blockNames.length;
	}

	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		int nRowDiff = 32;
		// side: 1=top, 0=bottom
		if (side == 1 || side == 0) {
			nRowDiff = nRowDiff - 16;// look one row above
		}
		switch (meta) {
			case hellfire:
				return hellfire + nRowDiff;
			case acid:
				return acid + nRowDiff;
			case death:
				return death + nRowDiff;
			default:
				return hellfire + nRowDiff;
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