package NetherStuffs.Items;

import java.util.List;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class NetherPotionBottle extends Item {

	public static String[] itemNames = new String[] { "NetherPotionBottleHellfire" , "NetherPotionBottleAcid", "NetherPotionBottleDeath"};
	public static String[] itemDisplayNames = new String[] { "Hellfire Potion" , "Acid Potion", "Death Potion"};
	
	public static final int hellfire = 0;
	public static final int acid = 1;
	public static final int death = 2;

	public NetherPotionBottle(int par1) {
		super(par1);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabBrewing);
	}
	
	public String getTextureFile() {
		return "/items.png";
	}

	public int getIconFromDamage(int par1) {
		switch (par1) {
		case hellfire:
			return 32;
		case acid:
			return 33;
		case death:
			return 34;
		default:
			return 16;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack) {
		return true;
	}
	
	public static int getMetadataSize() {
		return itemNames.length;
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

}
