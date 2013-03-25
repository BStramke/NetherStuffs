package bstramke.NetherStuffs.Items;

import bstramke.NetherStuffs.Common.CommonProxy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;

public class NetherSoulglassSwordHellfire extends NetherSword {

	public NetherSoulglassSwordHellfire(int par1, EnumToolMaterial par2EnumToolMaterial) {
		super(par1, par2EnumToolMaterial, Types.hellfire);
	}
	
	@Override
	public void updateIcons(IconRegister iconRegister)
	{
		iconIndex = iconRegister.registerIcon(CommonProxy.getIconLocation("SwordSoulglassHellfire"));
	}
}
