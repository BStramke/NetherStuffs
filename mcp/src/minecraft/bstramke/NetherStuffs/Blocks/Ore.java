package bstramke.NetherStuffs.Blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Client.Renderers.NetherOreRenderingHelper;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Items.ItemRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Ore extends BlockBase {
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

	private Icon icoNetherOre;
	private Icon icoNetherStone;
	
	private Icon icoNetherOreOverlay[];
	
	public Ore(int par1) {
		super(par1, Material.rock);
		this.setStepSound(soundStoneFootstep);
		setUnlocalizedName("NetherOre");
		
		for (int i = 0; i < OreItemBlock.getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherOre." + OreItemBlock.blockNames[i] + ".name", OreItemBlock.blockDisplayNames[i]);
		}
	}

	public int getMetadataSize() {
		return OreItemBlock.blockNames.length;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icoNetherOreOverlay = new Icon[11];
		icoNetherOre = iconRegister.registerIcon(CommonProxy.getIconLocation("NetherOre"));
		icoNetherStone = iconRegister.registerIcon(CommonProxy.getIconLocation("NetherStone"));
		icoNetherOreOverlay[demonicOre] = iconRegister.registerIcon(CommonProxy.getIconLocation("NetherOre_Demonic"));
		icoNetherOreOverlay[netherStone] = icoNetherStone;
		icoNetherOreOverlay[netherOreIron] = iconRegister.registerIcon(CommonProxy.getIconLocation("NetherOre_Iron"));
		icoNetherOreOverlay[netherOreGold] = iconRegister.registerIcon(CommonProxy.getIconLocation("NetherOre_Gold"));
		icoNetherOreOverlay[netherOreDiamond] = iconRegister.registerIcon(CommonProxy.getIconLocation("NetherOre_Diamond"));
		icoNetherOreOverlay[netherOreRedstone] = iconRegister.registerIcon(CommonProxy.getIconLocation("NetherOre_Redstone"));
		icoNetherOreOverlay[netherOreEmerald] = iconRegister.registerIcon(CommonProxy.getIconLocation("NetherOre_Emerald"));
		icoNetherOreOverlay[netherOreCoal] = iconRegister.registerIcon(CommonProxy.getIconLocation("NetherOre_Coal"));
		icoNetherOreOverlay[netherOreObsidian] = iconRegister.registerIcon(CommonProxy.getIconLocation("NetherOre_Obsidian"));
		icoNetherOreOverlay[netherOreLapis] = iconRegister.registerIcon(CommonProxy.getIconLocation("NetherOre_Lapis"));
		icoNetherOreOverlay[netherOreCobblestone] = iconRegister.registerIcon(CommonProxy.getIconLocation("NetherOre_Cobblestone"));
		
		blockIcon = icoNetherOre;
	}

	public Icon getIconOreOverlay(int meta)
	{
		if(meta >=0 && meta < icoNetherOreOverlay.length)
			return icoNetherOreOverlay[meta];
		else
			return icoNetherOre;
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
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
			return icoNetherOre;
		case netherStone:
			return icoNetherStone;
		default:
			return icoNetherOre;
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
		else if(meta == netherOreCoal) //drop coal directly as Charcoal
			return ItemRegistry.NetherWoodCharcoal.itemID;
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
