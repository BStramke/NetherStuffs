package bstramke.NetherStuffs.Items;

import java.text.NumberFormat;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoulEnergyBottle extends Item {
	public static String[] itemDisplayNames = new String[] { "Small Soulenergy Bottle", "Medium Soulenergy Bottle", "Large Soulenergy Bottle", "Large Soulenergy Bottle FILLED" };

	public static final int small = 0;
	public static final int medium = 1;
	public static final int large = 2;
	public static final int largeFilled = 3;
	private Icon icoFillings[];
	
	protected SoulEnergyBottle(int par1) {
		super(par1);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(NetherStuffs.tabNetherStuffs);
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int damage) {
		if(damage <= large)
			return iconIndex;
		if(damage == largeFilled)
			return icoFillings[7];
		
		if(damage>=4 && damage<=11)
			return icoFillings[damage-4];
		if(damage>=12 && damage<=19)
			return icoFillings[damage-12];
		if(damage>=20 && damage<=27)
			return icoFillings[damage-20];
		
		return iconIndex;
	}
	
	@Override
	public void updateIcons(IconRegister iconRegister)
	{
		icoFillings = new Icon[8];
		iconIndex = iconRegister.registerIcon(CommonProxy.getIconLocation("SoulEnergyBottle"));
		for(int i = 1; i <= icoFillings.length; i++)
			icoFillings[i-1] = iconRegister.registerIcon(CommonProxy.getIconLocation("SoulEnergyBottle_"+i));
	}

	public static int getSoulEnergyAmount(ItemStack item) {
		if (getTypeFromDamage(item.getItemDamage()) == largeFilled)
			return 100000;

		if (!item.hasTagCompound())
			return 0;
		else {
			updateOldToNewSoulEnergyTag(item);
			return item.getTagCompound().getInteger("SoulEnergyAmountNew");
		}
	}

	private static int getTypeFromDamage(int damage) {
		if(damage<=3)
			return damage;
		
		
		if(damage>=4 && damage<=11)
			return small;
		if(damage>=12 && damage<=19)
			return medium;
		if(damage>=20 && damage<=27)
			return large;
		
		return small;
	}
	
	private static int getSoulEnergyAmountMax(ItemStack item) {
		int itemDamage = item.getItemDamage();
		switch (getTypeFromDamage(item.getItemDamage())) {
			case small:
				return 1000;
			case medium:
				return 10000;
			case large:
				return 100000;
			case largeFilled:
				return 100000;
			default: return 100000;
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

		nLimit = getSoulEnergyAmountMax(item);

		if (nExistingAmount + nAmount > nLimit) {
			nRest = (nExistingAmount + nAmount) - nLimit;
			nExistingAmount = nLimit;
		} else {
			nExistingAmount += nAmount;
		}

		item.getTagCompound().setInteger("SoulEnergyAmountNew", nExistingAmount);
		updateItemForScale(item);
		return nRest;
	}

	private static int getFillingScale(ItemStack item) {
		int amount = getSoulEnergyAmount(item);
		int maxamount = getSoulEnergyAmountMax(item);
		return (int)Math.floor(((double)amount / (double)maxamount)*(double)8);
	}
	
	private static void updateItemForScale(ItemStack item) {
		int scale = getFillingScale(item);
		int type = getTypeFromDamage(item.getItemDamage());
		if(scale == 0)
			item.setItemDamage(type);
		else
		{
			switch(type) {
			case small:
				item.setItemDamage(scale + 3); break;
			case medium:
				item.setItemDamage(scale + 3 + 8); break;
			case large: 
				item.setItemDamage(scale + 3 + 8 + 8); break;
			}
		}
	}
	
	@Override
	public String getItemDisplayName(ItemStack is) {
		if (getTypeFromDamage(is.getItemDamage()) < itemDisplayNames.length && is.getItemDamage() >= 0)
			return itemDisplayNames[getTypeFromDamage(is.getItemDamage())];
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

		if (getTypeFromDamage(par1ItemStack.getItemDamage()) == largeFilled) {
			par3List.add("Contains infinite Soulenergy");
			return;
		}

		int nLimit = getSoulEnergyAmountMax(par1ItemStack);
		
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
			updateItemForScale(item);
		}
	}

	public static void setSoulEnergyAmount(ItemStack item, int i) {
		if (i >= 0) {
			updateOldToNewSoulEnergyTag(item);
			if (!item.hasTagCompound())
				item.stackTagCompound = new NBTTagCompound();
			item.getTagCompound().setInteger("SoulEnergyAmountNew", i);
			updateItemForScale(item);
		}
	}
}
