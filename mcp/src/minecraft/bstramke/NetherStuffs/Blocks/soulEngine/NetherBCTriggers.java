package bstramke.NetherStuffs.Blocks.soulEngine;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import bstramke.NetherStuffs.Common.NetherStuffsCore;
import buildcraft.api.core.IIconProvider;
import buildcraft.api.gates.ActionManager;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.gates.ITriggerParameter;
import buildcraft.api.gates.TriggerParameter;

public abstract class NetherBCTriggers implements ITrigger {

	protected int id;

	public NetherBCTriggers(int id) {
		id += 500;
		this.id = id;
		ActionManager.registerTrigger(this);
	}

	/*@Override
	public boolean hasParameter() {
		return false;
	}

	@Override
	public boolean isTriggerActive(ForgeDirection side, TileEntity tile, ITriggerParameter parameter) {
		return false;
	}*/

	@Override
	public final ITriggerParameter createParameter() {
		return new TriggerParameter();
	}

}
