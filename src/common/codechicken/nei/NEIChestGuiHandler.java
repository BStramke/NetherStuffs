package codechicken.nei;

import java.util.Arrays;
import java.util.List;

import net.minecraft.src.ContainerChest;
import net.minecraft.src.GuiChest;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.ItemStack;
import codechicken.nei.api.INEIGuiAdapter;
import codechicken.nei.api.TaggedInventoryArea;

public class NEIChestGuiHandler extends INEIGuiAdapter
{
	@Override
	public int getItemSpawnSlot(GuiContainer gui, ItemStack item)
	{
		if(!(gui instanceof GuiChest))
			return -1;
		
		return NEIServerUtils.getSlotForStack(gui.inventorySlots, 0, ((ContainerChest)gui.inventorySlots).numRows*9, item);
	}

	@Override
	public List<TaggedInventoryArea> getInventoryAreas(GuiContainer gui)
	{
		if(!(gui instanceof GuiChest))
			return null;
		
		return Arrays.asList(new TaggedInventoryArea("Chest", 0, ((ContainerChest)gui.inventorySlots).numRows*9, gui.inventorySlots));
	}
}
