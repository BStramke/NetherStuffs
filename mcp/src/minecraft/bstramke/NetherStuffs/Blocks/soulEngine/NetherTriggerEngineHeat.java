package bstramke.NetherStuffs.Blocks.soulEngine;

import bstramke.NetherStuffs.Common.ActionTriggerIconProvider;
import buildcraft.api.gates.ITriggerParameter;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherTriggerEngineHeat extends NetherBCTriggers {

	private Icon iconBlue, iconGreen, iconYellow, iconRed;
	
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
	@SideOnly(Side.CLIENT)
	public
	Icon getIcon() {
		switch (stage) {
		case Blue:
			return iconBlue;
		case Green:
			return iconGreen;
		case Yellow:
			return iconYellow;
		default:
			return iconRed;
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
	public
	void registerIcons(IconRegister iconRegister) {
		iconBlue = iconRegister.registerIcon("buildcraft:triggers/trigger_engineheat_blue");
		iconGreen = iconRegister.registerIcon("buildcraft:triggers/trigger_engineheat_green");
		iconYellow = iconRegister.registerIcon("buildcraft:triggers/trigger_engineheat_yellow");
		iconRed = iconRegister.registerIcon("buildcraft:triggers/trigger_engineheat_red");
	}

	@Override
	public boolean hasParameter() {
		return false;
	}
}
