package bstramke.NetherStuffs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.liquids.LiquidDictionary;
import bstramke.NetherStuffs.Blocks.BlockRegistry;
import bstramke.NetherStuffs.Blocks.soulBlocker.TileSoulBlocker;
import bstramke.NetherStuffs.Common.PlayerDummy;

public class NetherStuffsEventHook {
	public static int nDetectRadius; // will be set from config
	public static List lAllowedSpawnNetherBlockIds = new ArrayList();
	public static boolean SpawnSkeletonsOnlyOnNaturalNetherBlocks;
	public static List lBlockSpawnListForced = new ArrayList();
	
	private static Map<Integer, PlayerDummy> dummyPlayers = new HashMap<Integer, PlayerDummy>();

	@ForgeSubscribe
	public void worldLoadEvent(WorldEvent.Load event) {
		Integer tmp = event.world.provider.dimensionId;
		dummyPlayers.put(tmp, new PlayerDummy(event.world));
	}
	
	public static PlayerDummy getPlayerDummyForDimension(int nDimensionID) {
		return dummyPlayers.get(new Integer(nDimensionID));
	}
	
	@ForgeSubscribe
	public void entitySpawnInWorldEvent(LivingSpawnEvent event) {
		if (event.isCancelable() && event.world.provider.isHellWorld && !event.world.isRemote) {
			if (event.entity instanceof EntityGhast || event.entity instanceof EntityPigZombie || event.entity instanceof EntityBlaze || event.entity instanceof EntityMagmaCube
					|| event.entity instanceof EntitySkeleton || event.entity instanceof EntityWither) {
				int var1 = MathHelper.floor_double(event.entity.posX);
				int var2 = MathHelper.floor_double(event.entity.boundingBox.minY);
				int var3 = MathHelper.floor_double(event.entity.posZ);
				int nBlockID = event.entity.worldObj.getBlockId(var1, var2 - 1, var3);
				if (SpawnSkeletonsOnlyOnNaturalNetherBlocks && !lAllowedSpawnNetherBlockIds.contains(nBlockID)) {
					// System.out.println("prevented Spawning of " + event.entity + " on " + nBlockID);
					event.setCanceled(true);
					return;
				}

				if (lBlockSpawnListForced.contains(nBlockID)) {
					// System.out.println("prevented Spawning of " + event.entity + " on " + nBlockID);
					event.setCanceled(true);
					return;
				}

				List tmp = ((WorldServer) event.world).getAllTileEntityInBox((int) Math.round(event.entity.posX) - nDetectRadius, (int) Math.round(event.entity.posY) - nDetectRadius,
						(int) Math.round(event.entity.posZ) - nDetectRadius, (int) Math.round(event.entity.posX) + nDetectRadius, (int) Math.round(event.entity.posY) + nDetectRadius,
						(int) Math.round(event.entity.posZ) + nDetectRadius);

				Iterator entries = tmp.iterator();
				while (entries.hasNext()) {
					Object entity = entries.next();
					if (entity instanceof TileSoulBlocker) {
						// System.out.println("prevented Spawning of " + event.entity);
						event.setCanceled(true);
						return;
					}
				}
			}
		}
	}
	
	@ForgeSubscribe
	@SideOnly(Side.CLIENT)
	public void textureHook(TextureStitchEvent.Post event) {
		LiquidDictionary.getCanonicalLiquid(NetherStuffs.SoulEnergyLiquid).setTextureSheet("/terrain.png").setRenderingIcon(BlockRegistry.LiquidStill.getIcon(0,0));
		LiquidDictionary.getCanonicalLiquid(NetherStuffs.DemonicIngotLiquid).setTextureSheet("/terrain.png").setRenderingIcon(BlockRegistry.LiquidStill.getIcon(0,1));
	}
	
/*
	@ForgeSubscribe
	public void entitySpawnInWorldEvent(EntityJoinWorldEvent event) {
		if (nDetectRadius == 0)
			return;
		if (event.isCancelable() && event.world.provider.isHellWorld && !event.world.isRemote) {
			if (event.entity instanceof EntityGhast || event.entity instanceof EntityPigZombie || event.entity instanceof EntityBlaze || event.entity instanceof EntityMagmaCube
					|| event.entity instanceof EntitySkeleton || event.entity instanceof EntityWither) {

				int var1 = MathHelper.floor_double(event.entity.posX);
				int var2 = MathHelper.floor_double(event.entity.boundingBox.minY);
				int var3 = MathHelper.floor_double(event.entity.posZ);
				int nBlockID = event.entity.worldObj.getBlockId(var1, var2 - 1, var3);
				if (SpawnSkeletonsOnlyOnNaturalNetherBlocks && !lAllowedSpawnNetherBlockIds.contains(nBlockID)) {
					// System.out.println("prevented Spawning of " + event.entity + " on " + nBlockID);
					event.setCanceled(true);
					return;
				}

				if (lBlockSpawnListForced.contains(nBlockID)) {
					// System.out.println("prevented Spawning of " + event.entity + " on " + nBlockID);
					event.setCanceled(true);
					return;
				}

				List tmp = ((WorldServer) event.world).getAllTileEntityInBox((int) Math.round(event.entity.posX) - nDetectRadius, (int) Math.round(event.entity.posY) - nDetectRadius,
						(int) Math.round(event.entity.posZ) - nDetectRadius, (int) Math.round(event.entity.posX) + nDetectRadius, (int) Math.round(event.entity.posY) + nDetectRadius,
						(int) Math.round(event.entity.posZ) + nDetectRadius);

				Iterator entries = tmp.iterator();
				while (entries.hasNext()) {
					Object entity = entries.next();
					if (entity instanceof TileSoulBlocker) {
						// System.out.println("prevented Spawning of " + event.entity);
						event.setCanceled(true);
						return;
					}
				}
			}
		}
	}
	*/
}
