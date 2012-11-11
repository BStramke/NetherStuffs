package NetherStuffs.DemonicFurnace;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityXPOrb;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.SlotFurnace;
import cpw.mods.fml.common.registry.GameRegistry;

public class SlotDemonicFurnace extends SlotFurnace {

	private EntityPlayer thePlayer;
	private int itemsToProcess;

	public SlotDemonicFurnace(EntityPlayer par1EntityPlayer, IInventory par2iInventory, int par3, int par4, int par5) {
		super(par1EntityPlayer, par2iInventory, par3, par4, par5);
		this.thePlayer = par1EntityPlayer;
	}

	@Override
	public ItemStack decrStackSize(int par1) {
		if (this.getHasStack()) {
			this.itemsToProcess += Math.min(par1, this.getStack().stackSize);
		}

		return super.decrStackSize(par1);
	}

	@Override
	protected void onCrafting(ItemStack par1ItemStack) {
		// par1ItemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.itemsToProcess);

		if (!this.thePlayer.worldObj.isRemote) {
			int var2 = this.itemsToProcess;
			float var3 = DemonicFurnaceRecipes.smelting().getExperience(par1ItemStack);
			int var4;

			if (var3 == 0.0F) {
				var2 = 0;
			} else if (var3 < 1.0F) {
				var4 = MathHelper.floor_float((float) var2 * var3);

				if (var4 < MathHelper.ceiling_float_int((float) var2 * var3) && (float) Math.random() < (float) var2 * var3 - (float) var4) {
					++var4;
				}

				var2 = var4;
			}

			while (var2 > 0) {
				var4 = EntityXPOrb.getXPSplit(var2);
				var2 -= var4;
				this.thePlayer.worldObj.spawnEntityInWorld(new EntityXPOrb(this.thePlayer.worldObj, this.thePlayer.posX, this.thePlayer.posY + 0.5D, this.thePlayer.posZ + 0.5D, var4));
			}
		}

		this.itemsToProcess = 0;

		GameRegistry.onItemSmelted(thePlayer, par1ItemStack);
	}

}
