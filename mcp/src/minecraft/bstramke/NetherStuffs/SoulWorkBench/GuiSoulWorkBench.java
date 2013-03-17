package bstramke.NetherStuffs.SoulWorkBench;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Items.NetherItems;

public class GuiSoulWorkBench extends GuiContainer {

	private int ySize = 178;
	private TileSoulWorkBench benchInventory;

	public GuiSoulWorkBench(InventoryPlayer player_inventory, TileSoulWorkBench tile_entity) {
		super(new ContainerSoulWorkBench(tile_entity, player_inventory));
		this.benchInventory = tile_entity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString("Soul Workbench", 38, 0, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2 - 4, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.func_98187_b(CommonProxy.SOULWORKBENCH_PNG);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
		int var7;

		var7 = this.benchInventory.getProgressScaled(24);
		this.drawTexturedModalRect(var5 + 106, var6 + 34, this.ySize, 0, var7, 16);

		// draw fill state from bottom to top
		int nBottomLeftY = var6 + 69;
		/*this.mc.renderEngine.bindTexture(mc.renderEngine.getTexture(NetherItems.SoulEnergyLiquidItem.getTextureFile()));
		int nFillState = this.benchInventory.getFillingScaled(32);
		int y = 0;
		for (int x = 16; x <= 32 && x <= nFillState; x+=16) {
			this.drawTexturedModalRect(var5 + 12, nBottomLeftY-x, (NetherItems.SoulEnergyLiquidItem.getIconFromDamage(0)%16)*16, (NetherItems.SoulEnergyLiquidItem.getIconFromDamage(0)/16)*16, 16, 16);
			y = x;
		}
		
		if(nFillState%16!=0){
			nFillState -= y;
			this.drawTexturedModalRect(var5 + 12, nBottomLeftY-y-nFillState, (NetherItems.SoulEnergyLiquidItem.getIconFromDamage(0)%16)*16, (NetherItems.SoulEnergyLiquidItem.getIconFromDamage(0)/16)*16, 16, nFillState);
		}*/
		
		int nRequired = this.benchInventory.getSoulEnergyRequired();
		if (nRequired > 0)
			fontRenderer.drawString("+ " + nRequired + " Energy", var5 + 39, var6 + 72, 0x000000);
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;

		if (par1 > var5 + 9 && par1 < var5 + 9 + 21 && par2 > var6 + 68 - 32 && par2 < var6 + 68) {
			ItemStack par1ItemStack = NetherStuffs.SoulEnergyLiquid.asItemStack().copy();
			par1ItemStack.stackSize = this.benchInventory.getCurrentTankLevel();
			par1ItemStack.setItemDamage(this.benchInventory.maxTankLevel);
			drawItemStackTooltip(par1ItemStack , par1, par2);
		}
	}
}
