package bstramke.NetherStuffs.Blocks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoulGlassPane extends BlockPane {

	public SoulGlassPane(int par1, Material par4Material, boolean par5) {
		super(par1, CommonProxy.getIconLocation("SoulGlass"),  CommonProxy.getIconLocation("SoulGlassTop"), par4Material, par5);
		this.setCreativeTab(NetherStuffs.tabNetherStuffs);
		Block.opaqueCubeLookup[this.blockID] = true;
		Block.opaqueCubeLookup[Block.thinGlass.blockID] = true;
		setUnlocalizedName("NetherSoulGlassPane");
		LanguageRegistry.instance().addStringLocalization("tile.NetherSoulGlassPane.name", "Soul Glass Pane");
		setHardness(0.6F);
		setResistance(10.0F);
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
}
