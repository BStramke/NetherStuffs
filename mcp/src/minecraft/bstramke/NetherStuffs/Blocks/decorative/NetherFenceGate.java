package bstramke.NetherStuffs.Blocks.decorative;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.BlockRegistry;
import bstramke.NetherStuffs.Blocks.Plank;
import bstramke.NetherStuffs.Items.ItemRegistry;
import bstramke.NetherStuffs.NetherStuffs.IDs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class NetherFenceGate extends BlockFenceGate {

	private int type;
	
	public NetherFenceGate(int par1) {
		super(par1);
		setHardness(2.0F);
		setResistance(10.0F);
		if(par1 == NetherStuffs.IDs.Blocks.NetherFenceGateHellfireBlockId)
			type = Plank.hellfire;
		else if(par1 == NetherStuffs.IDs.Blocks.NetherFenceGateAcidBlockId)
			type = Plank.acid;
		else if(par1 == NetherStuffs.IDs.Blocks.NetherFenceGateDeathBlockId)
			type = Plank.death;
		else if(par1 == NetherStuffs.IDs.Blocks.NetherFenceGateNetherBricksBlockId)
			type = 4;
		else
			type = Plank.hellfire;
		
		if(type <= 3) //add 3 fence gate recipes
		{
			setStepSound(Block.soundWoodFootstep);
			GameRegistry.addRecipe(new ItemStack(this, 1), new Object[] { "IPI", "IPI", 'I', new ItemStack(ItemRegistry.NetherWoodStick), 'P', new ItemStack(BlockRegistry.netherPlank, 1, type)} );
		}
		else
		{
			setStepSound(Block.soundStoneFootstep);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		if(type == 4)
			return Block.netherBrick.getBlockTextureFromSide(0);
		else
			return BlockRegistry.netherPlank.getIcon(0, type);
	}
}
