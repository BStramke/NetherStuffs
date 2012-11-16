package NetherStuffs.Common;

import net.minecraft.src.MapColor;
import net.minecraft.src.Material;

public class NetherWoodMaterial extends Material {

	public static final Material netherWood = (NetherWoodMaterial) (new NetherWoodMaterial());

	private boolean isTranslucent = true;

	public NetherWoodMaterial() {
		super(MapColor.woodColor);
		this.setNoPushMobility();
		this.setBurning();
		this.isTranslucent = true;
		this.setNoHarvest();
	}
}
