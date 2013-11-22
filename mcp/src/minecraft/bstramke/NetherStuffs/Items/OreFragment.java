package bstramke.NetherStuffs.Items;

import java.util.List;

import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.OreItemBlock;
import bstramke.NetherStuffs.Common.CommonProxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class OreFragment extends Item {
	//Metadata Values
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
	public static final int netherOreCopper = 11;
	public static final int netherOreTin = 12;
	public static final int netherOreSilver = 13;
	public static final int netherOreLead = 14;
	
	Icon icons[];
	
	public OreFragment(int par1) {
		super(par1);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		icons = new Icon[15];
		this.setCreativeTab(NetherStuffs.tabNetherStuffs);
	}

	@Override
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon(CommonProxy.getIconLocation("fragmentNetherOreDemonic"));
		icons[0] = iconRegister.registerIcon(CommonProxy.getIconLocation("fragmentNetherOreDemonic"));
		icons[1] = iconRegister.registerIcon(CommonProxy.getIconLocation("fragmentNetherStone"));
		icons[2] = iconRegister.registerIcon(CommonProxy.getIconLocation("fragmentNetherOreIron"));
		icons[3] = iconRegister.registerIcon(CommonProxy.getIconLocation("fragmentNetherOreGold"));
		icons[4] = iconRegister.registerIcon(CommonProxy.getIconLocation("fragmentNetherOreDiamond"));
		icons[5] = iconRegister.registerIcon(CommonProxy.getIconLocation("fragmentNetherOreRedstone"));
		icons[6] = iconRegister.registerIcon(CommonProxy.getIconLocation("fragmentNetherOreEmerald"));
		icons[7] = iconRegister.registerIcon(CommonProxy.getIconLocation("fragmentNetherOreCoal"));
		icons[8] = iconRegister.registerIcon(CommonProxy.getIconLocation("fragmentNetherOreObsidian"));
		icons[9] = iconRegister.registerIcon(CommonProxy.getIconLocation("fragmentNetherOreLapis"));
		icons[10] = iconRegister.registerIcon(CommonProxy.getIconLocation("fragmentNetherOreCobblestone"));
		icons[11] = iconRegister.registerIcon(CommonProxy.getIconLocation("fragmentNetherOreCopper"));
		icons[12] = iconRegister.registerIcon(CommonProxy.getIconLocation("fragmentNetherOreTin"));
		icons[13] = iconRegister.registerIcon(CommonProxy.getIconLocation("fragmentNetherOreSilver"));
		icons[14] = iconRegister.registerIcon(CommonProxy.getIconLocation("fragmentNetherOreLead"));
	}
	
	@Override
	public Icon getIconFromDamage(int par1) {
		if (par1>=0 && par1<=14)
			return icons[par1];
		else
			return itemIcon;
	}

	public static int getMetadataSize() {
		return OreItemBlock.getMetadataSize();
	}

	@Override
	public String getItemDisplayName(ItemStack is) {
		if (is.getItemDamage() < getMetadataSize() && is.getItemDamage() >= 0)
			return OreItemBlock.blockDisplayNames[is.getItemDamage()] + " Fragment";
		else
			return "Unknown Ore Fragment";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}
