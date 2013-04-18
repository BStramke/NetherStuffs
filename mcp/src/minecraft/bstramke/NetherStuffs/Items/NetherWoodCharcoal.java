package bstramke.NetherStuffs.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherWoodCharcoal extends Item {
	
	public static String[] itemDisplayNames = new String[] { "Nether Charcoal", "Nether Coal" };
	
	public static int charcoal = 0;
	public static int coal = 1;
	protected NetherWoodCharcoal(int par1) {
		super(par1);
		this.setCreativeTab(NetherStuffs.tabNetherStuffs);
	}
	
	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon(CommonProxy.getIconLocation("NetherCoal"));
	}
	
	@Override
	public String getItemDisplayName(ItemStack is) {
		if (is.getItemDamage() < itemDisplayNames.length && is.getItemDamage() >= 0)
			return itemDisplayNames[is.getItemDamage()];
		else
			return  itemDisplayNames[0];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < itemDisplayNames.length; metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}
