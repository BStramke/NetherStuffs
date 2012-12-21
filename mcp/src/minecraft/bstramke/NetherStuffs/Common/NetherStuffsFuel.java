package bstramke.NetherStuffs.Common;

import net.minecraft.item.ItemStack;
import bstramke.NetherStuffs.Blocks.NetherBlocks;
import bstramke.NetherStuffs.Blocks.NetherPlank;
import bstramke.NetherStuffs.Blocks.NetherWood;
import bstramke.NetherStuffs.Items.NetherItems;
import cpw.mods.fml.common.IFuelHandler;

public class NetherStuffsFuel implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		if (fuel.itemID == NetherItems.NetherWoodCharcoal.shiftedIndex)
			return 3200;

		if (fuel.itemID == new ItemStack(NetherBlocks.netherWood, 0, NetherWood.hellfire).itemID) {
			if (fuel.getItemDamage() == NetherWood.hellfire)
				return 600;
			else
				return 400;
		}

		if (fuel.itemID == new ItemStack(NetherBlocks.netherPlank, 0, NetherPlank.hellfire).itemID)
			return 100;

		return 0;
	}

}
