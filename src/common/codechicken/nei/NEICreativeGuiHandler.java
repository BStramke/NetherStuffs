package codechicken.nei;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.GuiContainerCreative;
import codechicken.nei.api.INEIGuiAdapter;

public class NEICreativeGuiHandler extends INEIGuiAdapter
{
	@Override
	public VisiblityData modifyVisiblity(GuiContainer gui, VisiblityData currentVisibility)
	{
		if(!(gui instanceof GuiContainerCreative))
			return currentVisibility;
		
		if(GuiContainerCreative.selectedTabIndex != CreativeTabs.tabInventory.getTabIndex())
		{
			currentVisibility.showItemSection = currentVisibility.enableDeleteMode = false;
		}
		return currentVisibility;
	}

}
