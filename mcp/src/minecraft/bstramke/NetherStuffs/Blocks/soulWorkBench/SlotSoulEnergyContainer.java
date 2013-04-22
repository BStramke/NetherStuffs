package bstramke.NetherStuffs.Blocks.soulWorkBench;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import bstramke.NetherStuffs.Items.ItemRegistry;

public class SlotSoulEnergyContainer extends Slot {
	public final IInventory inventory;

	public SlotSoulEnergyContainer(IInventory par2iInventory, int par3, int par4, int par5) {
		super(par2iInventory, par3, par4, par5);
		inventory = par2iInventory;
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		if (par1ItemStack.itemID == ItemRegistry.SoulEnergyBottle.itemID)
			return true;
		else
			return false;
	}
}
