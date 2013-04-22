package bstramke.NetherStuffs.Items;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;

public class DemonicBarHandle extends Item {

	public DemonicBarHandle(int par1) {
		super(par1);
		this.setCreativeTab(NetherStuffs.tabNetherStuffs);
		setUnlocalizedName("NetherDemonicBarHandle");
		LanguageRegistry.instance().addStringLocalization("item.NetherDemonicBarHandle.name", "Demonic Haft");
	}

	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon(CommonProxy.getIconLocation("DemonicBarHandle"));
	}
}
