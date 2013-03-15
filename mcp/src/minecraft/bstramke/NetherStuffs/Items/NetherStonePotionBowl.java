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

public class NetherStonePotionBowl extends Item {

	public static String[] itemNames = new String[] { "NetherStonePotionBowlHellfire", "NetherStonePotionBowlAcid", "NetherStonePotionBowlDeath" };
	public static String[] itemDisplayNames = new String[] { "Hellfire Bowl", "Acid Bowl", "Death Bowl" };

	public static final int hellfire = 0;
	public static final int acid = 1;
	public static final int death = 2;
	private Icon icoBowlHellfire;
	private Icon icoBowlAcid;
	private Icon icoBowlDeath;

	public NetherStonePotionBowl(int par1) {
		super(par1);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabBrewing);
	}

	@Override
	public void func_94581_a(IconRegister iconRegister)
	{
		iconIndex = iconRegister.func_94245_a(CommonProxy.getIconLocation("NetherStonePotionBowl"));
		icoBowlHellfire = iconRegister.func_94245_a(CommonProxy.getIconLocation("NetherStonePotionBowlHellfire"));
		icoBowlAcid = iconRegister.func_94245_a(CommonProxy.getIconLocation("NetherStonePotionBowlAcid"));
		icoBowlDeath = iconRegister.func_94245_a(CommonProxy.getIconLocation("NetherStonePotionBowlDeath"));
	}

	@Override
	public Icon getIconFromDamage(int par1) {
		switch (par1) {
		case hellfire:
			return icoBowlHellfire;
		case acid:
			return icoBowlAcid;
		case death:
			return icoBowlDeath;
		default:
			return iconIndex;
		}
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

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}

}
