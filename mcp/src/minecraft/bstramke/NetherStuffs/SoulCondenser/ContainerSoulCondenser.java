package bstramke.NetherStuffs.SoulCondenser;

import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import bstramke.NetherStuffs.Blocks.SoulCondenser;
import bstramke.NetherStuffs.Items.NetherItems;
import bstramke.NetherStuffs.SoulSiphon.TileSoulSiphon;
import bstramke.NetherStuffs.SoulWorkBench.SlotSoulEnergyContainer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerSoulCondenser extends Container {
	protected TileSoulCondenser tile_entity;
	private int lastTankLevel = 0;

	public ContainerSoulCondenser(TileSoulCondenser tile_entity, InventoryPlayer player_inventory) {
		this.tile_entity = tile_entity;

		this.addSlotToContainer(new Slot(this.tile_entity, tile_entity.nTankDrainSlot, 59, 35));
		this.addSlotToContainer(new SlotSoulEnergyContainer(this.tile_entity, tile_entity.nTankFillSlot, 12, 9));
		bindPlayerInventory(player_inventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tile_entity.isUseableByPlayer(player);
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

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0) {
			this.tile_entity.setCurrentTankLevel(par2);
		}
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		Iterator var1 = this.crafters.iterator();

		while (var1.hasNext()) {
			ICrafting var2 = (ICrafting) var1.next();

			if (this.lastTankLevel != this.tile_entity.getCurrentTankLevel()) {
				var2.sendProgressBarUpdate(this, 0, this.tile_entity.getCurrentTankLevel());
			}
		}

		this.lastTankLevel = this.tile_entity.getCurrentTankLevel();
	}

	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting) {
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, this.tile_entity.getCurrentTankLevel());
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot_index) {
		ItemStack stack = null;
		Slot slot_object = (Slot) inventorySlots.get(slot_index);

		if (slot_object != null && slot_object.getHasStack()) {
			ItemStack stack_in_slot = slot_object.getStack();
			if (slot_object.inventory instanceof TileSoulCondenser) {

				stack = stack_in_slot.copy();

				if (!mergeItemStack(stack_in_slot, 1, inventorySlots.size(), true)) {
					return null;
				}

			} else if (slot_object.inventory instanceof InventoryPlayer) {

				if (stack_in_slot.itemID == NetherItems.SoulEnergyBottle.itemID) {
					if (!mergeItemStack(stack_in_slot, TileSoulCondenser.nTankFillSlot, TileSoulCondenser.nTankFillSlot + 1, false))
						return null;
				} else if (stack_in_slot.itemID == SoulCondenser.HSSoulKeeperItemId || stack_in_slot.itemID == SoulCondenser.HSSoulVesselItemId
						|| stack_in_slot.itemID == SoulCondenser.HSEssenceKeeperItemId || stack_in_slot.itemID == SoulCondenser.HSEssenceVesselItemId) {
					if (!mergeItemStack(stack_in_slot, TileSoulCondenser.nTankDrainSlot, TileSoulCondenser.nTankDrainSlot + 1, false))
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