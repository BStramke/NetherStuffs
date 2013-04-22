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
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoulGlass extends BlockGlass {

	public SoulGlass(int par1, Material par3Material, boolean par4) {
		super(par1, par3Material, par4);
		this.setStepSound(soundGlassFootstep);
		
		Block.opaqueCubeLookup[this.blockID] = true;
		setUnlocalizedName("NetherSoulGlass");
		this.setCreativeTab(NetherStuffs.tabNetherStuffs);
		setHardness(0.6F);
		setResistance(10.0F);
		LanguageRegistry.instance().addStringLocalization("tile.NetherSoulGlass.name", "Soul Glass");
	}
	
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
