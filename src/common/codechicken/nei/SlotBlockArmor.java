package codechicken.nei;

import codechicken.core.CommonUtils;
import net.minecraft.src.ContainerPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.SlotArmor;

public class SlotBlockArmor extends SlotArmor
{
	public SlotBlockArmor(ContainerPlayer container, InventoryPlayer invPlayer, int i, int j, int k, int armourslot)
	{
		super(container, invPlayer, i, j, k, armourslot);
	}
	
	@Override
	public boolean isItemValid(ItemStack par1ItemStack)
	{
		return super.isItemValid(par1ItemStack) || CommonUtils.isBlock(par1ItemStack.itemID);
	}
}
