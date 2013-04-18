package bstramke.NetherStuffs.Blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import bstramke.NetherStuffs.NetherStuffs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.util.Icon;

public class NetherFenceGate extends BlockFenceGate {

	private int type;
	
	public NetherFenceGate(int par1) {
		super(par1);
		
		if(par1 == NetherStuffs.NetherFenceGateHellfireBlockId)
			type = NetherPlank.hellfire;
		else if(par1 == NetherStuffs.NetherFenceGateAcidBlockId)
			type = NetherPlank.acid;
		else if(par1 == NetherStuffs.NetherFenceGateDeathBlockId)
			type = NetherPlank.death;
		else if(par1 == NetherStuffs.NetherFenceGateNetherBricksBlockId)
			type = 4;
		else
			type = NetherPlank.hellfire;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		if(type == 4)
			return Block.netherBrick.getBlockTextureFromSide(0);
		else
			return NetherBlocks.netherPlank.getIcon(0, type);
	}
}
