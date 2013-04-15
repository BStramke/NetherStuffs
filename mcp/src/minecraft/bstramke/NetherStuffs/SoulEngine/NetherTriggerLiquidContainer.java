package bstramke.NetherStuffs.SoulEngine;

import bstramke.NetherStuffs.Common.ActionTriggerIconProvider;
import buildcraft.api.gates.ITriggerParameter;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
		if (tile instanceof ITankContainer) {
			ITankContainer container = (ITankContainer) tile;

			LiquidStack searchedLiquid = null;

			if (parameter != null && parameter.getItem() != null) {
				searchedLiquid = LiquidContainerRegistry.getLiquidForFilledItem(parameter.getItem());
			}

			if (searchedLiquid != null) {
				searchedLiquid.amount = 1;
			}

			ILiquidTank[] liquids = container.getTanks(ForgeDirection.UNKNOWN);

			if (liquids == null || liquids.length == 0)
				return false;

			switch (state) {
			case Empty:
				for (ILiquidTank c : liquids) {
					if (searchedLiquid != null) {
						LiquidStack drained = c.drain(1, false);
						if (drained != null && searchedLiquid.isLiquidEqual(drained))
							return false;
					} else if (c.getLiquid() != null && c.getLiquid().amount > 0)
						return false;
				}

				return true;
			case Contains:
				for (ILiquidTank c : liquids) {
					if (c.getLiquid() != null && c.getLiquid().amount != 0) {
						if (searchedLiquid == null || searchedLiquid.isLiquidEqual(c.getLiquid()))
							return true;
					}
				}

				return false;

			case Space:
				for (ILiquidTank c : liquids) {
					if (searchedLiquid != null) {
						if (c.fill(searchedLiquid, false) > 0)
							return true;
					} else if (c.getLiquid() == null || c.getLiquid().amount < c.getCapacity())
						return true;
				}

				return false;
			case Full:
				for (ILiquidTank c : liquids) {
					if (searchedLiquid != null) {
						if (c.fill(searchedLiquid, false) > 0)
							return false;
					} else if (c.getLiquid() == null || c.getLiquid().amount < c.getCapacity())
						return false;
				}

				return true;
			}
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
