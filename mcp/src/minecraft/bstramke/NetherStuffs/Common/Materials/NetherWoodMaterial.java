package bstramke.NetherStuffs.Common.Materials;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class NetherWoodMaterial extends Material {

	private boolean isTranslucent = true;

	protected NetherWoodMaterial() {
		super(MapColor.woodColor);
		this.setNoPushMobility();
		this.setBurning();
		this.isTranslucent = true;
		this.setRequiresTool();
	}
}
