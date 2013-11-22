package bstramke.NetherStuffs.Items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.NetherStuffs.IDs;

public class ItemRegistry {

	public static final Item NetherCoal = new NetherCoal(NetherStuffs.IDs.Items.NetherWoodCharcoalItemId);
	public static final Item NetherOreIngot = new NetherOreIngot(NetherStuffs.IDs.Items.NetherOreIngotItemId);
	public static final Item NetherWoodStick = new NetherWoodStick(NetherStuffs.IDs.Items.NetherWoodStickItemId);
	public static final Item OreFragment = new OreFragment(NetherStuffs.IDs.Items.OreFragmentItemId);
	public static final Item OreFragmentExtended = new OreFragmentExtended(NetherStuffs.IDs.Items.OreExtendedFragmentItemId);

	public static void initOreDictionary() {
		OreDictionary.registerOre("ingotDemonic", new ItemStack(ItemRegistry.NetherOreIngot));
	}
}
