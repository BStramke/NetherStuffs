package bstramke.NetherStuffs.mc15compat;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Items.NetherPotionBottle;

public class CompatItem extends Item {
	public static final Item netherQuartz = (new CompatItem(406 - 256)).setIconCoord(15, 15).setItemName("netherQuartz").setCreativeTab(CreativeTabs.tabMaterials);
	public static final Item netherBrick = (new CompatItem(405 - 256)).setIconCoord(14, 15).setItemName("netherBrick").setCreativeTab(CreativeTabs.tabMaterials);

	public CompatItem(int par1) {
		super(par1);
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.ITEMS_PNG;
	}
}
