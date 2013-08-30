package bstramke.NetherStuffs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import net.minecraft.world.World;
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

		int demonic = 1;
		int netherCoal = 2;
		int iron = 4;
		int gold = 8;
		int diamond = 16;
		int emerald = 32;
		int redstone = 64;
		int obsidian = 128;
		int lapis = 256;
		int cobblestone = 512;
		int copper = 1024;
		int tin = 2048;
		int silver = 4096;
		int lead = 8192;
		int certus = 16384;
		int ferrous = 32768;
		int apatite = 65536;
		int uranium = 131072;

		int generation = nbt.getInteger("generation");
		int chunkX = evt.getChunk().xPosition;
		int chunkZ = evt.getChunk().zPosition;
		World world = evt.getChunk().worldObj;
		Random random = evt.getChunk().worldObj.rand;

		if ((generation & demonic) == 0 && NetherStuffs.bRegenerateDemonic) {
			NetherStuffs.WorldGen.demonicOre.generate(random, chunkX, chunkZ, world);
			generation |= demonic;
		}

		if ((generation & netherCoal) == 0 && NetherStuffs.bRegenerateCoal) {
			NetherStuffs.WorldGen.coalOre.generate(random, chunkX, chunkZ, world);
			generation |= netherCoal;
		}

		if ((generation & iron) == 0 && NetherStuffs.bRegenerateIron) {
			NetherStuffs.WorldGen.ironOre.generate(random, chunkX, chunkZ, world);
			generation |= iron;
		}

		if ((generation & gold) == 0 && NetherStuffs.bRegenerateGold) {
			NetherStuffs.WorldGen.goldOre.generate(random, chunkX, chunkZ, world);
			generation |= gold;
		}

		if ((generation & diamond) == 0 && NetherStuffs.bRegenerateDiamond) {
			NetherStuffs.WorldGen.diamondOre.generate(random, chunkX, chunkZ, world);
			generation |= diamond;
		}

		if ((generation & emerald) == 0 && NetherStuffs.bRegenerateEmerald) {
			NetherStuffs.WorldGen.emeraldOre.generate(random, chunkX, chunkZ, world);
			generation |= emerald;
		}

		if ((generation & redstone) == 0 && NetherStuffs.bRegenerateRedstone) {
			NetherStuffs.WorldGen.redstoneOre.generate(random, chunkX, chunkZ, world);
			generation |= redstone;
		}

		if ((generation & obsidian) == 0 && NetherStuffs.bRegenerateObsidian) {
			NetherStuffs.WorldGen.obsidianOre.generate(random, chunkX, chunkZ, world);
			generation |= obsidian;
		}

		if ((generation & lapis) == 0 && NetherStuffs.bRegenerateLapis) {
			NetherStuffs.WorldGen.lapisOre.generate(random, chunkX, chunkZ, world);
			generation |= lapis;
		}

		if ((generation & cobblestone) == 0 && NetherStuffs.bRegenerateCobblestone) {
			NetherStuffs.WorldGen.cobblestoneOre.generate(random, chunkX, chunkZ, world);
			generation |= cobblestone;
		}

		if ((generation & copper) == 0 && NetherStuffs.bRegenerateCopper) {
			NetherStuffs.WorldGen.copperOre.generate(random, chunkX, chunkZ, world);
			generation |= copper;
		}

		if ((generation & tin) == 0 && NetherStuffs.bRegenerateTin) {
			NetherStuffs.WorldGen.tinOre.generate(random, chunkX, chunkZ, world);
			generation |= tin;
		}

		if ((generation & silver) == 0 && NetherStuffs.bRegenerateSilver) {
			NetherStuffs.WorldGen.silverOre.generate(random, chunkX, chunkZ, world);
			generation |= silver;
		}

		if ((generation & lead) == 0 && NetherStuffs.bRegenerateLead) {
			NetherStuffs.WorldGen.leadOre.generate(random, chunkX, chunkZ, world);
			generation |= lead;
		}

		if ((generation & certus) == 0 && NetherStuffs.bRegenerateCertusQuartz) {
			NetherStuffs.WorldGen.certusQuartzOre.generate(random, chunkX, chunkZ, world);
			generation |= certus;
		}

		if ((generation & ferrous) == 0 && NetherStuffs.bRegenerateFerrous) {
			NetherStuffs.WorldGen.nickelOre.generate(random, chunkX, chunkZ, world);
			generation |= ferrous;
		}

		if ((generation & apatite) == 0 && NetherStuffs.bRegenerateApatite) {
			NetherStuffs.WorldGen.apatiteOre.generate(random, chunkX, chunkZ, world);
			generation |= apatite;
		}

		if ((generation & uranium) == 0 && NetherStuffs.bRegenerateUranium) {
			NetherStuffs.WorldGen.uraniumOre.generate(random, chunkX, chunkZ, world);
			generation |= uranium;
		}

		nbt.setInteger("generation", generation);
	}
}
