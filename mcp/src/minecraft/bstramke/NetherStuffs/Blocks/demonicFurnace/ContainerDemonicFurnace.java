package bstramke.NetherStuffs.Blocks.demonicFurnace;

import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import bstramke.NetherStuffs.Common.ContainerWithPlayerInventory;
import bstramke.NetherStuffs.Items.ItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerDemonicFurnace extends ContainerWithPlayerInventory {
	protected TileDemonicFurnace furnace;
	private int lastCookTime = 0;
	private int lastBurnTime = 0;
	private int lastItemBurnTime = 0;

	public ContainerDemonicFurnace(TileDemonicFurnace tile_entity, InventoryPlayer player_inventory) {
		this.furnace = tile_entity;

		this.addSlotToContainer(new Slot(furnace, 0, 56, 17));
		this.addSlotToContainer(new Slot(furnace, 1, 56, 53));
		this.addSlotToContainer(new SlotDemonicFurnace(player_inventory.player, furnace, 2, 116, 35));

		bindPlayerInventory(player_inventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return furnace.isUseableByPlayer(player);
	}

	/**
	 * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
	 */
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		Iterator var1 = this.crafters.iterator();

		while (var1.hasNext()) {
			ICrafting var2 = (ICrafting) var1.next();

			if (this.lastCookTime != this.furnace.furnaceCookTime) {
				var2.sendProgressBarUpdate(this, 0, this.furnace.furnaceCookTime);
			}

			if (this.lastBurnTime != this.furnace.furnaceBurnTime) {
				var2.sendProgressBarUpdate(this, 1, this.furnace.furnaceBurnTime);
			}

			if (this.lastItemBurnTime != this.furnace.currentItemBurnTime) {
				var2.sendProgressBarUpdate(this, 2, this.furnace.currentItemBurnTime);
			}
		}

		this.lastCookTime = this.furnace.furnaceCookTime;
		this.lastBurnTime = this.furnace.furnaceBurnTime;
		this.lastItemBurnTime = this.furnace.currentItemBurnTime;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0) {
			this.furnace.furnaceCookTime = par2;
		}

		if (par1 == 1) {
			this.furnace.furnaceBurnTime = par2;
		}

		if (par1 == 2) {
			this.furnace.currentItemBurnTime = par2;
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting) {
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, this.furnace.furnaceCookTime);
		par1ICrafting.sendProgressBarUpdate(this, 1, this.furnace.furnaceBurnTime);
		par1ICrafting.sendProgressBarUpdate(this, 2, this.furnace.currentItemBurnTime);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot_index) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(slot_index);
		// System.out.println(slot_index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slot_index == this.furnace.nOutputSlot) {
				if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
				
			} else if (slot_index != 1 && slot_index != 0) { // player inventory
				if (itemstack1.itemID == ItemRegistry.NetherWoodCharcoal.itemID) {
					if (!this.mergeItemStack(itemstack1, this.furnace.nFuelSlot, this.furnace.nFuelSlot + 1, false)) {
						return null;
					}
				} else if (itemstack1.itemID == new ItemStack(bstramke.NetherStuffs.NetherStuffs.NetherOreBlockId, 0, 0).itemID) {
					if (!this.mergeItemStack(itemstack1, this.furnace.nSmeltedSlot, this.furnace.nSmeltedSlot + 1, false)) {
						return null;
					}				
				} else if (slot_index >= 3 && slot_index < 30) {
					if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
						return null;
					}
				} else if (slot_index >= 30 && slot_index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
				return null;
			}
				
			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}
}