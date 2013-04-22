package bstramke.NetherStuffs.Items.swords;

import cpw.mods.fml.common.registry.LanguageRegistry;
import bstramke.NetherStuffs.Common.CommonProxy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;

public class NetherSoulglassSwordDeath extends NetherSword {

	public NetherSoulglassSwordDeath(int par1, EnumToolMaterial par2EnumToolMaterial) {
		super(par1, par2EnumToolMaterial, Types.death);
		setUnlocalizedName("NetherSoulglassSword");
		LanguageRegistry.instance().addStringLocalization("item.NetherSoulglassSword.name", "Soulglass Sword");
	}
	
	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon(CommonProxy.getIconLocation("SwordSoulglassDeath"));
	}
}
