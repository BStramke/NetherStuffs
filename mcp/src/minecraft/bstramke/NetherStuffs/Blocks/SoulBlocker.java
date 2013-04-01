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
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.SoulBlocker.TileSoulBlocker;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoulBlocker extends BlockContainer {
	public static final int NetherSoulBlocker = 0;
	private Icon icoSoulBlockerTop;
	private Icon icoSoulBlockerBottom;

	public SoulBlocker(int par1) {
		super(par1, Material.circuits);
		this.setCreativeTab(NetherStuffs.tabNetherStuffs);
	}


	public int getMetadataSize() {
		return SoulBlockerItemBlock.blockNames.length;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulBlocker"));
		icoSoulBlockerTop = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulBlocker_Top"));
		icoSoulBlockerBottom = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulBlocker_Bottom"));
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileSoulBlocker();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTextureFromSideAndMetadata(int side, int meta) {
		if(side == NetherBlocks.sideBottom)
			return icoSoulBlockerBottom;
		else if(side == NetherBlocks.sideTop)
			return icoSoulBlockerTop;
		else
			return super.getBlockTextureFromSideAndMetadata(side, meta);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}
