package codechicken.nei.api;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.World;

public interface IHighlightIdentifier
{
	public ItemStack identifyHighlight(World world, EntityPlayer player, int x, int y, int z, MovingObjectPosition mop);
}
