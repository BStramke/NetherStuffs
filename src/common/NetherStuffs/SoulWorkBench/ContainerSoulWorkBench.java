package NetherStuffs.SoulWorkBench;

import java.util.Iterator;

import net.minecraft.src.Container;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICrafting;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryCraftResult;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.SlotCrafting;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class ContainerSoulWorkBench extends Container {
	public IInventory craftResult = new InventoryCraftResult();
	protected TileSoulWorkBench soulworkbench;
	private int lastTankLevel = 0;
	private int lastProcessTime = 0;

	public ContainerSoulWorkBench(TileSoulWorkBench tile_entity, InventoryPlayer player_inventory) {
		this.soulworkbench = tile_entity;

		// Crafting Grid
		for (int i = 0; i < 9; i++) {
			if (i < 3)
				this.addSlotToContainer(new Slot(this.soulworkbench, i, 46 + (i) * 18, 17));
			else if (i < 6)
				this.addSlotToContainer(new Slot(this.soulworkbench, i, 46 + (i - 3) * 18, 35));
			else if (i < 9)
				this.addSlotToContainer(new Slot(this.soulworkbench, i, 46 + (i - 6) * 18, 53));
		}

		this.addSlotToContainer(new SlotSoulEnergyContainer(soulworkbench, tile_entity.nTankFillSlot, 12, 9));
		this.addSlotToContainer(new SlotCrafting(player_inventory.player, this.soulworkbench, this.craftResult, 2, 140, 35));

		bindPlayerInventory(player_inventory);
	}

	public void onCraftGuiClosed(EntityPlayer par1EntityPlayer) {
		/*
		 * super.onCraftGuiClosed(par1EntityPlayer);
		 * 
		 * if (!this.soulworkbench.worldObj.isRemote) { for (int var2 = 0; var2 < 9; ++var2) { ItemStack var3 = this.craftMatrix.getStackInSlotOnClosing(var2); if (var3 != null) {
		 * par1EntityPlayer.dropPlayerItem(var3); } } }
		 */
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return soulworkbench.isUseableByPlayer(player);
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
	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0) {
			this.soulworkbench.currentTankLevel = par2;
		}

		if (par1 == 1) {
			this.soulworkbench.processTime = par2;
		}
	}

	/*
	 * public void updateCraftingResults() { super.updateCraftingResults(); Iterator var1 = this.crafters.iterator();
	 * 
	 * while (var1.hasNext()) { ICrafting var2 = (ICrafting) var1.next();
	 * 
	 * if (this.lastTankLevel != this.soulworkbench.currentTankLevel) { var2.updateCraftingInventoryInfo(this, 0, this.soulworkbench.currentTankLevel); }
	 * 
	 * if (this.lastProcessTime != this.soulworkbench.processTime) { var2.updateCraftingInventoryInfo(this, 1, this.soulworkbench.processTime); } }
	 * 
	 * this.lastTankLevel = this.soulworkbench.currentTankLevel; this.lastProcessTime = this.soulworkbench.processTime; }
	 */

	public void addCraftingToCrafters(ICrafting par1ICrafting) {
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.updateCraftingInventoryInfo(this, 0, this.soulworkbench.currentTankLevel);
		par1ICrafting.updateCraftingInventoryInfo(this, 1, this.soulworkbench.processTime);
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	public void onCraftMatrixChanged(IInventory par1IInventory) {
		this.craftResult.setInventorySlotContents(0, SoulWorkBenchRecipes.getInstance().getCraftingResult(this.soulworkbench));
	}

}
