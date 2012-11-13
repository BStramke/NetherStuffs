package NetherStuffs;

import java.util.Iterator;
import java.util.List;

import NetherStuffs.SoulDetector.TileSoulDetector;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.EntityBlaze;
import net.minecraft.src.EntityGhast;
import net.minecraft.src.EntityMagmaCube;
import net.minecraft.src.EntityPig;
import net.minecraft.src.EntityPigZombie;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.EntityWither;
import net.minecraft.src.TileEntity;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class NetherStuffsEventHook {
	@ForgeSubscribe
	public void entitySpawnInWorldEvent(EntityJoinWorldEvent event) {
		if (event.isCancelable()) {
			if (event.entity instanceof EntityGhast || event.entity instanceof EntityPigZombie || event.entity instanceof EntityBlaze || event.entity instanceof EntityMagmaCube
					|| event.entity instanceof EntitySkeleton || event.entity instanceof EntityWither) {
				Iterator var5 = event.entity.worldObj.loadedTileEntityList.iterator();
				//System.out.println(event.entity.worldObj.getChunkFromChunkCoords(event.entity.chunkCoordX, event.entity.chunkCoordZ).chunkTileEntityMap);
/*
				while (var5.hasNext()) {
					TileEntity var6 = (TileEntity) var5.next();
					if (var6 instanceof TileSoulDetector) {
						if (var6.getDistanceFrom(event.entity.posX, event.entity.posY, event.entity.posZ) < 15) {
							event.setCanceled(true);
							return;
						}
					}
				}*/
			}
		}
	}
}
