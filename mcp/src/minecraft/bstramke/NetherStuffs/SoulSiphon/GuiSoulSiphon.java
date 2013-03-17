package bstramke.NetherStuffs.SoulSiphon;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.SoulSiphon;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Items.NetherItems;

public class GuiSoulSiphon extends GuiContainer {

	private TileSoulSiphon tile_entity;

	public GuiSoulSiphon(InventoryPlayer player_inventory, TileSoulSiphon tile_entity) {
		super(new ContainerSoulSiphon(tile_entity, player_inventory));
		this.tile_entity = tile_entity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		switch (tile_entity.blockMetadata) {
		case SoulSiphon.mk1:
			this.fontRenderer.drawString("Soul Siphon MK 1", 38, 6, 4210752);
			break;
		case SoulSiphon.mk2:
			this.fontRenderer.drawString("Soul Siphon MK 2", 38, 6, 4210752);
			break;
		case SoulSiphon.mk3:
			this.fontRenderer.drawString("Soul Siphon MK 3", 38, 6, 4210752);
			break;
		case SoulSiphon.mk4:
			this.fontRenderer.drawString("Soul Siphon MK 4", 38, 6, 4210752);
			break;
		}

		this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.func_98187_b(CommonProxy.SOULSIPHON_PNG);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);

		// draw fill state from bottom to top
		int nBottomLeftY = var6 + 69;
		/*this.mc.renderEngine.bindTexture(mc.renderEngine.getTexture(NetherItems.SoulEnergyLiquidItem.getTextureFile()));
		int nFillState = this.tile_entity.getFillingScaled(32);
		int y = 0;
		for (int x = 16; x <= 32 && x <= nFillState; x += 16) {
			this.drawTexturedModalRect(var5 + 12, nBottomLeftY - x, (NetherItems.SoulEnergyLiquidItem.getIconFromDamage(0) % 16) * 16,
					(NetherItems.SoulEnergyLiquidItem.getIconFromDamage(0) / 16) * 16, 16, 16);
			y = x;
		}

		if (nFillState % 16 != 0) {
			nFillState -= y;
			this.drawTexturedModalRect(var5 + 12, nBottomLeftY - y - nFillState, (NetherItems.SoulEnergyLiquidItem.getIconFromDamage(0) % 16) * 16,
					(NetherItems.SoulEnergyLiquidItem.getIconFromDamage(0) / 16) * 16, 16, nFillState);
		}*/
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;

		if (par1 > var5 + 9 && par1 < var5 + 9 + 21 && par2 > var6 + 68 - 32 && par2 < var6 + 68) {
			ItemStack par1ItemStack = NetherStuffs.SoulEnergyLiquid.asItemStack().copy();
			par1ItemStack.setItemDamage(tile_entity.maxTankLevel);
			par1ItemStack.stackSize = this.tile_entity.getCurrentTankLevel();
			drawItemStackTooltip(par1ItemStack, par1, par2);
		}
	}
}
