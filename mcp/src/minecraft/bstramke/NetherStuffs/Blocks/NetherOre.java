package bstramke.NetherStuffs.Blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import bstramke.NetherStuffs.Client.NetherOreRenderingHelper;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherOre extends Block {
	public static final int demonicOre = 0;
	public static final int netherStone = 1;
	public static final int netherOreIron = 2;
	public static final int netherOreGold = 3;
	public static final int netherOreDiamond = 4;
	public static final int netherOreRedstone = 5;
	public static final int netherOreEmerald = 6;
	public static final int netherOreCoal = 7;
	public static final int netherOreObsidian = 8;
	public static final int netherOreLapis = 9;
	public static final int netherOreCobblestone = 10;

	public NetherOre(int par1, int par2) {
		super(par1, par2, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setRequiresSelfNotify();
		this.setStepSound(soundStoneFootstep);
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.BLOCKS_PNG;
	}

	public int getMetadataSize() {
		return NetherOreItemBlock.blockNames.length;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		switch (meta) {
		case demonicOre:
		case netherOreIron:
		case netherOreGold:
		case netherOreDiamond:
		case netherOreRedstone:
		case netherOreEmerald:
		case netherOreCoal:
		case netherOreObsidian:
		case netherOreLapis:
		case netherOreCobblestone:
			return 0;
		case netherStone:
			return 1;
		default:
			return 0;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return NetherOreRenderingHelper.instance.getRenderId();
	}

	@Override
	public int idDropped(int meta, Random par2Random, int par3) {
		if(meta == netherOreCobblestone) //cobble may drop as cobble
			return Block.cobblestone.blockID;
		else
			return super.idDropped(meta, par2Random, par3);
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