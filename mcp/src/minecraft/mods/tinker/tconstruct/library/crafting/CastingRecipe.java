package mods.tinker.tconstruct.library.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CastingRecipe
{
	public ItemStack output;
	public FluidStack castingMetal;
	public ItemStack cast;
	public boolean consumeCast;
	public int coolTime;
	
	public CastingRecipe(ItemStack replacement, FluidStack metal, ItemStack cast, boolean consume, int delay)
	{
		castingMetal = metal;
		this.cast = cast;
		output = replacement;
		consumeCast = consume;
		coolTime = delay;
	}
	
	public boolean matches(FluidStack metal, ItemStack cast)
	{
		if (castingMetal.isFluidEqual(metal) && ItemStack.areItemStacksEqual(this.cast, cast))
			return true;
		else
			return false;
	}
	
	public ItemStack getResult()
	{
		return output.copy();
	}
}