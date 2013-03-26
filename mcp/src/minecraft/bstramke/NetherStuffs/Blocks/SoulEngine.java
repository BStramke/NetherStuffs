package bstramke.NetherStuffs.Blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.SoulEngine.TileEngine;
import buildcraft.api.tools.IToolWrench;

public class SoulEngine extends BlockContainer {

	protected SoulEngine(int par1) {
		super(par1, Material.iron);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		//TODO: custom renderer
		return 0;
		//return BuildCraftCore.blockByEntityModel;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEngine();
	}

	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile instanceof TileEngine) {
			return ForgeDirection.getOrientation(((TileEngine) tile).orientation).getOpposite() == side;
		}
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		TileEngine engine = ((TileEngine) world.getBlockTileEntity(x, y, z));

		if (engine != null) {
			engine.delete();
		}
		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {

		TileEngine tile = (TileEngine) world.getBlockTileEntity(i, j, k);

		// Drop through if the player is sneaking
		if (entityplayer.isSneaking())
			return false;

		// Switch orientation if whacked with a wrench.
		Item equipped = entityplayer.getCurrentEquippedItem() != null ? entityplayer.getCurrentEquippedItem().getItem() : null;
		if (equipped instanceof IToolWrench && ((IToolWrench) equipped).canWrench(entityplayer, i, j, k)) {

			tile.switchOrientation();
			((IToolWrench) equipped).wrenchUsed(entityplayer, i, j, k);
			return true;
		} else {
			if (!tile.worldObj.isRemote)
				entityplayer.openGui(NetherStuffs.instance, 0, world, i, j, k);
			return true;
		}
	}

	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int par5) {
		TileEngine tile = (TileEngine) world.getBlockTileEntity(x, y, z);
		tile.orientation = ForgeDirection.UP.ordinal();
		tile.switchOrientation();
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SuppressWarnings({ "all" })
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		TileEngine tile = (TileEngine) world.getBlockTileEntity(i, j, k);

		if (!tile.isBurning())
			return;

		float f = (float) i + 0.5F;
		float f1 = (float) j + 0.0F + (random.nextFloat() * 6F) / 16F;
		float f2 = (float) k + 0.5F;
		float f3 = 0.52F;
		float f4 = random.nextFloat() * 0.6F - 0.3F;

		world.spawnParticle("reddust", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("reddust", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("reddust", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("reddust", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		TileEngine tile = (TileEngine) world.getBlockTileEntity(i, j, k);

		if (tile != null) {
			tile.checkRedstonePower();
		}
	}
}
