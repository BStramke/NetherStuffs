package bstramke.NetherStuffs.Items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import bstramke.NetherStuffs.NetherStuffs;

public class ItemRegistry {

	public static final Item NetherCoal = new NetherCoal(NetherStuffs.NetherWoodCharcoalItemId);
	public static final Item NetherOreIngot = new NetherOreIngot(NetherStuffs.NetherOreIngotItemId);
	public static final Item NetherWoodStick = new NetherWoodStick(NetherStuffs.NetherWoodStickItemId);

	public static void initOreDictionary() {
		OreDictionary.registerOre("ingotDemonic", new ItemStack(ItemRegistry.NetherOreIngot));
	}
}
