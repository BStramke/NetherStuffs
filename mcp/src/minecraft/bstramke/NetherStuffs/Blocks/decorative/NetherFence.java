package bstramke.NetherStuffs.Blocks.decorative;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.BlockRegistry;
import bstramke.NetherStuffs.Blocks.Plank;
import bstramke.NetherStuffs.Items.ItemRegistry;
import bstramke.NetherStuffs.NetherStuffs.IDs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
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

		if (par1 == NetherStuffs.IDs.Blocks.NetherFenceHellfireBlockId)
		{
			LanguageRegistry.instance().addStringLocalization("tile.NetherFenceHellfire.name", "Hellfirewood Fence");
			type = Plank.hellfire;
		}
		else if (par1 == NetherStuffs.IDs.Blocks.NetherFenceAcidBlockId)
		{
			LanguageRegistry.instance().addStringLocalization("tile.NetherFenceAcid.name", "Acidwood Fence");
			type = Plank.acid;
		}
		else if (par1 == NetherStuffs.IDs.Blocks.NetherFenceDeathBlockId)
		{
			LanguageRegistry.instance().addStringLocalization("tile.NetherFenceDeath.name", "Deathwood Fence");
			type = Plank.death;
		}
		else
			type = 4; //its the default fence

		if(type <= 3) //add 3 fence recipes
			NetherStuffs.addShapedRecipeFirst(new ItemStack(this, 12), new Object[] { "PPP", "PPP", 'P', new ItemStack(BlockRegistry.netherPlank, 1, type)} );
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
			return BlockRegistry.netherPlank.getIcon(0, type);
	}

	public boolean canConnectFenceTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		int l = par1IBlockAccess.getBlockId(par2, par3, par4);

		if(l == NetherStuffs.IDs.Blocks.NetherFenceGateHellfireBlockId && this.blockID == NetherStuffs.IDs.Blocks.NetherFenceHellfireBlockId)
			return true;
		
		if(l == NetherStuffs.IDs.Blocks.NetherFenceGateDeathBlockId && this.blockID == NetherStuffs.IDs.Blocks.NetherFenceDeathBlockId)
			return true;
		
		if(l == NetherStuffs.IDs.Blocks.NetherFenceGateAcidBlockId && this.blockID == NetherStuffs.IDs.Blocks.NetherFenceAcidBlockId)
			return true;
		
		if(l == NetherStuffs.IDs.Blocks.NetherFenceGateNetherBricksBlockId && this.blockID == Block.netherFence.blockID)
			return true;
		
		if (l != this.blockID && l != Block.fenceGate.blockID) {
			Block block = Block.blocksList[l];
			return block != null && block.blockMaterial.isOpaque() && block.renderAsNormalBlock() ? block.blockMaterial != Material.pumpkin : false;
		} else {
			return true;
		}
	}

	public static boolean isIdAFence(int par0) {
		return par0 == NetherStuffs.IDs.Blocks.NetherFenceHellfireBlockId || par0 == NetherStuffs.IDs.Blocks.NetherFenceDeathBlockId || par0 == NetherStuffs.IDs.Blocks.NetherFenceAcidBlockId;
	}
}
