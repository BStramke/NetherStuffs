package bstramke.NetherStuffs.Items.swords;

import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class NetherDiamondSwordAcid extends NetherSword {
	public NetherDiamondSwordAcid(int itemId, EnumToolMaterial par2EnumToolMaterial) {
		super(itemId, par2EnumToolMaterial, Types.acid);
		setUnlocalizedName("NetherDiamondSword");
	}
	
	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon(CommonProxy.getIconLocation("SwordDiamondAcid"));
	}
}
