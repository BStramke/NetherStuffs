package buildcraft.api.fuels;

import java.util.LinkedList;

import net.minecraftforge.fluids.FluidStack;

public class IronEngineCoolant {

	public static LinkedList<IronEngineCoolant> coolants = new LinkedList<IronEngineCoolant>();

	public static IronEngineCoolant getCoolantForLiquid(FluidStack liquid) {
		if (liquid == null)
			return null;
		if (liquid.fluidID <= 0)
			return null;

		for (IronEngineCoolant coolant : coolants)
			if (coolant.liquid.isFluidEqual(liquid))
				return coolant;

		return null;
	}

	public final FluidStack liquid;
	public final float coolingPerUnit;

	public IronEngineCoolant(FluidStack liquid, float coolingPerUnit) {
		this.liquid = liquid;
		this.coolingPerUnit = coolingPerUnit;
	}

}
