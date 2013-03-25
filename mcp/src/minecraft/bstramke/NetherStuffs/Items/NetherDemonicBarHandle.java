package bstramke.NetherStuffs.Items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import bstramke.NetherStuffs.Common.CommonProxy;

public class NetherDemonicBarHandle extends Item {

	public NetherDemonicBarHandle(int par1) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}

	@Override
	public void updateIcons(IconRegister iconRegister)
	{
		iconIndex = iconRegister.registerIcon(CommonProxy.getIconLocation("DemonicBarHandle"));
	}
}
