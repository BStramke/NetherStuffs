package bstramke.NetherStuffs.Blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherSoulGlass extends BlockGlass {

	public NetherSoulGlass(int par1, int par2, Material par3Material, boolean par4) {
		super(par1, par2, par3Material, par4);
		this.setStepSound(soundGlassFootstep);
		this.setCreativeTab(CreativeTabs.tabBlock);
		if (Loader.isModLoaded("NetherStuffsCore") || NetherStuffs.DevSetCoreModAvailable) {
			BlockPane.addToConnectList(par1);
			addToSameBlockList(Block.glass.blockID);
			addToSameBlockList(this.blockID);
		}
	}

	public String getItemNameIS(ItemStack is) {
		String name = "NetherSoulGlass";
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

	/*
	 * The issue is: it looks strange... public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) { int var6 = par1IBlockAccess.getBlockId(par2, par3,
	 * par4); return var6 == Block.glass.blockID ? false : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5); }
	 */

}
