package NetherStuffs.SoulWorkBench;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.SlotCrafting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class SlotCraftingSoulWorkBench extends SlotCrafting {
	private final IInventory craftMatrix;

	/** The player that is using the GUI where this slot resides. */
	private EntityPlayer thePlayer;

	/**
	 * The number of items that have been crafted so far. Gets passed to ItemStack.onCrafting before being reset.
	 */
	private int amountCrafted;

	public SlotCraftingSoulWorkBench(EntityPlayer par1EntityPlayer, IInventory par2IInventory, IInventory par3IInventory, int par4, int par5, int par6) {
		super(par1EntityPlayer, par2IInventory, par3IInventory, par4, par5, par6);
		this.thePlayer = par1EntityPlayer;
		this.craftMatrix = par2IInventory;
	}

	@Override
	public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {

		if (((TileSoulWorkBench) this.craftMatrix).consumeFuelFromTank(par2ItemStack)) {
			GameRegistry.onItemCrafted(par1EntityPlayer, par2ItemStack, craftMatrix);
			this.onCrafting(par2ItemStack);

			for (int var3 = 0; var3 < this.craftMatrix.getSizeInventory(); ++var3) {
				ItemStack var4 = this.craftMatrix.getStackInSlot(var3);

				if (var4 != null) {
					this.craftMatrix.decrStackSize(var3, 1);

					if (var4.getItem().hasContainerItem()) {
						ItemStack var5 = var4.getItem().getContainerItemStack(var4);

						if (var5.isItemStackDamageable() && var5.getItemDamage() > var5.getMaxDamage()) {
							MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(thePlayer, var5));
							var5 = null;
						}

						if (var5 != null && (!var4.getItem().doesContainerItemLeaveCraftingGrid(var4) || !this.thePlayer.inventory.addItemStackToInventory(var5))) {
							if (this.craftMatrix.getStackInSlot(var3) == null) {
								this.craftMatrix.setInventorySlotContents(var3, var5);
							} else {
								this.thePlayer.dropPlayerItem(var5);
							}
						}
					}
				}
			}
		}
	}
}
