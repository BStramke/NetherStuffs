package bstramke.NetherStuffs.Items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TorchArrow extends Item {

	public TorchArrow(int par1) {
		super(par1);
		this.setCreativeTab(NetherStuffs.tabNetherStuffs);
		setUnlocalizedName("torchArrow");
		LanguageRegistry.instance().addStringLocalization("item.torchArrow.name", "Torch Arrow");
	}

	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon(CommonProxy.getIconLocation("TorchArrow"));
	}
}
