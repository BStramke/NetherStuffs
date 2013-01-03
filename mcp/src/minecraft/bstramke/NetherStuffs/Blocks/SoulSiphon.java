package bstramke.NetherStuffs.Blocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.SoulSiphon.TileSoulSiphon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoulSiphon extends BlockContainer {

	public static final int mk1 = 0;
	public static final int mk2 = 1;
	public static final int mk3 = 2;
	public static final int mk4 = 3;

	public SoulSiphon(int par1, int par2) {
		super(par1, par2, Material.iron);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setRequiresSelfNotify();
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.BLOCKS_PNG;
	}

	public int getMetadataSize() {
		return SoulSiphonItemBlock.blockNames.length;
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		if (!par1World.isRemote) {
			par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate());
		}
	}

	@Override
	public void updateTick(World par1World, int xCoord, int yCoord, int zCoord, Random par5Random) {
		if (!par1World.isRemote) {
			TileEntity tile_entity = par1World.getBlockTileEntity(xCoord, yCoord, zCoord);
			if (tile_entity instanceof TileSoulSiphon) {
				int nRange;
				int nMeta = par1World.getBlockMetadata(xCoord, yCoord, zCoord);
				switch (nMeta) {
				case SoulSiphon.mk1:
					nRange = 4;
					break;
				case SoulSiphon.mk2:
					nRange = 8;
					break;
				case SoulSiphon.mk3:
				case SoulSiphon.mk4:
					nRange = 12;
					break;
				default:
					nRange = 4;
				}
				int nRangeUp = nRange;
				int nRangeDown = nRange;
				int nRangeNorth = nRange;
				int nRangeSouth = nRange;
				int nRangeEast = nRange;
				int nRangeWest = nRange;

				Integer nLowerX = xCoord - nRangeWest;
				Integer nLowerY = yCoord - nRangeDown;
				Integer nLowerZ = zCoord - nRangeNorth;
				Integer nUpperX = xCoord + nRangeEast + 1;
				Integer nUpperY = yCoord + nRangeUp + 1;// height has to be 1 more for upwards detection (detects Head Position)
				Integer nUpperZ = zCoord + nRangeSouth + 1;

				AxisAlignedBB axis = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(nLowerX, nLowerY, nLowerZ, nUpperX, nUpperY, nUpperZ);
				List tmp = par1World.getEntitiesWithinAABBExcludingEntity((Entity) null, axis);

				List results = new ArrayList(); // this could be a boolean, but maybe we want a count based detection, like, if 5 Pigs...
				Iterator it = tmp.iterator();

				while (it.hasNext()) {
					Object data = it.next();
					if (data instanceof EntityLiving && !(data instanceof EntityPlayerMP) && !(data instanceof EntityPlayer)) {
						((EntityLiving) data).attackEntityFrom(DamageSource.generic, 1);
					} else {
						it.remove();
					}
				}

				if (!tmp.isEmpty()) {
					int nSiphonAmount = tmp.size();
					if (nMeta == SoulSiphon.mk4)
						nSiphonAmount = (int) (nSiphonAmount * 1.25F); // MK4 gets a Bonus on Siphoned amount

					nSiphonAmount *= 10;
					// System.out.println("Entity Count in range (not counting players): " + nSiphonAmount);
					((TileSoulSiphon) tile_entity).addFuelToTank(nSiphonAmount);
				}
			}

			par1World.scheduleBlockUpdate(xCoord, yCoord, zCoord, this.blockID, this.tickRate());
		}
	}

	@Override
	public int tickRate() {
		return 40;
	};

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g, float t) {
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);

		if (tile_entity == null || player.isSneaking()) {
			return false;
		}

		player.openGui(NetherStuffs.instance, 0, world, x, y, z);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileSoulSiphon();
	}
}
