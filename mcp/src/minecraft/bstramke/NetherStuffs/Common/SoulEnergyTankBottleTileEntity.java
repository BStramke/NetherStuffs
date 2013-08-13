package bstramke.NetherStuffs.Common;

import bstramke.NetherStuffs.Items.ItemRegistry;
import bstramke.NetherStuffs.Items.SoulEnergyBottle;
import net.minecraft.item.ItemStack;

public abstract class SoulEnergyTankBottleTileEntity extends SoulEnergyTankTileEntity {
	public static int nTankFillSlot;
	public static int nTankDrainSlot;
	private ItemStack inventory[];
	
	public SoulEnergyTankBottleTileEntity(int TankFillSlot, int TankDrainSlot, int MaxTankLevel) {
		super(MaxTankLevel);
		nTankFillSlot = TankFillSlot;
		nTankDrainSlot = TankDrainSlot;
	}
	
	public abstract void fillFuelToBottle();
}
