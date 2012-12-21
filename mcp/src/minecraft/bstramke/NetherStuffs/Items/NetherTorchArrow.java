package bstramke.NetherStuffs.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherTorchArrow extends Item {

	public NetherTorchArrow(int par1) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getIconFromDamage(int par1) {
		return 64;
	}
	
	@Override
	public String getTextureFile() {
		return CommonProxy.ITEMS_PNG;
	}
}
