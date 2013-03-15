package bstramke.NetherStuffs.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherWoodCharcoal extends Item {
	
	public static String[] itemNames = new String[] { "NetherCharcoal", "NetherCoal" };
	public static String[] itemDisplayNames = new String[] { "Nether Charcoal", "Nether Coal" };
	
	public static int charcoal = 0;
	public static int coal = 1;
	protected NetherWoodCharcoal(int par1) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}
	
	@Override
	public void func_94581_a(IconRegister iconRegister)
	{
		iconIndex = iconRegister.func_94245_a(CommonProxy.getIconLocation("NetherCoal"));
	}
	
	public static int getMetadataSize() {
		return itemNames.length;
	}
	
	@Override
	public String getItemNameIS(ItemStack is) {
		String name = "";
		if (is.getItemDamage() < getMetadataSize() && is.getItemDamage() >= 0)
			name = itemNames[is.getItemDamage()];
		else
			name = itemNames[0];

		return getItemName() + "." + name;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}
