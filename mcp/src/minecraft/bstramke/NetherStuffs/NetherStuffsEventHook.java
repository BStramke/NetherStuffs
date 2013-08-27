package bstramke.NetherStuffs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.WorldEvent;
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
			}
		}
	}

	@ForgeSubscribe
	public void entityAttacked(LivingAttackEvent event) {
		EntityLivingBase attacked = (EntityLivingBase) event.entityLiving;
		DamageSource attacker = event.source;

		if (attacked instanceof EntityPlayerMP && !((EntityPlayerMP) attacked).mcServer.isPVPEnabled())
			return;

		if (attacker.getEntity() instanceof EntityLivingBase) {
			ItemStack item = ((EntityLivingBase) attacker.getEntity()).getCurrentItemOrArmor(0);
			if (EnchantmentHelper.getEnchantmentLevel(NetherStuffs.EnchantmentAcidId, item) > 0) {
				attacked.addPotionEffect(new PotionEffect(Potion.hunger.id, 20 * 60, 8));
				attacked.addPotionEffect(new PotionEffect(Potion.poison.id, 20 * 5, 8));
			}

			if (EnchantmentHelper.getEnchantmentLevel(NetherStuffs.EnchantmentDeathId, item) > 0) {
				attacked.addPotionEffect(new PotionEffect(Potion.wither.id, 20 * 5, 4));
			}

			if (EnchantmentHelper.getEnchantmentLevel(NetherStuffs.EnchantmentHellfireId, item) > 0) {
				attacked.attackEntityFrom(DamageSource.lava, 2);
				attacked.setFire(8);
			}
		}
	}

	@ForgeSubscribe 
	public void onChunkDataEvent(ChunkDataEvent.Load evt) {
		NBTTagCompound nbt = evt.getData();
		nbt = nbt.getCompoundTag("netherstuffs");
		if(nbt.getBoolean("generated") == false)
		{
			nbt.setBoolean("generated", true);
		}
	}
}
