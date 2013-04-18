package bstramke.NetherStuffs.Blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import bstramke.NetherStuffs.NetherStuffs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class NetherFence extends BlockFence {
	private int type;

	public NetherFence(int par1, String par2Str, Material par3Material) {
		super(par1, par2Str, par3Material);
		setHardness(2.0F);
		setResistance(10.0F);
		setStepSound(Block.soundWoodFootstep);

		if (par1 == NetherStuffs.NetherFenceHellfireBlockId)
			type = NetherPlank.hellfire;
		else if (par1 == NetherStuffs.NetherFenceAcidBlockId)
			type = NetherPlank.acid;
		else if (par1 == NetherStuffs.NetherFenceDeathBlockId)
			type = NetherPlank.death;
		else
			type = 4; //its the default fence
	}

	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
		if (side == ForgeDirection.UP)
			return true;
		else
			return super.isBlockSolidOnSide(world, x, y, z, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		if(type == 4)
			return Block.netherBrick.getBlockTextureFromSide(par1);
		else
			return NetherBlocks.netherPlank.getIcon(0, type);
	}

	public boolean canConnectFenceTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		int l = par1IBlockAccess.getBlockId(par2, par3, par4);

		if(l == NetherStuffs.NetherFenceGateHellfireBlockId && this.blockID == NetherStuffs.NetherFenceHellfireBlockId)
			return true;
		
		if(l == NetherStuffs.NetherFenceGateDeathBlockId && this.blockID == NetherStuffs.NetherFenceDeathBlockId)
			return true;
		
		if(l == NetherStuffs.NetherFenceGateAcidBlockId && this.blockID == NetherStuffs.NetherFenceAcidBlockId)
			return true;
		
		if(l == NetherStuffs.NetherFenceGateNetherBricksBlockId && this.blockID == Block.netherFence.blockID)
			return true;
		
		if (l != this.blockID && l != Block.fenceGate.blockID) {
			Block block = Block.blocksList[l];
			return block != null && block.blockMaterial.isOpaque() && block.renderAsNormalBlock() ? block.blockMaterial != Material.pumpkin : false;
		} else {
			return true;
		}
	}

	public static boolean isIdAFence(int par0) {
		return par0 == NetherStuffs.NetherFenceHellfireBlockId || par0 == NetherStuffs.NetherFenceDeathBlockId || par0 == NetherStuffs.NetherFenceAcidBlockId;
	}
}
