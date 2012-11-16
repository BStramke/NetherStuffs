package NetherStuffs.Blocks;

import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.BlockEnderChest;
import net.minecraft.src.BlockGlass;
import net.minecraft.src.BlockPane;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class NetherSoulGlassPane extends BlockPane {

	public NetherSoulGlassPane(int par1, int par2, int par3, Material par4Material, boolean par5) {
		super(par1, par2, par3, par4Material, par5);
		this.setHardness(0.3F);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		BlockPane.addToConnectList(this.blockID);
	}

	public String getItemNameIS(ItemStack is) {
		String name = "NetherSoulGlassPane";
		return getBlockName() + "." + name;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		list.add(new ItemStack(par1, 1, 0));
	}

	@Override
	public String getTextureFile() {
		return "/blocks.png";
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		return true;
	}
}
