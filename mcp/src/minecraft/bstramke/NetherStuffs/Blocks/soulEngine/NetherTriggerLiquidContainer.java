package bstramke.NetherStuffs.Blocks.soulEngine;

import java.util.Locale;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;
import buildcraft.api.gates.ITriggerParameter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherTriggerLiquidContainer extends NetherBCTriggers {
	private Icon iconEmpty, iconSpace, iconContains, iconFull;

	public enum State {
		Empty, Contains, Space, Full
	};

	public State state;

	public NetherTriggerLiquidContainer(int id, State state) {
		super(id, "netherstuffs.fluid." + state.name().toLowerCase(Locale.ENGLISH));
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
			IFluidHandler container = (IFluidHandler) tile;

			FluidStack searchedFluid = null;

			if (parameter != null && parameter.getItem() != null) {
				searchedFluid = FluidContainerRegistry.getFluidForFilledItem(parameter.getItem());
			}

			if (searchedFluid != null) {
				searchedFluid.amount = 1;
			}

			FluidTankInfo[] liquids = container.getTankInfo(ForgeDirection.UNKNOWN);

			if (liquids == null || liquids.length == 0)
				return false;

			switch (state) {
			case Empty:
				for (FluidTankInfo c : liquids) {
					if (c.fluid != null && c.fluid.amount > 0 && (searchedFluid == null || searchedFluid.isFluidEqual(c.fluid)))
						return false;
				}
				return true;
			case Contains:
				for (FluidTankInfo c : liquids) {
					if (c.fluid != null && c.fluid.amount > 0 && (searchedFluid == null || searchedFluid.isFluidEqual(c.fluid)))
						return true;
				}
				return false;
			case Space:
				if (searchedFluid == null) {
					for (FluidTankInfo c : liquids) {
						if (c.fluid == null || c.fluid.amount < c.capacity)
							return true;
					}
					return false;
				}
				return container.fill(side, searchedFluid, false) > 0;
			case Full:
				if (searchedFluid == null) {
					for (FluidTankInfo c : liquids) {
						if (c.fluid == null || c.fluid.amount < c.capacity)
							return false;
					}
					return true;
				}
				return container.fill(side, searchedFluid, false) <= 0;
			}
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
/*
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
	}*/
}
