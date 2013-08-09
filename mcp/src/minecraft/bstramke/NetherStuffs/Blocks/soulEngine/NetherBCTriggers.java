package bstramke.NetherStuffs.Blocks.soulEngine;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import bstramke.NetherStuffs.Common.ActionTriggerIconProvider;
import buildcraft.api.gates.ActionManager;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.gates.ITriggerParameter;
import buildcraft.api.gates.TriggerParameter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public abstract class NetherBCTriggers implements ITrigger {

	protected final int legacyId;
	protected final String uniqueTag;

	public NetherBCTriggers(int legacyId, String uniqueTag) {	
		this.legacyId = legacyId;
		this.uniqueTag = uniqueTag;
		ActionManager.registerTrigger(this);
		
	}

	@Override
	public String getUniqueTag() {
		return uniqueTag;
	}

	@Override
	public int getLegacyId() {
		return this.legacyId;
	}

	public int getIconIndex() {
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon() {
		return ActionTriggerIconProvider.INSTANCE.getIcon(getIconIndex());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
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
