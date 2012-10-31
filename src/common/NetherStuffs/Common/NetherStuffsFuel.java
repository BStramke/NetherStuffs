package NetherStuffs.Common;

import net.minecraft.src.ItemStack;
import NetherStuffs.Blocks.NetherBlocks;
import NetherStuffs.Blocks.NetherWood;
import NetherStuffs.Items.NetherItems;
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

		return 0;
	}

}
