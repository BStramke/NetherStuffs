package bstramke.NetherStuffs.Items;

import java.util.List;

import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.OreExtendedItemBlock;
import bstramke.NetherStuffs.Common.CommonProxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class OreFragmentExtended extends Item {
	// Metadata Values
	public static final int netherOreCertusQuartz = 0;
	public static final int netherOreFerrous = 1;
	public static final int netherOreApatite = 2;
	public static final int netherOreUranium = 3;

	Icon icons[];

	public OreFragmentExtended(int par1) {
		super(par1);
		this.setMaxDamage(0);
		this.setCreativeTab(NetherStuffs.tabNetherStuffs);
		this.setHasSubtypes(true);
		icons = new Icon[4];
	}
	
	@Override
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon(CommonProxy.getIconLocation("fragmentNetherOreCertusQuartz"));
		icons[0] = iconRegister.registerIcon(CommonProxy.getIconLocation("fragmentNetherOreCertusQuartz"));
		icons[1] = iconRegister.registerIcon(CommonProxy.getIconLocation("fragmentNetherOreFerrous"));
		icons[2] = iconRegister.registerIcon(CommonProxy.getIconLocation("fragmentNetherOreApatite"));
		icons[3] = iconRegister.registerIcon(CommonProxy.getIconLocation("fragmentNetherOreUranium"));
	}

	@Override
	public Icon getIconFromDamage(int par1) {
		if(par1>=0 && par1<=3)
			return icons[par1];
		else
			return itemIcon;
	}

	public static int getMetadataSize() {
		return OreExtendedItemBlock.getMetadataSize();
	}

	@Override
	public String getItemDisplayName(ItemStack is) {
		if (is.getItemDamage() < getMetadataSize() && is.getItemDamage() >= 0)
			return OreExtendedItemBlock.blockDisplayNames[is.getItemDamage()] + " Fragment";
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
