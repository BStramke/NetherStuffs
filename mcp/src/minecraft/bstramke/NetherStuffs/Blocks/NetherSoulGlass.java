package bstramke.NetherStuffs.Blocks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherSoulGlass extends BlockGlass {

	public NetherSoulGlass(int par1, Material par3Material, boolean par4) {
		super(par1, par3Material, par4);
		this.setStepSound(soundGlassFootstep);
		
		Block.opaqueCubeLookup[this.blockID] = true;
		
		this.setCreativeTab(CreativeTabs.tabBlock);

		try {
			Class[] args = new Class[1];
			args[0] = int.class;
			Method m = BlockPane.class.getDeclaredMethod("addToConnectList", args);
			m.invoke(null, this.blockID);
		} catch (NoSuchMethodException e) {} catch (SecurityException e) {} catch (IllegalAccessException e) {} catch (IllegalArgumentException e) {} catch (InvocationTargetException e) {}

		try {
			Class[] args = new Class[1];
			args[0] = int.class;
			Method m;
			m = BlockBreakable.class.getDeclaredMethod("addToSameBlockList", args);
			m.invoke(null, Block.glass.blockID);
			m.invoke(null, this.blockID);
		} catch (NoSuchMethodException e) {} catch (SecurityException e) {} catch (IllegalAccessException e) {} catch (IllegalArgumentException e) {} catch (InvocationTargetException e) {}

	}
	
	/*@Override
	public String getItemNameIS(ItemStack is) {
		String name = "NetherSoulGlass";
		return getUnlocalizedName() + "." + name;
	}*/

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		list.add(new ItemStack(par1, 1, 0));
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{	
		blockIcon = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulGlass"));
	}
}
