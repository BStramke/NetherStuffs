package bstramke.NetherStuffs.Common;

import net.minecraft.item.ItemStack;
import bstramke.NetherStuffs.Blocks.BlockRegistry;
import bstramke.NetherStuffs.Blocks.Plank;
import bstramke.NetherStuffs.Blocks.Wood;
import bstramke.NetherStuffs.Items.ItemRegistry;
import cpw.mods.fml.common.IFuelHandler;

public class NetherStuffsFuel implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		if (fuel.itemID == ItemRegistry.NetherCoal.itemID)
			return 3200;

		if (fuel.itemID == new ItemStack(BlockRegistry.netherWood, 0, Wood.hellfire).itemID) {
			if (fuel.getItemDamage() == Wood.hellfire)
				return 600;
			else
				return 400;
		}

		if (fuel.itemID == new ItemStack(BlockRegistry.netherPlank, 0, Plank.hellfire).itemID)
			return 100;


		return 0;
	}

}
