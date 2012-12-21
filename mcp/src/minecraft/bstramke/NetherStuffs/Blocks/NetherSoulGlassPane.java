package bstramke.NetherStuffs.Blocks;

import java.util.List;

import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherSoulGlassPane extends BlockPane {

	public NetherSoulGlassPane(int par1, int par2, int par3, Material par4Material, boolean par5) {
		super(par1, par2, par3, par4Material, par5);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		if(Loader.isModLoaded("NetherStuffsCore")){
			BlockPane.addToConnectList(this.blockID);
		}
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
		return CommonProxy.BLOCKS_PNG;
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		return true;
	}
}
