package bstramke.NetherStuffs.Blocks.soulEngine;

import java.util.LinkedList;

import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;

public class SoulEngineFuel {

	public static LinkedList<SoulEngineFuel> fuels = new LinkedList<SoulEngineFuel>();

	public static SoulEngineFuel getFuelForLiquid(LiquidStack liquid) {
		if (liquid == null)
			return null;
		if (liquid.itemID <= 0)
			return null;

		for (SoulEngineFuel fuel : fuels)
			if (fuel.liquid.isLiquidEqual(liquid))
				return fuel;

		return null;
	}

	public final LiquidStack liquid;
	public final float powerPerCycle;
	public final int totalBurningTime;

	public SoulEngineFuel(int liquidId, float powerPerCycle, int totalBurningTime) {
		this(new LiquidStack(liquidId, LiquidContainerRegistry.BUCKET_VOLUME, 0), powerPerCycle, totalBurningTime);
	}

	public SoulEngineFuel(LiquidStack liquid, float powerPerCycle, int totalBurningTime) {
		this.liquid = liquid;
		this.powerPerCycle = powerPerCycle;
		this.totalBurningTime = totalBurningTime;
	}
}