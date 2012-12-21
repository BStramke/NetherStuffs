package bstramke.NetherStuffs.Common;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class NetherLeavesMaterial extends Material {

	public static final Material netherLeaves = (NetherLeavesMaterial) (new NetherLeavesMaterial());

	private boolean isTranslucent = true;

	public NetherLeavesMaterial() {
		super(MapColor.airColor);
		this.setNoPushMobility();
		this.setBurning();
		this.isTranslucent = true;
	}
}
