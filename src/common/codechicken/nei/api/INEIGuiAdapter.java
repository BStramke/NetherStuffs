package codechicken.nei.api;

import java.util.List;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.ItemStack;
import codechicken.nei.VisiblityData;

/**
 * Lets you just override those things you want to
 */
public class INEIGuiAdapter implements INEIGuiHandler
{
	@Override
	public VisiblityData modifyVisiblity(GuiContainer gui, VisiblityData currentVisibility)
	{
		return currentVisibility;
	}

	@Override
	public int getItemSpawnSlot(GuiContainer gui, ItemStack item)
	{
		return -1;
	}

	@Override
	public List<TaggedInventoryArea> getInventoryAreas(GuiContainer gui)
	{
		return null;
	}	
}
