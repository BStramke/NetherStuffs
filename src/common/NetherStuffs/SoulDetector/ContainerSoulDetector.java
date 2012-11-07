package NetherStuffs.SoulDetector;

import java.util.Iterator;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICrafting;

public class ContainerSoulDetector extends Container {
	private TileSoulDetector tile_entity;

	public ContainerSoulDetector(TileSoulDetector tile_entity) {
		this.tile_entity = tile_entity;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile_entity.isUseableByPlayer(player);
		
	}
	
	//Communication Stuff
	public void updateCraftingResults() {
		super.updateCraftingResults();
		/*Iterator var1 = this.crafters.iterator();

		while (var1.hasNext()) {
			ICrafting var2 = (ICrafting) var1.next();

			if (this.lastCookTime != this.furnace.furnaceCookTime) {
				var2.updateCraftingInventoryInfo(this, 0, this.furnace.furnaceCookTime);
			}

			if (this.lastBurnTime != this.furnace.furnaceBurnTime) {
				var2.updateCraftingInventoryInfo(this, 1, this.furnace.furnaceBurnTime);
			}

			if (this.lastItemBurnTime != this.furnace.currentItemBurnTime) {
				var2.updateCraftingInventoryInfo(this, 2, this.furnace.currentItemBurnTime);
			}
		}

		this.lastCookTime = this.furnace.furnaceCookTime;
		this.lastBurnTime = this.furnace.furnaceBurnTime;
		this.lastItemBurnTime = this.furnace.currentItemBurnTime;*/
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		/*if (par1 == 0) {
			this.furnace.furnaceCookTime = par2;
		}

		if (par1 == 1) {
			this.furnace.furnaceBurnTime = par2;
		}

		if (par1 == 2) {
			this.furnace.currentItemBurnTime = par2;
		}*/
	}
	
	public void addCraftingToCrafters(ICrafting par1ICrafting) {
		super.addCraftingToCrafters(par1ICrafting);
		/*par1ICrafting.updateCraftingInventoryInfo(this, 0, this.furnace.furnaceCookTime);
		par1ICrafting.updateCraftingInventoryInfo(this, 1, this.furnace.furnaceBurnTime);
		par1ICrafting.updateCraftingInventoryInfo(this, 2, this.furnace.currentItemBurnTime);*/
	}
}
