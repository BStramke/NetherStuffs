package codechicken.core.inventory;

import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;

public class SlotDummyOutput extends SlotDummy
{
    public SlotDummyOutput(IInventory inv, int slot, int x, int y)
    {
        super(inv, slot, x, y);
    }
    
    @Override
    public void slotClick(ItemStack stack, int button, boolean shift)
    {
    }
}
