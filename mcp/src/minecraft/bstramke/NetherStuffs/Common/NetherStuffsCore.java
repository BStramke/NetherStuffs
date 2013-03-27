package bstramke.NetherStuffs.Common;

import bstramke.NetherStuffs.SoulEngine.NetherBCTriggers;
import bstramke.NetherStuffs.SoulEngine.NetherTriggerEngineHeat;
import bstramke.NetherStuffs.SoulEngine.NetherTriggerLiquidContainer;
import bstramke.NetherStuffs.SoulEngine.TileSoulEngine;
import buildcraft.api.core.IIconProvider;

public class NetherStuffsCore {
	public static NetherStuffsCore instance = new NetherStuffsCore();
	
	public static IIconProvider actionTriggerIconProvider = new ActionTriggerIconProvider();
	public static NetherBCTriggers triggerBlueEngineHeat = new NetherTriggerEngineHeat(ActionTriggerIconProvider.Trigger_EngineHeat_Blue, TileSoulEngine.EnergyStage.Blue);
	public static NetherBCTriggers triggerGreenEngineHeat = new NetherTriggerEngineHeat(ActionTriggerIconProvider.Trigger_EngineHeat_Green, TileSoulEngine.EnergyStage.Green);
	public static NetherBCTriggers triggerYellowEngineHeat = new NetherTriggerEngineHeat(ActionTriggerIconProvider.Trigger_EngineHeat_Yellow, TileSoulEngine.EnergyStage.Yellow);
	public static NetherBCTriggers triggerRedEngineHeat = new NetherTriggerEngineHeat(ActionTriggerIconProvider.Trigger_EngineHeat_Red, TileSoulEngine.EnergyStage.Red);
	
	public static NetherBCTriggers triggerEmptyLiquid = new NetherTriggerLiquidContainer(ActionTriggerIconProvider.Trigger_LiquidContainer_Empty, NetherTriggerLiquidContainer.State.Empty);
	public static NetherBCTriggers triggerContainsLiquid = new NetherTriggerLiquidContainer(ActionTriggerIconProvider.Trigger_LiquidContainer_Contains, NetherTriggerLiquidContainer.State.Contains);
	public static NetherBCTriggers triggerSpaceLiquid = new NetherTriggerLiquidContainer(ActionTriggerIconProvider.Trigger_LiquidContainer_Space, NetherTriggerLiquidContainer.State.Space);
	public static NetherBCTriggers triggerFullLiquid = new NetherTriggerLiquidContainer(ActionTriggerIconProvider.Trigger_LiquidContainer_Full, NetherTriggerLiquidContainer.State.Full);
}
