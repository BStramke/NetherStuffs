package NetherStuffs.DemonicFurnace;

import java.util.Iterator;

import NetherStuffs.Items.NetherItems;
import NetherStuffs.SoulFurnace.TileSoulFurnace;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICrafting;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.SlotFurnace;
import net.minecraft.src.TileEntityFurnace;

public class ContainerDemonicFurnace extends Container {
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

	protected void bindPlayerInventory(InventoryPlayer player_inventory) {
		int var3;

		for (var3 = 0; var3 < 3; ++var3) {
			for (int var4 = 0; var4 < 9; ++var4) {
				this.addSlotToContainer(new Slot(player_inventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
			}
		}

		for (var3 = 0; var3 < 9; ++var3) {
			this.addSlotToContainer(new Slot(player_inventory, var3, 8 + var3 * 18, 142));
		}

	}

	/**
	 * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
	 */
	@Override
	public void updateCraftingResults() {
		super.updateCraftingResults();
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
		ItemStack stack = null;
		Slot slot_object = (Slot) inventorySlots.get(slot_index);

		if (slot_object != null && slot_object.getHasStack()) {
			ItemStack stack_in_slot = slot_object.getStack();
			if (slot_object.inventory instanceof TileDemonicFurnace) {

				stack = stack_in_slot.copy();

				if (!mergeItemStack(stack_in_slot, 1, inventorySlots.size(), true)) {
					return null;
				}

			} else if (slot_object.inventory instanceof InventoryPlayer) {

				if (stack_in_slot.itemID == new ItemStack(NetherStuffs.NetherStuffs.NetherOreBlockId, 0, 0).itemID) {
					if (!mergeItemStack(stack_in_slot, 0, inventorySlots.size(), false))
						return null;
				} else if (stack_in_slot.itemID == NetherItems.NetherWoodCharcoal.shiftedIndex) {
					if (!mergeItemStack(stack_in_slot, 1, inventorySlots.size(), false))
						return null;
				}
			}

			if (stack_in_slot.stackSize == 0) {
				slot_object.putStack(null);
			} else {
				slot_object.onSlotChanged();
			}
		}

		return stack;
	}
}