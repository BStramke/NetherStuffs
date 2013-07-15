package bstramke.NetherStuffs.Blocks.soulEngine;

import java.util.LinkedList;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class SoulEngineFuel {

	public static LinkedList<SoulEngineFuel> fuels = new LinkedList<SoulEngineFuel>();

	public static SoulEngineFuel getFuelForLiquid(FluidStack liquid) {
		if (liquid == null)
			return null;
		if (liquid.fluidID <= 0)
			return null;
		

		for (SoulEngineFuel fuel : fuels)
			if (fuel.liquid.isFluidEqual(liquid))
				return fuel;

		return null;
	}

	public final FluidStack liquid;
	public final float powerPerCycle;
	public final int totalBurningTime;

	public SoulEngineFuel(int liquidId, float powerPerCycle, int totalBurningTime) {
		this(new FluidStack(liquidId, FluidContainerRegistry.BUCKET_VOLUME), powerPerCycle, totalBurningTime);
	}

	public SoulEngineFuel(FluidStack liquid, float powerPerCycle, int totalBurningTime) {
		this.liquid = liquid;
		this.powerPerCycle = powerPerCycle;
		this.totalBurningTime = totalBurningTime;
	}
}