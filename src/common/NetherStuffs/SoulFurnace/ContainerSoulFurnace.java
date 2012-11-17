package NetherStuffs.SoulFurnace;

import java.util.Iterator;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.ICrafting;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.TileEntityFurnace;
import NetherStuffs.DemonicFurnace.DemonicFurnaceRecipes;
import NetherStuffs.DemonicFurnace.SlotDemonicFurnace;
import NetherStuffs.DemonicFurnace.TileDemonicFurnace;
import NetherStuffs.Items.NetherItems;
import NetherStuffs.SoulWorkBench.SlotSoulEnergyContainer;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class ContainerSoulFurnace extends Container {

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
				var2.updateCraftingInventoryInfo(this, 0, this.furnace.furnaceCookTime);
			}

			if (this.lastTankLevel != this.furnace.currentTankLevel) {
				var2.updateCraftingInventoryInfo(this, 1, this.furnace.currentTankLevel);
			}
		}

		this.lastCookTime = this.furnace.furnaceCookTime;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0) {
			this.furnace.furnaceCookTime = par2;
		} else if (par1 == 1) {
			this.furnace.currentTankLevel = par2;
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting) {
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.updateCraftingInventoryInfo(this, 0, this.furnace.furnaceCookTime);
		par1ICrafting.updateCraftingInventoryInfo(this, 1, this.furnace.currentTankLevel);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot_index) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(slot_index);

		if (var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if (slot_index == 2) {
				if (!this.mergeItemStack(var5, 3, 39, true)) {
					return null;
				}

				var4.onSlotChange(var5, var3);
			} else if (slot_index != 1 && slot_index != 0) {
				if (FurnaceRecipes.smelting().getSmeltingResult(var5) != null || DemonicFurnaceRecipes.smelting().getSmeltingResult(var5) != null) {
					if (!this.mergeItemStack(var5, 0, 1, false)) {
						return null;
					}
				} else if (var5.itemID == NetherItems.SoulEnergyBottle.shiftedIndex) {
					if (!this.mergeItemStack(var5, 1, 2, false)) {
						return null;
					}
				} else if (slot_index >= 3 && slot_index < 30) {
					if (!this.mergeItemStack(var5, 30, 39, false)) {
						return null;
					}
				} else if (slot_index >= 30 && slot_index < 39 && !this.mergeItemStack(var5, 3, 30, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(var5, 3, 39, false)) {
				return null;
			}

			if (var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}

			if (var5.stackSize == var3.stackSize) {
				return null;
			}

			var4.onPickupFromSlot(par1EntityPlayer, var5);
		}

		return var3;
	}
}
