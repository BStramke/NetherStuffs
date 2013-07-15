/** 
 * Copyright (c) SpaceToad, 2011
 * http://www.mod-buildcraft.com
 * 
 * BuildCraft is distributed under the terms of the Minecraft Mod Public 
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */

package buildcraft.api.fuels;

import java.util.LinkedList;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class IronEngineFuel {

	public static LinkedList<IronEngineFuel> fuels = new LinkedList<IronEngineFuel>();

	public static IronEngineFuel getFuelForLiquid(FluidStack liquid) {
		if (liquid == null)
			return null;
		if (liquid.fluidID <= 0)
			return null;

		for (IronEngineFuel fuel : fuels)
			if (fuel.liquid.isFluidEqual(liquid))
				return fuel;

		return null;
	}

	public final FluidStack liquid;
	public final float powerPerCycle;
	public final int totalBurningTime;

	public IronEngineFuel(int liquidId, float powerPerCycle, int totalBurningTime) {
		this(new FluidStack(liquidId, FluidContainerRegistry.BUCKET_VOLUME), powerPerCycle, totalBurningTime);
	}

	public IronEngineFuel(FluidStack liquid, float powerPerCycle, int totalBurningTime) {
		this.liquid = liquid;
		this.powerPerCycle = powerPerCycle;
		this.totalBurningTime = totalBurningTime;
	}
}
