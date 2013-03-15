package bstramke.NetherStuffs.Blocks;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.SoulBlocker.TileSoulBlocker;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoulBlocker extends BlockContainer {
	public static final int NetherSoulBlocker = 0;
	private Icon icoSoulBlocker;

	public SoulBlocker(int par1) {
		super(par1, Material.circuits);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setRequiresSelfNotify();
	}


	public int getMetadataSize() {
		return SoulBlockerItemBlock.blockNames.length;
	}

	@Override
	public void func_94332_a(IconRegister par1IconRegister)
	{
		icoSoulBlocker = par1IconRegister.func_94245_a(CommonProxy.getIconLocation("SoulBlocker"));
	}
	
	@Override
	public Icon getBlockTextureFromSideAndMetadata(int side, int meta) {
		switch (meta) {
		case NetherSoulBlocker:
			return icoSoulBlocker;
		default:
			return icoSoulBlocker;
		}
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileSoulBlocker();
	}

	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}
