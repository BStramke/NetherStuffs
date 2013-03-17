package bstramke.NetherStuffs.Items;

import java.text.NumberFormat;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoulEnergyBottle extends Item {
	public static String[] itemDisplayNames = new String[] { "Small Soulenergy Bottle", "Medium Soulenergy Bottle", "Large Soulenergy Bottle", "Large Soulenergy Bottle FILLED" };

	public static final int small = 0;
	public static final int medium = 1;
	public static final int large = 2;
	public static final int largeFilled = 3;

	protected SoulEnergyBottle(int par1) {
		super(par1);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	public void func_94581_a(IconRegister iconRegister)
	{
		iconIndex = iconRegister.func_94245_a(CommonProxy.getIconLocation("SoulEnergyBottle"));
	}

	public static int getSoulEnergyAmount(ItemStack item) {
		if (item.getItemDamage() == largeFilled)
			return 100000;

		if (!item.hasTagCompound())
			return 0;
		else {
			updateOldToNewSoulEnergyTag(item);
			return item.getTagCompound().getInteger("SoulEnergyAmountNew");
		}
	}

	public static int addSoulEnergy(int nAmount, ItemStack item) {
		if (nAmount == 0)
			return 0;
		int nExistingAmount = 0;
		int nLimit = 100000;
		int nRest = 0;

		if (!item.hasTagCompound())
			item.stackTagCompound = new NBTTagCompound();
		else {
			updateOldToNewSoulEnergyTag(item);
			nExistingAmount = item.getTagCompound().getInteger("SoulEnergyAmountNew");
		}

		switch (item.getItemDamage()) {
		case small:
			nLimit = 1000;
			break;
		case medium:
			nLimit = 10000;
			break;
		case large:
			nLimit = 100000;
			break;
		case largeFilled:
			nLimit = 100000;
			break;
		}

		if (nExistingAmount + nAmount > nLimit) {
			nRest = (nExistingAmount + nAmount) - nLimit;
			nExistingAmount = nLimit;
		} else {
			nExistingAmount += nAmount;
		}

		item.getTagCompound().setInteger("SoulEnergyAmountNew", nExistingAmount);
		return nRest;
	}

	@Override
	public String getItemDisplayName(ItemStack is) {
		if (is.getItemDamage() < itemDisplayNames.length && is.getItemDamage() >= 0)
			return itemDisplayNames[is.getItemDamage()];
		else
			return  itemDisplayNames[0];
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < itemDisplayNames.length; metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}

	private static void updateOldToNewSoulEnergyTag(ItemStack par1ItemStack) {
		if (!par1ItemStack.hasTagCompound())
			return;

		int nAmount = 0;
		nAmount = par1ItemStack.getTagCompound().getInteger("SoulEnergyAmount");
		if (nAmount > 0) {
			nAmount *= 10;
			par1ItemStack.getTagCompound().removeTag("SoulEnergyAmount");
			par1ItemStack.getTagCompound().setInteger("SoulEnergyAmountNew", nAmount);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		int nExistingAmount = 0;
		updateOldToNewSoulEnergyTag(par1ItemStack);

		if (par1ItemStack.getItemDamage() == largeFilled) {
			par3List.add("Contains infinite Soulenergy");
			return;
		}

		int nLimit = 0;
		switch (par1ItemStack.getItemDamage()) {
		case small:
			nLimit = 1000;
			break;
		case medium:
			nLimit = 10000;
			break;
		case large:
			nLimit = 100000;
			break;
		case largeFilled:
			nLimit = 100000;
			break;
		}
		
		if (par1ItemStack.hasTagCompound() && par1ItemStack.stackTagCompound.hasKey("SoulEnergyAmountNew")) {
			nExistingAmount = par1ItemStack.getTagCompound().getInteger("SoulEnergyAmountNew");
			if (nExistingAmount > 0)
				par3List.add("Soulenergy: " + NumberFormat.getInstance().format(nExistingAmount) + " / " + NumberFormat.getInstance().format(nLimit));
			else
				par3List.add("Soulenergy: 0" + " / " + NumberFormat.getInstance().format(nLimit));
		} else {
			par3List.add("Soulenergy: 0" + " / " + NumberFormat.getInstance().format(nLimit));
		}
	}

	public static void decreaseSoulEnergyAmount(ItemStack item, int i) {
		if (i >= 0) {
			if (!item.hasTagCompound())
				item.stackTagCompound = new NBTTagCompound();

			updateOldToNewSoulEnergyTag(item);

			int nCurrentAmount = item.getTagCompound().getInteger("SoulEnergyAmountNew");
			int nNewAmount = nCurrentAmount - i;
			if (nNewAmount < 0)
				nNewAmount = 0;
			item.getTagCompound().setInteger("SoulEnergyAmountNew", nNewAmount);
		}
	}

	public static void setSoulEnergyAmount(ItemStack item, int i) {
		if (i >= 0) {
			updateOldToNewSoulEnergyTag(item);
			if (!item.hasTagCompound())
				item.stackTagCompound = new NBTTagCompound();
			item.getTagCompound().setInteger("SoulEnergyAmountNew", i);
		}
	}
}
