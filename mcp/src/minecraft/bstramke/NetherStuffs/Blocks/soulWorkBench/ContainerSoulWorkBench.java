package bstramke.NetherStuffs.Blocks.soulWorkBench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import bstramke.NetherStuffs.Common.ContainerWithPlayerInventory;
import bstramke.NetherStuffs.Items.ItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerSoulWorkBench extends ContainerWithPlayerInventory {
	public IInventory craftResult = new InventoryCraftResult();
	protected TileSoulWorkBench soulworkbench;

	public ContainerSoulWorkBench(TileSoulWorkBench tile_entity, InventoryPlayer player_inventory) {
		this.soulworkbench = tile_entity;
		int offsetY = 0;
		// Crafting Grid
		for (int i = 0; i < 9; i++) {
			if (i < 3)
				this.addSlotToContainer(new Slot(this.soulworkbench, i, 46 + (i) * 18, 17 - offsetY));
			else if (i < 6)
				this.addSlotToContainer(new Slot(this.soulworkbench, i, 46 + (i - 3) * 18, 35 - offsetY));
			else if (i < 9)
				this.addSlotToContainer(new Slot(this.soulworkbench, i, 46 + (i - 6) * 18, 53 - offsetY));
		}

		this.addSlotToContainer(new SlotCraftingSoulWorkBench(player_inventory.player, this.soulworkbench, this.soulworkbench, this.soulworkbench.nOutputSlot, 140, 35 - offsetY));

		bindPlayerInventory(player_inventory, 97, 155);
	}

	public ContainerSoulWorkBench() {}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return soulworkbench.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot_index) {
		ItemStack stack = null;
		Slot slot_object = (Slot) inventorySlots.get(slot_index);
		if (slot_object != null && slot_object.getHasStack()) {
			ItemStack stack_in_slot = slot_object.getStack();
			stack = stack_in_slot.copy();

			if (slot_index == this.soulworkbench.nOutputSlot) {
				if (!this.mergeItemStack(stack_in_slot, 11, 46, true)) {
					return null;
				}
				slot_object.onSlotChange(stack_in_slot, stack);
			} else if (slot_index >= 11 && slot_index <= 37) { // player inventory
				if (!this.mergeItemStack(stack_in_slot, 38, 46, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(stack_in_slot, 11, 37, false)) {
				return null;
			}

			if (stack_in_slot.stackSize == 0) {
				slot_object.putStack(null);
			} else {
				slot_object.onSlotChanged();
			}

			if (stack_in_slot.stackSize == stack.stackSize)
				return null;

			slot_object.onPickupFromSlot(par1EntityPlayer, stack_in_slot);
		}
		return stack;
	}
}
