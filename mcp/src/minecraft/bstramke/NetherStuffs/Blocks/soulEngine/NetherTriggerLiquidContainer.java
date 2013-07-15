package bstramke.NetherStuffs.Blocks.soulEngine;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import bstramke.NetherStuffs.Common.ActionTriggerIconProvider;
import buildcraft.api.gates.ITriggerParameter;

public class NetherTriggerLiquidContainer extends NetherBCTriggers {

	public enum State {
		Empty, Contains, Space, Full
	};

	public State state;

	public NetherTriggerLiquidContainer(int id, State state) {
		super(id);
		this.state = state;
	}

	@Override
	public boolean hasParameter() {
		if (state == State.Contains || state == State.Space)
			return true;
		else
			return false;
	}

	@Override
	public String getDescription() {
		switch (state) {
		case Empty:
			return "Tank Empty";
		case Contains:
			return "Liquid in Tank";
		case Space:
			return "Space for Liquid";
		default:
			return "Tank Full";
		}
	}

	@Override
	public boolean isTriggerActive(ForgeDirection side, TileEntity tile, ITriggerParameter parameter) {
		if (tile instanceof IFluidTank) {
			IFluidTank container = (IFluidTank) tile;

			FluidStack searchedLiquid = null;

			if (parameter != null && parameter.getItem() != null) {
				searchedLiquid = FluidContainerRegistry.getFluidForFilledItem(parameter.getItem());
			}

			if (searchedLiquid != null) {
				searchedLiquid.amount = 1;
			}

			/*IFluidTank[] liquids = container.getTanks(ForgeDirection.UNKNOWN);
			
			if (liquids == null || liquids.length == 0)
				return false;

			switch (state) {
			case Empty:
				for (IFluidTank c : liquids) {
					if (searchedLiquid != null) {
						FluidStack drained = c.drain(1, false);
						if (drained != null && searchedLiquid.isFluidEqual(drained))
							return false;
					} else if (c.getFluid() != null && c.getFluid().amount > 0)
						return false;
				}

				return true;
			case Contains:
				for (IFluidTank c : liquids) {
					if (c.getFluid() != null && c.getFluid().amount != 0) {
						if (searchedLiquid == null || searchedLiquid.isFluidEqual(c.getFluid()))
							return true;
					}
				}

				return false;

			case Space:
				for (IFluidTank c : liquids) {
					if (searchedLiquid != null) {
						if (c.fill(searchedLiquid, false) > 0)
							return true;
					} else if (c.getFluid() == null || c.getFluid().amount < c.getCapacity())
						return true;
				}

				return false;
			case Full:
				for (IFluidTank c : liquids) {
					if (searchedLiquid != null) {
						if (c.fill(searchedLiquid, false) > 0)
							return false;
					} else if (c.getFluid() == null || c.getFluid().amount < c.getCapacity())
						return false;
				}

				return true;
			}*/
		}

		return false;
	}

	@Override
	public int getIconIndex() {
		switch (state) {
		case Empty:
			return ActionTriggerIconProvider.Trigger_LiquidContainer_Empty;
		case Contains:
			return ActionTriggerIconProvider.Trigger_LiquidContainer_Contains;
		case Space:
			return ActionTriggerIconProvider.Trigger_LiquidContainer_Space;
		default:
			return ActionTriggerIconProvider.Trigger_LiquidContainer_Full;
		}
	}
}
