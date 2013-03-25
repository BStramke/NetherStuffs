package bstramke.NetherStuffs.Items;

import bstramke.NetherStuffs.Common.CommonProxy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class NetherDiamondSwordAcid extends NetherSword {
	public NetherDiamondSwordAcid(int itemId, EnumToolMaterial par2EnumToolMaterial) {
		super(itemId, par2EnumToolMaterial, Types.acid);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}
	
	@Override
	public void updateIcons(IconRegister iconRegister)
	{
		iconIndex = iconRegister.registerIcon(CommonProxy.getIconLocation("SwordDiamondAcid"));
	}
}
