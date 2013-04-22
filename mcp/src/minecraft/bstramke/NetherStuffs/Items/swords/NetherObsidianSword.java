package bstramke.NetherStuffs.Items.swords;

import cpw.mods.fml.common.registry.LanguageRegistry;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;

public class NetherObsidianSword extends NetherSword {

	public NetherObsidianSword(int itemId, EnumToolMaterial par2EnumToolMaterial) {
		super(itemId, par2EnumToolMaterial);
		setUnlocalizedName("NetherObsidianSword");
		LanguageRegistry.instance().addStringLocalization("item.NetherObsidianSword.name", "Obsidian Sword");
	}
	
	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon(CommonProxy.getIconLocation("SwordObsidian"));
	}
}
