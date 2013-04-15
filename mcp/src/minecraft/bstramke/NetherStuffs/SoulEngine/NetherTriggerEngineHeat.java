package bstramke.NetherStuffs.SoulEngine;

import bstramke.NetherStuffs.Common.ActionTriggerIconProvider;
import buildcraft.api.gates.ITriggerParameter;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherTriggerEngineHeat extends NetherBCTriggers {

	public TileSoulEngine.EnergyStage stage;

	public NetherTriggerEngineHeat(int id, TileSoulEngine.EnergyStage stage) {
		super(id);

		this.stage = stage;
	}

	@Override
	public String getDescription() {
		switch (stage) {
		case Blue:
			return "Engine Blue";
		case Green:
			return "Engine Green";
		case Yellow:
			return "Engine Yellow";
		default:
			return "Engine Red";
		}
	}

	@Override
	public boolean isTriggerActive(ForgeDirection side, TileEntity tile, ITriggerParameter parameter) {
		if (tile instanceof TileSoulEngine) {
			return tile != null && ((TileSoulEngine)tile).getEnergyStage() == stage;
		}
		return false;
	}

	@Override
	public int getIconIndex() {
		switch (stage) {
		case Blue:
			return ActionTriggerIconProvider.Trigger_EngineHeat_Blue;
		case Green:
			return ActionTriggerIconProvider.Trigger_EngineHeat_Green;
		case Yellow:
			return ActionTriggerIconProvider.Trigger_EngineHeat_Yellow;
		default:
			return ActionTriggerIconProvider.Trigger_EngineHeat_Red;
		}
	}

}
