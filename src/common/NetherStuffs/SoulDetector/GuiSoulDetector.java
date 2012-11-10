package NetherStuffs.SoulDetector;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.network.PacketDispatcher;

import NetherStuffs.Blocks.SoulDetector;
import NetherStuffs.Client.ClientProxy;

public class GuiSoulDetector extends GuiContainer {

	private TileSoulDetector tile_entity;
	private GuiButton btnRangeIncZUp;
	private GuiButton btnRangeDecZUp;
	private GuiButton btnRangeIncNorth;
	private GuiButton btnRangeDecNorth;
	private GuiButton btnRangeIncWest;
	private GuiButton btnRangeDecWest;
	private GuiButton btnRangeIncEast;
	private GuiButton btnRangeDecEast;
	private GuiButton btnRangeIncZDown;
	private GuiButton btnRangeDecZDown;
	private GuiButton btnRangeIncSouth;
	private GuiButton btnRangeDecSouth;

	private EntityPlayer player;

	public GuiSoulDetector(TileSoulDetector tile_entity, EntityPlayer player) {
		super(new ContainerSoulDetector(tile_entity));
		this.tile_entity = tile_entity;
	}

	public void initGui() {
		super.initGui();

		this.controlList.add(this.btnRangeIncZUp = new GuiButtonSoulDetector(1, this.guiLeft + 57, this.guiTop + 15, 9, 9, false));
		this.controlList.add(this.btnRangeDecZUp = new GuiButtonSoulDetector(2, this.guiLeft + 57 + 9, this.guiTop + 15, 9, 9, true));

		this.controlList.add(this.btnRangeIncNorth = new GuiButtonSoulDetector(3, this.guiLeft + 96, this.guiTop + 15, 9, 9, false));
		this.controlList.add(this.btnRangeDecNorth = new GuiButtonSoulDetector(4, this.guiLeft + 96 + 9, this.guiTop + 15, 9, 9, true));

		this.controlList.add(this.btnRangeIncWest = new GuiButtonSoulDetector(5, this.guiLeft + 57, this.guiTop + 33, 9, 9, false));
		this.controlList.add(this.btnRangeDecWest = new GuiButtonSoulDetector(6, this.guiLeft + 57 + 9, this.guiTop + 33, 9, 9, true));

		this.controlList.add(this.btnRangeIncEast = new GuiButtonSoulDetector(7, this.guiLeft + 117, this.guiTop + 33, 9, 9, false));
		this.controlList.add(this.btnRangeDecEast = new GuiButtonSoulDetector(8, this.guiLeft + 117 + 9, this.guiTop + 33, 9, 9, true));

		this.controlList.add(this.btnRangeIncZDown = new GuiButtonSoulDetector(9, this.guiLeft + 57, this.guiTop + 51, 9, 9, false));
		this.controlList.add(this.btnRangeDecZDown = new GuiButtonSoulDetector(10, this.guiLeft + 57 + 9, this.guiTop + 51, 9, 9, true));

		this.controlList.add(this.btnRangeIncSouth = new GuiButtonSoulDetector(11, this.guiLeft + 96, this.guiTop + 51, 9, 9, false));
		this.controlList.add(this.btnRangeDecSouth = new GuiButtonSoulDetector(12, this.guiLeft + 96 + 9, this.guiTop + 51, 9, 9, true));

		int nMeta = this.tile_entity.getBlockMetadata() & 7;
		switch (nMeta) {
		case SoulDetector.mk1:
			drawDetectionMark1();
			break;
		case SoulDetector.mk2:
			drawDetectionMark2();
		case SoulDetector.mk3:
			drawDetectionMark3();
		case SoulDetector.mk4:
			drawDetectionMark4();
		}
	}

	private void drawRangeNumbers() {
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;

		Integer nRangeUp = ((Integer) this.tile_entity.getRange(ForgeDirection.UP));
		Integer nRangeNorth = ((Integer) this.tile_entity.getRange(ForgeDirection.NORTH));
		Integer nRangeWest = ((Integer) this.tile_entity.getRange(ForgeDirection.WEST));
		Integer nRangeEast = ((Integer) this.tile_entity.getRange(ForgeDirection.EAST));
		Integer nRangeDown = ((Integer) this.tile_entity.getRange(ForgeDirection.DOWN));
		Integer nRangeSouth = ((Integer) this.tile_entity.getRange(ForgeDirection.SOUTH));

		this.fontRenderer.drawString(nRangeUp.toString(), var5 + 57 - nRangeUp.toString().length() * 7, var6 + 16, 0xffffff);
		this.fontRenderer.drawString(nRangeNorth.toString(), var5 + 96 - nRangeNorth.toString().length() * 7, var6 + 16, 0xffffff);
		this.fontRenderer.drawString(nRangeWest.toString(), var5 + 57 - nRangeWest.toString().length() * 7, var6 + 34, 0xffffff);
		this.fontRenderer.drawString(nRangeEast.toString(), var5 + 117 - nRangeEast.toString().length() * 7, var6 + 34, 0xffffff);
		this.fontRenderer.drawString(nRangeDown.toString(), var5 + 57 - nRangeDown.toString().length() * 7, var6 + 52, 0xffffff);
		this.fontRenderer.drawString(nRangeSouth.toString(), var5 + 96 - nRangeSouth.toString().length() * 7, var6 + 52, 0xffffff);
	}

	private void drawDetectionMark1() {
		// no settings needed as far as i know
	}

	private void drawDetectionMark2() {
		// Hostile / Non-Hostile Setting
	}

	private void drawDetectionMark3() {
		// Mob-Selector
	}

	private void drawDetectionMark4() {
		// Players / Playerlists
	}

	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton.enabled) {
			if (par1GuiButton.id >= 1 && par1GuiButton.id <= 12) {
				ForgeDirection dir = ForgeDirection.UNKNOWN;
				switch (par1GuiButton.id) {
				case 1: // ZUp+
				case 2: // ZUp-
					dir = ForgeDirection.UP;
					break;
				case 3: // N+
				case 4: // N-
					dir = ForgeDirection.NORTH;
					break;
				case 5: // W+
				case 6: // W-
					dir = ForgeDirection.WEST;
					break;
				case 7: // E+
				case 8: // E-
					dir = ForgeDirection.EAST;
					break;
				case 9: // ZDown+
				case 10: // ZDown-
					dir = ForgeDirection.DOWN;
					break;
				case 11: // S+
				case 12: // S-
					dir = ForgeDirection.SOUTH;
					break;
				}

				if (par1GuiButton.id % 2 == 0)// even numbers are for decreasing
					this.tile_entity.decRange(dir);
				else
					this.tile_entity.incRange(dir);
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		int nMeta = this.tile_entity.getBlockMetadata() & 7;

		if (nMeta == SoulDetector.mk1 || nMeta == SoulDetector.mk2 || nMeta == SoulDetector.mk3 || nMeta == SoulDetector.mk4)
			this.fontRenderer.drawString("Soul Detector MK " + (nMeta + 1), 6, 6, 4210752);
		else
			this.fontRenderer.drawString("Soul Detector", 6, 6, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		int var4 = this.mc.renderEngine.getTexture("/souldetector.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(var4);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
		drawRangeNumbers();
	}

	private int nUpdateDelayCounter = 0;

	@Override
	public void updateScreen() {
		super.updateScreen();
		nUpdateDelayCounter++;
		if (nUpdateDelayCounter >= 20) {
			requeryDataFromServer();
			nUpdateDelayCounter = 0;
		}
	}

	private void requeryDataFromServer() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeShort(2);
			outputStream.writeInt(this.tile_entity.xCoord);
			outputStream.writeInt(this.tile_entity.yCoord);
			outputStream.writeInt(this.tile_entity.zCoord);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "NetherStuffs";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToServer(packet);
	}
}
