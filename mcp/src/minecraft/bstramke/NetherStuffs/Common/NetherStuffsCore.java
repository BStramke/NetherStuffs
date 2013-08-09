package bstramke.NetherStuffs.Common;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.soulEngine.NetherBCTriggers;
import bstramke.NetherStuffs.Blocks.soulEngine.NetherTriggerEngineHeat;
import bstramke.NetherStuffs.Blocks.soulEngine.NetherTriggerLiquidContainer;
import bstramke.NetherStuffs.Blocks.soulEngine.TileEngine;

public class NetherStuffsCore {
	public static Icon icoEnergy;
	
	public static NetherStuffsCore instance = new NetherStuffsCore();
	
	//public static IIconProvider actionTriggerIconProvider = new ActionTriggerIconProvider();
	public static NetherBCTriggers triggerBlueEngineHeat = new NetherTriggerEngineHeat(ActionTriggerIconProvider.Trigger_EngineHeat_Blue, TileEngine.EnergyStage.BLUE, "netherstuffs.engine.stage.blue");
	public static NetherBCTriggers triggerGreenEngineHeat = new NetherTriggerEngineHeat(ActionTriggerIconProvider.Trigger_EngineHeat_Green, TileEngine.EnergyStage.GREEN, "netherstuffs.engine.stage.green");
	public static NetherBCTriggers triggerYellowEngineHeat = new NetherTriggerEngineHeat(ActionTriggerIconProvider.Trigger_EngineHeat_Yellow, TileEngine.EnergyStage.YELLOW, "netherstuffs.engine.stage.yellow");
	public static NetherBCTriggers triggerRedEngineHeat = new NetherTriggerEngineHeat(ActionTriggerIconProvider.Trigger_EngineHeat_Red, TileEngine.EnergyStage.RED, "netherstuffs.engine.stage.red");
	
	public static NetherBCTriggers triggerEmptyLiquid = new NetherTriggerLiquidContainer(ActionTriggerIconProvider.Trigger_LiquidContainer_Empty, NetherTriggerLiquidContainer.State.Empty);
	public static NetherBCTriggers triggerContainsLiquid = new NetherTriggerLiquidContainer(ActionTriggerIconProvider.Trigger_LiquidContainer_Contains, NetherTriggerLiquidContainer.State.Contains);
	public static NetherBCTriggers triggerSpaceLiquid = new NetherTriggerLiquidContainer(ActionTriggerIconProvider.Trigger_LiquidContainer_Space, NetherTriggerLiquidContainer.State.Space);
	public static NetherBCTriggers triggerFullLiquid = new NetherTriggerLiquidContainer(ActionTriggerIconProvider.Trigger_LiquidContainer_Full, NetherTriggerLiquidContainer.State.Full);
	
	public void registerItemIcons(IconRegister iconRegister) {
		if(NetherStuffs.bBuildcraftAvailable)
			icoEnergy = iconRegister.registerIcon("buildcraft:icons/energy");
		
	}
}
