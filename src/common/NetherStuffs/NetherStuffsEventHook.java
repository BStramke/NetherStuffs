package NetherStuffs;

import java.util.Iterator;
import java.util.List;

import net.minecraft.src.EntityBlaze;
import net.minecraft.src.EntityGhast;
import net.minecraft.src.EntityMagmaCube;
import net.minecraft.src.EntityPig;
import net.minecraft.src.EntityPigZombie;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.EntityWither;
import net.minecraft.src.TileEntity;
import net.minecraft.src.WorldServer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import NetherStuffs.SoulBlocker.TileSoulBlocker;
import NetherStuffs.SoulDetector.TileSoulDetector;

public class NetherStuffsEventHook {
	static int nDetectRadius = 8;

	@ForgeSubscribe
	public void entitySpawnInWorldEvent(EntityJoinWorldEvent event) {
		if (event.isCancelable() && event.world.provider.isHellWorld && !event.world.isRemote) {
			if (event.entity instanceof EntityGhast || event.entity instanceof EntityPigZombie || event.entity instanceof EntityBlaze || event.entity instanceof EntityMagmaCube
					|| event.entity instanceof EntitySkeleton || event.entity instanceof EntityWither) {

				List tmp = ((WorldServer) event.world).getAllTileEntityInBox((int) Math.round(event.entity.posX) - nDetectRadius, (int) Math.round(event.entity.posY) - nDetectRadius,
						(int) Math.round(event.entity.posZ) - nDetectRadius, (int) Math.round(event.entity.posX) + nDetectRadius, (int) Math.round(event.entity.posY) + nDetectRadius,
						(int) Math.round(event.entity.posZ) + nDetectRadius);

				Iterator entries = tmp.iterator();
				while (entries.hasNext()) {
					Object entity = entries.next();
					if (entity instanceof TileSoulBlocker) {
						//System.out.println("prevented Spawning of " + event.entity);
						event.setCanceled(true);
						return;
					}
				}
			}
		}
	}
}
