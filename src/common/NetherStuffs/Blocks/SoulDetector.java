package NetherStuffs.Blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import NetherStuffs.NetherStuffs;
import NetherStuffs.SoulDetector.TileSoulDetector;

public class SoulDetector extends BlockContainer {

	public SoulDetector(int par1, int par2) {
		super(par1, par2, Material.circuits);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setRequiresSelfNotify();
	}

	public int tickRate() {
		return 40;
	}

	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		if (!par1World.isRemote) {
			par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate());
			setEmittingSignal(false, par1World, par2, par3, par4);
		}
	}

	public boolean isPoweringTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		return (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) > 0;
	}

	public boolean isIndirectlyPoweringTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		return (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) > 0;
	}

	/*
	 * public String getTextureFile() { return "/blocks.png"; }
	 * 
	 * public int getMetadataSize() { return SoulDetectorItemBlock.blockNames.length; }
	 */

	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		return 59;
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
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

		player.openGui(NetherStuffs.instance, 0, world, x, y, z);
		return true;
	}
	
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (!par1World.isRemote) {
			int nRadius = 5;
			List tmp = par1World.getEntitiesWithinAABBExcludingEntity(null,
					AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(par2 - nRadius, par3 - nRadius, par4 - nRadius, par2 + nRadius, par3 + nRadius, par4 + nRadius));
			//System.out.println(tmp);

			if (tmp.size() >= 1) {
				setEmittingSignal(true, par1World, par2, par3, par4);
			} else {
				setEmittingSignal(false, par1World, par2, par3, par4);
			}

			par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate());
		}
	}

	private void setEmittingSignal(boolean active, World par1World, int par2, int par3, int par4) {
		if (!par1World.isRemote) {
			if (par1World.getBlockMetadata(par2, par3, par4) > 0 && active == false) // it is active, sets to non active
				par1World.setBlockMetadataWithNotify(par2, par3, par4, 0);
			else if (par1World.getBlockMetadata(par2, par3, par4) == 0 && active == true) // it is not active, sets to active
				par1World.setBlockMetadataWithNotify(par2, par3, par4, 8);
		}
	}

	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
		setEmittingSignal(false, par1World, par2, par3, par4);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileSoulDetector();
	}
}
