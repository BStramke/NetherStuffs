package bstramke.NetherStuffs.Common;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import buildcraft.api.core.IIconProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ActionTriggerIconProvider implements IIconProvider {
	public static final int Trigger_EngineHeat_Blue = 0;
	public static final int Trigger_EngineHeat_Green = 1;
	public static final int Trigger_EngineHeat_Yellow = 2;
	public static final int Trigger_EngineHeat_Red = 3;

	public static final int Trigger_LiquidContainer_Empty = 4;
	public static final int Trigger_LiquidContainer_Contains = 5;
	public static final int Trigger_LiquidContainer_Space = 6;
	public static final int Trigger_LiquidContainer_Full = 7;

	private boolean registered = false;
	
	@SideOnly(Side.CLIENT)
	private static Icon[] icons = new Icon[8];

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int itemIcon) {
		return icons[itemIcon];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		if (registered)
			return;
		registered = true;

		icons[ActionTriggerIconProvider.Trigger_EngineHeat_Blue] = iconRegister.registerIcon("buildcraft:triggers/trigger_engineheat_blue");
		icons[ActionTriggerIconProvider.Trigger_EngineHeat_Green] = iconRegister.registerIcon("buildcraft:triggers/trigger_engineheat_green");
		icons[ActionTriggerIconProvider.Trigger_EngineHeat_Yellow] = iconRegister.registerIcon("buildcraft:triggers/trigger_engineheat_yellow");
		icons[ActionTriggerIconProvider.Trigger_EngineHeat_Red] = iconRegister.registerIcon("buildcraft:triggers/trigger_engineheat_red");

		icons[ActionTriggerIconProvider.Trigger_LiquidContainer_Empty] = iconRegister.registerIcon("buildcraft:triggers/trigger_liquidcontainer_empty");
		icons[ActionTriggerIconProvider.Trigger_LiquidContainer_Contains] = iconRegister.registerIcon("buildcraft:triggers/trigger_liquidcontainer_contains");
		icons[ActionTriggerIconProvider.Trigger_LiquidContainer_Space] = iconRegister.registerIcon("buildcraft:triggers/trigger_liquidcontainer_space");
		icons[ActionTriggerIconProvider.Trigger_LiquidContainer_Full] = iconRegister.registerIcon("buildcraft:triggers/trigger_liquidcontainer_full");
	}

}
