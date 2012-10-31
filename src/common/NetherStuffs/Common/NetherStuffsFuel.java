package NetherStuffs.Common;

import NetherStuffs.Blocks.NetherBlocks;
import NetherStuffs.Items.NetherItems;
import net.minecraft.src.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class NetherStuffsFuel implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		if (fuel.itemID == NetherItems.NetherWoodCharcoal.shiftedIndex)
			return 3200;
		
		return 0;
	}

}
