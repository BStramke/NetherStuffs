package bstramke.NetherStuffs.Common;

import bstramke.NetherStuffs.SoulEngine.Engine.EnergyStage;
import bstramke.NetherStuffs.SoulEngine.NetherBCTriggers;
import bstramke.NetherStuffs.SoulEngine.NetherTriggerEngineHeat;
import bstramke.NetherStuffs.SoulEngine.NetherTriggerLiquidContainer;
import buildcraft.api.core.IIconProvider;
import buildcraft.core.DefaultProps;
import buildcraft.core.triggers.BCTrigger;
import buildcraft.core.triggers.TriggerLiquidContainer;

public class NetherStuffsCore {
	public static IIconProvider actionTriggerIconProvider = new ActionTriggerIconProvider();
	public static NetherBCTriggers triggerBlueEngineHeat = new NetherTriggerEngineHeat(ActionTriggerIconProvider.Trigger_EngineHeat_Blue, EnergyStage.Blue);
	public static NetherBCTriggers triggerGreenEngineHeat = new NetherTriggerEngineHeat(ActionTriggerIconProvider.Trigger_EngineHeat_Green, EnergyStage.Green);
	public static NetherBCTriggers triggerYellowEngineHeat = new NetherTriggerEngineHeat(ActionTriggerIconProvider.Trigger_EngineHeat_Yellow, EnergyStage.Yellow);
	public static NetherBCTriggers triggerRedEngineHeat = new NetherTriggerEngineHeat(ActionTriggerIconProvider.Trigger_EngineHeat_Red, EnergyStage.Red);
	
	public static NetherBCTriggers triggerEmptyLiquid = new NetherTriggerLiquidContainer(ActionTriggerIconProvider.Trigger_LiquidContainer_Empty, NetherTriggerLiquidContainer.State.Empty);
	public static NetherBCTriggers triggerContainsLiquid = new NetherTriggerLiquidContainer(ActionTriggerIconProvider.Trigger_LiquidContainer_Contains, NetherTriggerLiquidContainer.State.Contains);
	public static NetherBCTriggers triggerSpaceLiquid = new NetherTriggerLiquidContainer(ActionTriggerIconProvider.Trigger_LiquidContainer_Space, NetherTriggerLiquidContainer.State.Space);
	public static NetherBCTriggers triggerFullLiquid = new NetherTriggerLiquidContainer(ActionTriggerIconProvider.Trigger_LiquidContainer_Full, NetherTriggerLiquidContainer.State.Full);
}
