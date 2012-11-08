package NetherStuffs.SoulDetector;

import java.util.Iterator;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICrafting;
import net.minecraftforge.common.ForgeDirection;

public class ContainerSoulDetector extends Container {
	protected TileSoulDetector tile_entity;
	//private int[] detectionRanges = new int[6];
	//private int[] detectionRangesMax = new int[6];

	public ContainerSoulDetector(TileSoulDetector tile_entity) {
		this.tile_entity = tile_entity;
		/*for (int i = 0; i < detectionRanges.length; i++) {
			detectionRanges[i] = 0;
			detectionRangesMax[i] = 20;
		}*/
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile_entity.isUseableByPlayer(player);

	}

	// Communication Stuff
	/*public void updateCraftingResults() {
		super.updateCraftingResults();
		Iterator var1 = this.crafters.iterator();

		int i = 0;
		while (var1.hasNext()) {
			ICrafting var2 = (ICrafting) var1.next();
			if (i < 6) {
				if (this.detectionRanges[i] != this.tile_entity.detectionRanges[i]) {
					var2.updateCraftingInventoryInfo(this, i,
							this.tile_entity.detectionRanges[i]);
				}
			}
			i++;
		}

	}*/

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		switch (par1) {
		case 0:
			this.tile_entity.setRange(ForgeDirection.NORTH, par2);
			break;
		case 1:
			this.tile_entity.setRange(ForgeDirection.SOUTH, par2);
			break;
		case 2:
			this.tile_entity.setRange(ForgeDirection.EAST, par2);
			break;
		case 3:
			this.tile_entity.setRange(ForgeDirection.WEST, par2);
			break;
		case 4:
			this.tile_entity.setRange(ForgeDirection.DOWN, par2);
			break;
		case 5:
			this.tile_entity.setRange(ForgeDirection.UP, par2);
			break;
		}
	}

	public void addCraftingToCrafters(ICrafting par1ICrafting) {
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.updateCraftingInventoryInfo(this, 0,
				this.tile_entity.getRange(ForgeDirection.NORTH));
		par1ICrafting.updateCraftingInventoryInfo(this, 1,
				this.tile_entity.getRange(ForgeDirection.SOUTH));
		par1ICrafting.updateCraftingInventoryInfo(this, 2,
				this.tile_entity.getRange(ForgeDirection.EAST));
		par1ICrafting.updateCraftingInventoryInfo(this, 3,
				this.tile_entity.getRange(ForgeDirection.WEST));
		par1ICrafting.updateCraftingInventoryInfo(this, 4,
				this.tile_entity.getRange(ForgeDirection.DOWN));
		par1ICrafting.updateCraftingInventoryInfo(this, 5,
				this.tile_entity.getRange(ForgeDirection.UP));
	}

}
