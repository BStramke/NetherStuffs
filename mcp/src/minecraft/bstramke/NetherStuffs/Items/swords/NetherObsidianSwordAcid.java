package bstramke.NetherStuffs.Items.swords;

import cpw.mods.fml.common.registry.LanguageRegistry;
import bstramke.NetherStuffs.Common.CommonProxy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;

public class NetherObsidianSwordAcid extends NetherSword {

	public NetherObsidianSwordAcid(int itemId, EnumToolMaterial par2EnumToolMaterial) {
		super(itemId, par2EnumToolMaterial, Types.acid);
		setUnlocalizedName("NetherObsidianSword");
		LanguageRegistry.instance().addStringLocalization("item.NetherObsidianSword.name", "Obsidian Sword");
	}
	
	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon(CommonProxy.getIconLocation("SwordObsidianAcid"));
	}
}
