package bstramke.NetherStuffs.Common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerWithPlayerInventory extends Container {

	public void bindPlayerInventory(InventoryPlayer player_inventory) {
		bindPlayerInventory(player_inventory, 84, 142);
	}
	
	public void bindPlayerInventory(InventoryPlayer player_inventory, int yPosStart, int yPosHotbar) {
		int var3;
		for (var3 = 0; var3 < 3; ++var3) {
			for (int var4 = 0; var4 < 9; ++var4) {
				this.addSlotToContainer(new Slot(player_inventory, var4 + var3 * 9 + 9, 8 + var4 * 18, yPosStart + var3 * 18));
			}
		}

		for (var3 = 0; var3 < 9; ++var3) {
			this.addSlotToContainer(new Slot(player_inventory, var3, 8 + var3 * 18, yPosHotbar));
		}
	}
	
	@Override
	public abstract ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2);

}
