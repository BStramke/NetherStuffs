package bstramke.NetherStuffs.Blocks.soulDetector;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

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
