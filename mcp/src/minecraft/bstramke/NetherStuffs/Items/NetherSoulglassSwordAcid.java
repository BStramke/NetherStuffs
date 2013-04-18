package bstramke.NetherStuffs.Items;

import bstramke.NetherStuffs.Common.CommonProxy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;

public class NetherSoulglassSwordAcid extends NetherSword {

	public NetherSoulglassSwordAcid(int par1, EnumToolMaterial par2EnumToolMaterial) {
		super(par1, par2EnumToolMaterial, Types.acid);
	}
	
	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon(CommonProxy.getIconLocation("SwordSoulglassAcid"));
	}
}
