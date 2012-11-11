package NetherStuffs.SoulWorkBench;

import NetherStuffs.NetherStuffs;
import NetherStuffs.Items.NetherItems;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class SlotSoulEnergyContainer extends Slot {
	public final IInventory inventory;

	public SlotSoulEnergyContainer(IInventory par2iInventory, int par3, int par4, int par5) {
		super(par2iInventory, par3, par4, par5);
		inventory = par2iInventory;
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		if (par1ItemStack.itemID == NetherItems.SoulEnergyBottle.shiftedIndex)
			return true;
		else
			return false;
	}
}
