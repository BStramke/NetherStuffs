package bstramke.NetherStuffs.Blocks.soulSiphon;

import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import bstramke.NetherStuffs.Blocks.soulWorkBench.SlotSoulEnergyContainer;
import bstramke.NetherStuffs.Common.ContainerWithPlayerInventory;
import bstramke.NetherStuffs.Items.ItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerSoulSiphon extends ContainerWithPlayerInventory {
	protected TileSoulSiphon tile_entity;
	private int lastTankLevel = 0;

	public ContainerSoulSiphon(TileSoulSiphon tile_entity, InventoryPlayer player_inventory) {
		this.tile_entity = tile_entity;

		this.addSlotToContainer(new Slot(this.tile_entity, tile_entity.nTankDrainSlot, 59, 35));
		this.addSlotToContainer(new SlotSoulEnergyContainer(this.tile_entity, tile_entity.nTankFillSlot, 12, 9));
		bindPlayerInventory(player_inventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tile_entity.isUseableByPlayer(player);
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
			if (slot_object.inventory instanceof TileSoulSiphon) {

				stack = stack_in_slot.copy();

				if (!mergeItemStack(stack_in_slot, 1, inventorySlots.size(), true)) {
					return null;
				}

			} else if (slot_object.inventory instanceof InventoryPlayer) {

				if (stack_in_slot.itemID == ItemRegistry.SoulEnergyBottle.itemID) {
					if (!mergeItemStack(stack_in_slot, 0, 1, false))
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
