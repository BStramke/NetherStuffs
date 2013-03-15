package bstramke.NetherStuffs.Items;

import bstramke.NetherStuffs.Common.CommonProxy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;

public class NetherSoulglassSword extends NetherSword {

	public NetherSoulglassSword(int par1, EnumToolMaterial par2EnumToolMaterial) {
		super(par1, par2EnumToolMaterial);
	}
	
	@Override
	public void func_94581_a(IconRegister iconRegister)
	{
		iconIndex = iconRegister.func_94245_a(CommonProxy.getIconLocation("SwordSoulglass"));
	}
}
