package NetherStuffs.Blocks;

import java.util.List;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.Block;
import net.minecraft.src.BlockGlass;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;

public class NetherSoulGlass extends BlockGlass {

	public NetherSoulGlass(int par1, int par2, Material par3Material, boolean par4) {
		super(par1, par2, par3Material, par4);
		this.setHardness(0.3F);
		this.setStepSound(soundGlassFootstep);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
		
	public String getItemNameIS(ItemStack is) {
		String name = "NetherSoulGlass";
		return getBlockName() + "." + name;
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		list.add(new ItemStack(par1, 1, 0));
	}
	
	public String getTextureFile() {
		return "/blocks.png";
	}
	
	/*
	 * The issue is: it looks strange...
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
   {
       int var6 = par1IBlockAccess.getBlockId(par2, par3, par4);
       return var6 == Block.glass.blockID ? false : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
   }*/
	
}
