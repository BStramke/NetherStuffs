package bstramke.NetherStuffs.Items.swords;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import bstramke.NetherStuffs.Common.CommonProxy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;

public class NetherSoulglassSwordHellfire extends NetherSword {

	public NetherSoulglassSwordHellfire(int par1, EnumToolMaterial par2EnumToolMaterial) {
		super(par1, par2EnumToolMaterial, Types.hellfire);
		setUnlocalizedName("NetherSoulglassSword");
		LanguageRegistry.instance().addStringLocalization("item.NetherSoulglassSword.name", "Soulglass Sword");
	}
	
	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon(CommonProxy.getIconLocation("SwordSoulglassHellfire"));
	}
}
