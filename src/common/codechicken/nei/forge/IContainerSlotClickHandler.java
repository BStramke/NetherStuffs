package codechicken.nei.forge;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.Slot;

public interface IContainerSlotClickHandler
{
	public void beforeSlotClick(GuiContainer gui, int slotIndex, int button, Slot slot, int modifier);
	public boolean handleSlotClick(GuiContainer gui, int slotIndex, int button, Slot slot, int modifier, boolean eventconsumed);
	public void afterSlotClick(GuiContainer gui, int slotIndex, int button, Slot slot, int modifier);
}
