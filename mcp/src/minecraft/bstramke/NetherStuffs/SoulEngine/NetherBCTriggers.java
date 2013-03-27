package bstramke.NetherStuffs.SoulEngine;

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
		id+=500;
		this.id = id;
		ActionManager.triggers[id] = this;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
    @SideOnly(Side.CLIENT)
	public abstract Icon getTextureIcon();
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIconProvider getIconProvider() {
    	return NetherStuffsCore.instance.actionTriggerIconProvider;
    }

	@Override
	public boolean hasParameter() {
		return false;
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public boolean isTriggerActive(ForgeDirection side, TileEntity tile, ITriggerParameter parameter) {
		return false;
	}

	@Override
	public final ITriggerParameter createParameter() {
		return new TriggerParameter();
	}

}
