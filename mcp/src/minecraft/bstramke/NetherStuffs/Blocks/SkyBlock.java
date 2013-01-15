package bstramke.NetherStuffs.Blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SkyBlock extends Block {

	public static String[] blockNames = new String[] { "SkyBlock" };
	public static String[] blockDisplayNames = new String[] { "Sky Block" };

	public SkyBlock(int par1) {
		super(par1, Material.air);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setStepSound(soundPowderFootstep);
		this.setRequiresSelfNotify();
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.BLOCKS_PNG;
	}

	public static int getMetadataSize() {
		return blockNames.length;
	}

	public String getItemNameIS(ItemStack is) {
		String name = "NetherSkyBlock";
		return getBlockName() + "." + name;
	}

	@Override
	public void onBlockAdded(net.minecraft.world.World par1World, int xCoord, int yCoord, int zCoord) {
		//System.out.println("onBlockAdded");
		par1World.scheduleBlockUpdate(xCoord, yCoord, zCoord, this.blockID, this.tickRate());
	};

	@Override
	public int tickRate() {
		return 40;
	};

	@Override
	public void updateTick(net.minecraft.world.World par1World, int xCoord, int yCoord, int zCoord, java.util.Random par5Random) {
		if (!par1World.isRemote) {
			//System.out.println("tick");
			par1World.scheduleBlockUpdate(xCoord, yCoord, zCoord, this.blockID, this.tickRate());
		}
	};

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		return 144;
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
