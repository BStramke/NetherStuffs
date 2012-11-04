package NetherStuffs.SoulWorkBench;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class SlotSoulWorkBenchGrid extends Slot {
	IInventory inventory;
	public SlotSoulWorkBenchGrid(IInventory par2iInventory, int par3, int par4, int par5) {
		super(par2iInventory, par3, par4, par5);
		inventory = par2iInventory;
	}

	public boolean isItemValid(ItemStack par1ItemStack) {
		return false;
	}
}
