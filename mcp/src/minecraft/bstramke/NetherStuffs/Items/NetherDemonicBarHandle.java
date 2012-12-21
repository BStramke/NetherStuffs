package bstramke.NetherStuffs.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import bstramke.NetherStuffs.Common.CommonProxy;

public class NetherDemonicBarHandle extends Item {

	public NetherDemonicBarHandle(int par1) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.ITEMS_PNG;
	}
}
