package bstramke.NetherStuffs.Items;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;

public class NetherWoodStick extends Item {

	public NetherWoodStick(int par1) {
		super(par1);
		this.setCreativeTab(NetherStuffs.tabNetherStuffs);
		setUnlocalizedName("NetherWoodStick");
		LanguageRegistry.instance().addStringLocalization("item.NetherWoodStick.name", "Nether Stick");
	}

	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon(CommonProxy.getIconLocation("NetherWoodStick"));
	}
}
