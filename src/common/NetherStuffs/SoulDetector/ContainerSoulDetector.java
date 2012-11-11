package NetherStuffs.SoulDetector;

import java.util.Iterator;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICrafting;
import net.minecraftforge.common.ForgeDirection;

public class ContainerSoulDetector extends Container {
	protected TileSoulDetector tile_entity;

	public ContainerSoulDetector(TileSoulDetector tile_entity) {
		this.tile_entity = tile_entity;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile_entity.isUseableByPlayer(player);

	}
}
