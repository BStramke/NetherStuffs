package codechicken.nei.api;

import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;

public interface IInfiniteItemHandler
{
	void onPickup(ItemStack heldItem);
	void onPlaceInfinite(ItemStack heldItem);
	
	boolean canHandleItem(ItemStack stack);
	boolean isItemInfinite(ItemStack stack);
	
	public void replenishInfiniteStack(InventoryPlayer inventory, int slotNo);
	public ItemStack getInfiniteItem(ItemStack typeStack);
}
