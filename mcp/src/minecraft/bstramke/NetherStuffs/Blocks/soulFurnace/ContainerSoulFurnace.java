package bstramke.NetherStuffs.Blocks.soulFurnace;

import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import bstramke.NetherStuffs.Blocks.demonicFurnace.DemonicFurnaceRecipes;
import bstramke.NetherStuffs.Blocks.demonicFurnace.SlotDemonicFurnace;
import bstramke.NetherStuffs.Blocks.soulWorkBench.SlotSoulEnergyContainer;
import bstramke.NetherStuffs.Common.ContainerWithPlayerInventory;
import bstramke.NetherStuffs.Items.ItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerSoulFurnace extends ContainerWithPlayerInventory {

	protected TileSoulFurnace furnace;
	private int lastCookTime = 0;
	private int lastTankLevel = 0;

	public ContainerSoulFurnace(TileSoulFurnace tile_entity, InventoryPlayer player_inventory) {
		this.furnace = tile_entity;

		this.addSlotToContainer(new Slot(this.furnace, 0, 59, 35));
		this.addSlotToContainer(new SlotSoulEnergyContainer(this.furnace, tile_entity.nTankFillSlot, 12, 9));
		this.addSlotToContainer(new SlotDemonicFurnace(player_inventory.player, this.furnace, tile_entity.nOutputSlot, 116, 35, true));

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

			if (this.lastTankLevel != this.furnace.getCurrentTankLevel()) {
				var2.sendProgressBarUpdate(this, 1, this.furnace.getCurrentTankLevel());
			}
		}

		this.lastCookTime = this.furnace.furnaceCookTime;
		this.lastTankLevel = this.furnace.getCurrentTankLevel();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0) {
			this.furnace.furnaceCookTime = par2;
		} else if (par1 == 1) {
			this.furnace.setCurrentTankLevel(par2);
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting) {
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, this.furnace.furnaceCookTime);
		par1ICrafting.sendProgressBarUpdate(this, 1, this.furnace.getCurrentTankLevel());
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot_index) {
		ItemStack stack = null;
		Slot slot_object = (Slot) this.inventorySlots.get(slot_index);
		//System.out.println(slot_index);

		if (slot_object != null && slot_object.getHasStack()) {
			ItemStack stack_in_slot = slot_object.getStack();
			stack = stack_in_slot.copy();

			if (slot_index == 2) {
				if (!this.mergeItemStack(stack_in_slot, 3, 39, true)) {
					return null;
				}

				slot_object.onSlotChange(stack_in_slot, stack);
			} else if (slot_index != 1 && slot_index != 0) {
				if (FurnaceRecipes.smelting().getSmeltingResult(stack_in_slot) != null || DemonicFurnaceRecipes.smelting().getSmeltingResult(stack_in_slot) != null) {
					if (!this.mergeItemStack(stack_in_slot, 0, 1, false)) {
						return null;
					}
				} else if (stack_in_slot.itemID == ItemRegistry.SoulEnergyBottle.itemID) {
					if (!this.mergeItemStack(stack_in_slot, 1, 2, false)) {
						return null;
					}
				} else if (slot_index >= 3 && slot_index < 30) {
					if (!this.mergeItemStack(stack_in_slot, 30, 39, false)) {
						return null;
					}
				} else if (slot_index >= 30 && slot_index < 39 && !this.mergeItemStack(stack_in_slot, 3, 30, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(stack_in_slot, 3, 39, false)) {
				return null;
			}

			if (stack_in_slot.stackSize == 0) {
				slot_object.putStack((ItemStack) null);
			} else {
				slot_object.onSlotChanged();
			}

			if (stack_in_slot.stackSize == stack.stackSize) {
				return null;
			}

			slot_object.onPickupFromSlot(par1EntityPlayer, stack_in_slot);
		}

		return stack;
	}
}
