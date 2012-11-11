package NetherStuffs.Common;

import net.minecraft.src.MapColor;
import net.minecraft.src.Material;

public class NetherPuddleMaterial extends Material {

	public static final Material netherPuddle = (NetherPuddleMaterial) (new NetherPuddleMaterial());

	private boolean isTranslucent = true;

	public NetherPuddleMaterial() {
		super(MapColor.airColor);
		this.setNoPushMobility();
		this.setGroundCover();
		this.setNoHarvest();
		this.isTranslucent = true;
	}

	@Override
	public boolean isOpaque() {
		return true;
	}
}
