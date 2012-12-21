package bstramke.NetherStuffs.Common;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class NetherWoodMaterial extends Material {

	public static final Material netherWood = (NetherWoodMaterial) (new NetherWoodMaterial());

	private boolean isTranslucent = true;

	public NetherWoodMaterial() {
		super(MapColor.woodColor);
		this.setNoPushMobility();
		this.setBurning();
		this.isTranslucent = true;
		this.setRequiresTool();
	}
}
