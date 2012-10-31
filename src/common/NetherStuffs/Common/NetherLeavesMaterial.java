package NetherStuffs.Common;

import net.minecraft.src.MapColor;
import net.minecraft.src.Material;

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
