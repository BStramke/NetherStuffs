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
	public void func_94581_a(IconRegister iconRegister)
	{
		iconIndex = iconRegister.func_94245_a(CommonProxy.getIconLocation("DemonicBarHandle"));
	}
}
