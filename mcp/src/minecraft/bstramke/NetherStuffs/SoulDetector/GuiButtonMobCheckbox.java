package bstramke.NetherStuffs.SoulDetector;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

import bstramke.NetherStuffs.Common.CommonProxy;

public class GuiButtonMobCheckbox extends GuiButton {
	private int yButtonOffset;
	private int nMobIndex;

	public GuiButtonMobCheckbox(int par1, int par2, int par3, boolean isChecked, int nMobIndex) {
		super(par1, par2, par3, 8, 8, "");
		this.nMobIndex = nMobIndex;
		if (isChecked)
			this.yButtonOffset = 16;
		else
			this.yButtonOffset = 0;
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		if (this.drawButton) {
			// FontRenderer var4 = par1Minecraft.fontRenderer;
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1Minecraft.renderEngine.getTexture(CommonProxy.MOBBUTTONS_PNG));
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
			int var5 = this.getHoverState(this.field_82253_i);
			int nStateOffset = 0;
			if (var5 == 0) // disabled
			{} else if (var5 == 1) // not hovering
				nStateOffset = 0;
			else if (var5 == 2) // hovering
				nStateOffset = 32;

			int yStart = this.nMobIndex / 16;
			if (this.nMobIndex < 16)
				this.drawTexturedModalRect(this.xPosition, this.yPosition, this.nMobIndex * 8, this.yButtonOffset + nStateOffset, 8, 8);
			else
				this.drawTexturedModalRect(this.xPosition, this.yPosition, (this.nMobIndex - 16) * 8, 8 + this.yButtonOffset + nStateOffset, 8, 8);

			// this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + var5 * 20, this.width / 2, this.height);
			// this.mouseDragged(par1Minecraft, par2, par3);
			// int var6 = 14737632;

			/*
			 * if (!this.enabled) { var6 = -6250336; } else if (this.field_82253_i) { var6 = 16777120; }
			 * 
			 * this.drawCenteredString(var4, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, var6);
			 */
		}
	}
}
