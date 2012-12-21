package net.minecraft.block;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockBreakable extends Block
{
	protected static List actAsSameBlock = new ArrayList();
    private boolean localFlag;

    protected BlockBreakable(int par1, int par2, Material par3Material, boolean par4)
    {
        super(par1, par2, par3Material);
        this.localFlag = par4;
    }

	/**
	 * 
	 * @param nBlockID
	 *            BlockId to Connect To
	 */
	public static void addToSameBlockList(int nBlockID) {
		if (!actAsSameBlock.contains(nBlockID))
			actAsSameBlock.add(nBlockID);
	}
	
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        int var6 = par1IBlockAccess.getBlockId(par2, par3, par4);
        return !this.localFlag && actAsSameBlock.contains(var6) /* var6 == this.blockID */? false : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }
}
