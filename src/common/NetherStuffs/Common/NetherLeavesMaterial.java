package NetherStuffs.Common;

import net.minecraft.src.MapColor;
import net.minecraft.src.Material;

public class NetherLeavesMaterial extends Material {
	
	public static final Material netherLeaves = (NetherLeavesMaterial) (new NetherLeavesMaterial());
	
	private boolean isTranslucent = true;
	public NetherLeavesMaterial() {
		super(MapColor.airColor);
		this.setNoPushMobility();
	}
   
   /**
    * Indicate if the material is opaque
    */
   @Override
   public boolean isOpaque()
   {
       return this.isTranslucent ? false : this.blocksMovement();
   }

}
