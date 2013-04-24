/** 
 * Copyright (c) SpaceToad, 2011
 * http://www.mod-buildcraft.com
 * 
 * BuildCraft is distributed under the terms of the Minecraft Mod Public 
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */

package bstramke.NetherStuffs.Blocks.soulEngine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import bstramke.NetherStuffs.Common.ContainerWithPlayerInventory;

public class ContainerSoulEngine extends ContainerWithPlayerInventory {

	private int inventorySize = 1;
	protected TileSoulEngine engine;

	public ContainerSoulEngine(TileSoulEngine tileEngine, InventoryPlayer inventoryplayer) {

		engine = tileEngine;

		addSlotToContainer(new Slot(tileEngine, 0, 52, 41));
		
		bindPlayerInventory(inventoryplayer);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++) {
			engine.sendGUINetworkData(this, (ICrafting) crafters.get(i));
		}
	}

	@Override
	public void updateProgressBar(int i, int j) {
		if (engine != null) {
			engine.getGUINetworkData(i, j);
		}
	}

	public boolean isUsableByPlayer(EntityPlayer entityplayer) {
		return engine.isUseableByPlayer(entityplayer);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return engine.isUseableByPlayer(entityplayer);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer pl, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i < inventorySize) {
				if (!mergeItemStack(itemstack1, inventorySize, inventorySlots.size(), true))
					return null;
			} else if (!mergeItemStack(itemstack1, 0, inventorySize, false))
				return null;
			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
		}
		return itemstack;
	}

	public int getInventorySize() {
		return inventorySize;
	}
}
