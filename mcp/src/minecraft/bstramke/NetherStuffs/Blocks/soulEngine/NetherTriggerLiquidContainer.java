package bstramke.NetherStuffs.Blocks.soulEngine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import bstramke.NetherStuffs.Common.ActionTriggerIconProvider;
import buildcraft.api.gates.ITriggerParameter;

public class NetherTriggerLiquidContainer extends NetherBCTriggers {
	private Icon iconEmpty, iconSpace, iconContains, iconFull;
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
	public Icon getIcon() {
		switch (state) {
		case Empty:
			return iconEmpty;
		case Contains:
			return iconContains;
		case Space:
			return iconSpace;
		default:
			return iconFull;
		}
	}

	@Override
	public int getLegacyId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getUniqueTag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		iconEmpty = iconRegister.registerIcon("buildcraft:triggers/trigger_liquidcontainer_empty");
		iconContains = iconRegister.registerIcon("buildcraft:triggers/trigger_liquidcontainer_contains");
		iconSpace = iconRegister.registerIcon("buildcraft:triggers/trigger_liquidcontainer_space");
		iconFull = iconRegister.registerIcon("buildcraft:triggers/trigger_liquidcontainer_full");
	}
}
