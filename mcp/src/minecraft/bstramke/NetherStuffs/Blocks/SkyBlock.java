package bstramke.NetherStuffs.Blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SkyBlock extends Block {

	public static String[] blockNames = new String[] { "SkyBlock" };
	public static String[] blockDisplayNames = new String[] { "Sky Block" };
	private Icon icoSkyBlockTopBottom;
	private Icon icoSkyBlockSide;

	public SkyBlock(int par1) {
		super(par1, Material.air);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setStepSound(soundPowderFootstep);
		//this.setRequiresSelfNotify();
		LanguageRegistry.instance().addStringLocalization("tile.NetherSkyBlock.name", SkyBlock.blockDisplayNames[0]);
	}

	public static int getMetadataSize() {
		return blockNames.length;
	}

	@Override
	public void onBlockAdded(World par1World, int xCoord, int yCoord, int zCoord) {
		par1World.scheduleBlockUpdate(xCoord, yCoord, zCoord, this.blockID, this.tickRate(par1World));
	};

	@Override
	public int tickRate(World par1World) {
		return 40;
	};

	@Override
	public void updateTick(World par1World, int xCoord, int yCoord, int zCoord, java.util.Random par5Random) {
		if (!par1World.isRemote) {
			par1World.scheduleBlockUpdate(xCoord, yCoord, zCoord, this.blockID, this.tickRate(par1World));
		}
	};

	@Override
	public void func_94332_a(IconRegister par1IconRegister)
	{
		icoSkyBlockTopBottom = par1IconRegister.func_94245_a(CommonProxy.getIconLocation("SkyBlockTopBottom"));
		icoSkyBlockSide = par1IconRegister.func_94245_a(CommonProxy.getIconLocation("SkyBlockSide"));
	}
	
	@Override
	public Icon getBlockTextureFromSideAndMetadata(int side, int meta) {
		switch (side) {
		case NetherBlocks.sideBottom:
		case NetherBlocks.sideTop:	
			return icoSkyBlockTopBottom;
		default:
			return icoSkyBlockSide;
		}
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}
