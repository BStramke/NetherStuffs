package NetherStuffs.Items;

import java.util.List;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class SoulEnergyBottle extends Item {

	public static String[] itemNames = new String[] { "SoulEnergyBottleSmall", "SoulEnergyBottleMedium", "SoulEnergyBottleLarge" };
	public static String[] itemDisplayNames = new String[] { "Small Soulenergy Bottle", "Medium Soulenergy Bottle", "Large Soulenergy Bottle" };

	public static final int small = 0;
	public static final int medium = 1;
	public static final int large = 2;

	protected SoulEnergyBottle(int par1) {
		super(par1);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	public boolean getShareTag() {
		return true;
	}

	public String getTextureFile() {
		return "/items.png";
	}

	public int getIconFromDamage(int par1) {
		/*switch (par1) {
		case small:
			return 48;
		case medium:
			return 49;
		case large:
			return 50;
		default:
			return 19;
		}*/
		return 16;
	}

	public static int getMetadataSize() {
		return itemNames.length;
	}

	public static boolean addSoulEnergy(int nAmount, ItemStack item) {
		int nExistingAmount = 0;

		if (!item.hasTagCompound())
			item.stackTagCompound = new NBTTagCompound();
		else {
			nExistingAmount = item.getTagCompound().getInteger("SoulEnergyAmount");
		}

		nExistingAmount += nAmount;
		item.getTagCompound().setInteger("SoulEnergyAmount", nExistingAmount);

		/*
		 * { item.setTagCompound(nbt) }
		 */

		return true;
	}

	public String getItemNameIS(ItemStack is) {
		String name = "";
		if (is.getItemDamage() < getMetadataSize() && is.getItemDamage() >= 0)
			name = itemNames[is.getItemDamage()];
		else
			name = itemNames[0];

		return getItemName() + "." + name;
	}

	public int getMetadata(int meta) {
		return meta;
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		int nExistingAmount = 0;
		if (par1ItemStack.hasTagCompound() && par1ItemStack.stackTagCompound.hasKey("SoulEnergyAmount")) {
			nExistingAmount = par1ItemStack.getTagCompound().getInteger("SoulEnergyAmount");
			par3List.add("Contains Soulenergy: "+((Integer) nExistingAmount).toString());
		}
	}

}
