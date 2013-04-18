package bstramke.NetherStuffs.Blocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.BlockNotifyType;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.SoulDetector.TileSoulDetector;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoulDetector extends BlockContainer {

	public static final int mk1 = 0;
	public static final int mk2 = 1;
	public static final int mk3 = 2;
	public static final int mk4 = 3;

	private Icon[] icoInactive;
	private Icon[] icoActive;

	public SoulDetector(int par1) {
		super(par1, Material.circuits);
		this.setCreativeTab(NetherStuffs.tabNetherStuffs);
	}

	@Override
	public int tickRate(World par1World) {
		return 20;
	}

	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		if (!par1World.isRemote) {
			par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
			setEmittingSignal(false, par1World, par2, par3, par4);
		}
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		return (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) > 0 ? 1 : 0;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		return (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) > 0 ? 1 : 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		icoActive = new Icon[6];
		icoInactive = new Icon[6];

		for (int i = 0; i < 6; i++) {
			String side = "";
			switch (i) {
			case NetherBlocks.sideBottom:
				side = "Bottom";
				break;
			case NetherBlocks.sideTop:
				side = "Top";
				break;
			case NetherBlocks.sideNorth:
				side = "North";
				break;
			case NetherBlocks.sideSouth:
				side = "South";
				break;
			case NetherBlocks.sideWest:
				side = "West";
				break;
			case NetherBlocks.sideEast:
				side = "East";
				break;
			}

			icoActive[i] = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulDetectorActive_" + side));
			icoInactive[i] = par1IconRegister.registerIcon(CommonProxy.getIconLocation("SoulDetectorInactive_" + side));
		}
	}

	@Override
	public Icon getIcon(int side, int meta) {
		if ((meta & 8) > 0)
			return icoActive[side];
		else
			return icoInactive[side];
	}

	public int getMetadataSize() {
		return SoulDetectorItemBlock.blockNames.length;
	}

	@Override
	public int damageDropped(int meta) {
		return meta & 7;
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g, float t) {
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);

		if (tile_entity == null || player.isSneaking()) {
			return false;
		}

		FMLNetworkHandler.openGui(player, NetherStuffs.instance, 0, world, x, y, z);

		if (!world.isRemote) {
			((TileSoulDetector) tile_entity).sendToClient(player);
			((TileSoulDetector) tile_entity).sendDetectToClient(player);
		}
		return true;
	}

	@Override
	public void updateTick(World par1World, int xCoord, int yCoord, int zCoord, Random par5Random) {
		if (!par1World.isRemote) {
			TileEntity tile_entity = par1World.getBlockTileEntity(xCoord, yCoord, zCoord);
			if (tile_entity instanceof TileSoulDetector) {

				int nRangeUp = ((TileSoulDetector) tile_entity).getRange(ForgeDirection.UP);
				int nRangeDown = ((TileSoulDetector) tile_entity).getRange(ForgeDirection.DOWN);
				int nRangeNorth = ((TileSoulDetector) tile_entity).getRange(ForgeDirection.NORTH);
				int nRangeSouth = ((TileSoulDetector) tile_entity).getRange(ForgeDirection.SOUTH);
				int nRangeEast = ((TileSoulDetector) tile_entity).getRange(ForgeDirection.EAST);
				int nRangeWest = ((TileSoulDetector) tile_entity).getRange(ForgeDirection.WEST);

				Integer nLowerX = xCoord - nRangeWest;
				Integer nLowerY = yCoord - nRangeDown;
				Integer nLowerZ = zCoord - nRangeNorth;
				Integer nUpperX = xCoord + nRangeEast + 1;
				Integer nUpperY = yCoord + nRangeUp + 1;// height has to be 1 more for upwards detection (detects Head Position)
				Integer nUpperZ = zCoord + nRangeSouth + 1;

				AxisAlignedBB axis = AxisAlignedBB.getAABBPool().getAABB(nLowerX, nLowerY, nLowerZ, nUpperX, nUpperY, nUpperZ);
				List tmp = par1World.getEntitiesWithinAABBExcludingEntity((Entity) null, axis);

				List results = new ArrayList(); // this could be a boolean, but maybe we want a count based detection, like, if 5 Pigs...
				Iterator it = tmp.iterator();

				while (it.hasNext()) {
					Object data = it.next();
					if (data instanceof EntityLiving || data instanceof EntityPlayerMP) {} else {
						it.remove();
					}
				}

				if (((TileSoulDetector) tile_entity).detectEntities[TileSoulDetector.nDetectEverything]) {
					results.addAll(tmp);
				}

				if (((TileSoulDetector) tile_entity).detectEntities[TileSoulDetector.nDetectHostile]) {
					it = tmp.iterator();
					while (it.hasNext() && results.size() == 0) {
						Object data = it.next();
						if (data instanceof EntityMob || data instanceof EntityGhast || data instanceof EntitySlime || data instanceof EntityWolf || data instanceof EntityDragon) {
							if (data instanceof EntityWolf && ((EntityWolf) data).isTamed() == true) {} else
								results.add(data);
						}
					}
				}

				if (((TileSoulDetector) tile_entity).detectEntities[TileSoulDetector.nDetectNonHostile]) {
					it = tmp.iterator();
					while (it.hasNext() && results.size() == 0) {
						Object data = it.next();
						if (data instanceof EntityCow || data instanceof EntityBat || data instanceof EntityVillager || data instanceof EntityOcelot) {
							if (data instanceof EntityWolf && ((EntityWolf) data).isTamed() == false) {} else
								results.add(data);
						}
					}
				}

				if (results.size() == 0) {
					it = tmp.iterator();
					while (it.hasNext() && results.size() == 0) {
						Object data = it.next();
						if (data instanceof EntityPig && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectPig])
							results.add(data);
						else if (data instanceof EntitySheep && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectSheep])
							results.add(data);
						else if (data instanceof EntityCow && !(data instanceof EntityMooshroom) && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectCow])
							results.add(data);
						else if (data instanceof EntityChicken && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectChicken])
							results.add(data);
						else if (data instanceof EntitySquid && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectSquid])
							results.add(data);
						else if (data instanceof EntityMooshroom && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectMooshroom])
							results.add(data);
						else if (data instanceof EntityVillager && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectVillager])
							results.add(data);
						else if (data instanceof EntityOcelot && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectOcelot])
							results.add(data);
						else if (data instanceof EntityBat && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectBat])
							results.add(data);
						else if (data instanceof EntityWolf && ((EntityWolf) data).isAngry() == false
								&& ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectWolfTameable])
							results.add(data);
						else if (data instanceof EntityIronGolem && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectIronGolem])
							results.add(data);
						else if (data instanceof EntitySnowman && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectSnowGolem])
							results.add(data);
						else if (data instanceof EntityCreeper && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectCreeper])
							results.add(data);
						else if (data instanceof EntityZombie && !(data instanceof EntityPigZombie) && ((EntityZombie) data).isVillager() == false
								&& ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectZombie])
							results.add(data);
						else if (data instanceof EntitySpider && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectSpider]) // this also detects cave spiders
							results.add(data);
						else if (data instanceof EntityWither && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectWither])
							results.add(data);
						else if (data instanceof EntitySkeleton && ((EntitySkeleton) data).getSkeletonType() != 1
								&& ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectSkeleton])
							results.add(data);
						else if (data instanceof EntityWolf && ((EntityWolf) data).isAngry() == true
								&& ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectWolfAggressive])
							results.add(data);
						else if (data instanceof EntitySilverfish && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectSilverfish])
							results.add(data);
						else if (data instanceof EntityEnderman && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectEnderman])
							results.add(data);
						else if (data instanceof EntitySlime && !(data instanceof EntityMagmaCube) && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectSlime])
							results.add(data);
						else if (data instanceof EntityGhast && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectGhast])
							results.add(data);
						else if (data instanceof EntityPigZombie && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectPigZombie])
							results.add(data);
						else if (data instanceof EntitySkeleton && ((EntitySkeleton) data).getSkeletonType() == 1
								&& ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectWitherSkeleton])
							results.add(data);
						else if (data instanceof EntityMagmaCube && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectMagmaCube])
							results.add(data);
						else if (((Entity) data).ridingEntity instanceof EntitySpider && ((Entity) data).riddenByEntity instanceof EntitySkeleton
								&& ((EntitySkeleton) ((Entity) data).riddenByEntity).getSkeletonType() == 1
								&& ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectWitherSkeletonJockey])
							results.add(data);
						else if (data instanceof EntityBlaze && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectBlaze])
							results.add(data);
						else if (data instanceof EntityWitch && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectWitch])
							results.add(data);
						else if (data instanceof EntityZombie && ((EntityZombie) data).isVillager() == true
								&& ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectZombieVillager])
							results.add(data);
						else if (((Entity) data).ridingEntity instanceof EntitySpider && ((Entity) data).riddenByEntity instanceof EntitySkeleton
								&& ((EntitySkeleton) ((Entity) data).riddenByEntity).getSkeletonType() != 1
								&& ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectWitherSkeletonJockey])
							results.add(data);
						else if (data instanceof EntityDragon && ((TileSoulDetector) tile_entity).detectEntitiesMobs[TileSoulDetector.nDetectEnderDragon])
							results.add(data);
					}
				}

				// System.out.println(results);

				if (results.size() >= 1) {
					setEmittingSignal(true, par1World, xCoord, yCoord, zCoord);
				} else {
					setEmittingSignal(false, par1World, xCoord, yCoord, zCoord);
				}
			}

			par1World.scheduleBlockUpdate(xCoord, yCoord, zCoord, this.blockID, this.tickRate(par1World));
		}
	}

	private void setEmittingSignal(boolean active, World par1World, int par2, int par3, int par4) {
		if (!par1World.isRemote) {
			int nMeta = par1World.getBlockMetadata(par2, par3, par4);
			if ((nMeta & 8) > 0 && active == false) // it is active, sets to non active
				par1World.setBlockMetadataWithNotify(par2, par3, par4, (nMeta & 7), BlockNotifyType.ALL);
			else if ((nMeta & 8) == 0 && active == true) // it is not active, sets to active
				par1World.setBlockMetadataWithNotify(par2, par3, par4, (nMeta | 8), BlockNotifyType.ALL);
		}
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
		setEmittingSignal(false, par1World, par2, par3, par4);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileSoulDetector();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}
