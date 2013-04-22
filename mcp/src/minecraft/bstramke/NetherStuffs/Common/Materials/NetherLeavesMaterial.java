package bstramke.NetherStuffs.Common.Materials;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class NetherLeavesMaterial extends Material {

	private boolean isTranslucent = true;

	protected NetherLeavesMaterial() {
		super(MapColor.airColor);
		this.setNoPushMobility();
		this.setBurning();
		this.isTranslucent = true;
	}
}
