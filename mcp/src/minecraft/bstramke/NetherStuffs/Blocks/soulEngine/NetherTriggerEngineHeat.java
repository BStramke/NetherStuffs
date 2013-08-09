package bstramke.NetherStuffs.Blocks.soulEngine;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.gates.ITriggerParameter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class NetherTriggerEngineHeat extends NetherBCTriggers {

	private Icon iconBlue, iconGreen, iconYellow, iconRed;
	
	public TileSoulEngine.EnergyStage stage;

	public NetherTriggerEngineHeat(int id, TileSoulEngine.EnergyStage stage, String uniqueTag) {
		super(id, uniqueTag);
		this.stage = stage;
	}

	@Override
	public String getDescription() {
		switch (stage) {
		case BLUE:
			return "Engine Blue";
		case GREEN:
			return "Engine Green";
		case YELLOW:
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
		case BLUE:
			return iconBlue;
		case GREEN:
			return iconGreen;
		case YELLOW:
			return iconYellow;
		default:
			return iconRed;
		}
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
}
