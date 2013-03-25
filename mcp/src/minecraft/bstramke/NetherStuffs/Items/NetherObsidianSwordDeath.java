package bstramke.NetherStuffs.Items;

import bstramke.NetherStuffs.Common.CommonProxy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;

public class NetherObsidianSwordDeath extends NetherSword {

	public NetherObsidianSwordDeath(int itemId, EnumToolMaterial par2EnumToolMaterial) {
		super(itemId, par2EnumToolMaterial, Types.death);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}
	
	@Override
	public void updateIcons(IconRegister iconRegister)
	{
		iconIndex = iconRegister.registerIcon(CommonProxy.getIconLocation("SwordObsidianDeath"));
	}
}
