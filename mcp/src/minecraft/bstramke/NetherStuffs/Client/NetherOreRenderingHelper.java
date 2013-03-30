package bstramke.NetherStuffs.Client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import bstramke.NetherStuffs.Blocks.NetherOre;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class NetherOreRenderingHelper implements ISimpleBlockRenderingHandler {
	private static int renderId;

	public static NetherOreRenderingHelper instance = new NetherOreRenderingHelper();

	public NetherOreRenderingHelper() {
		this.renderId = RenderingRegistry.getNextAvailableRenderId();
	}

	@Override
	public void renderInventoryBlock(Block par1Block, int metadata, int modelID, RenderBlocks renderer) {
		Tessellator var4 = Tessellator.instance;
		par1Block.setBlockBoundsForItemRender();
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		var4.startDrawingQuads();
		var4.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderBottomFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(0, metadata));
		if (metadata != NetherOre.netherStone) {
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(0.0F, -1.0F, 0.0F);
			var4.setBrightness(15);
			renderer.renderBottomFace(par1Block, 0.0D, 0.0D, 0.0D, ((NetherOre) par1Block).getIconOreOverlay(metadata));
		}

		var4.draw();

		var4.startDrawingQuads();
		var4.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderTopFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(1, metadata));
		if (metadata != NetherOre.netherStone) {
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 1.0F, 0.0F);
			var4.setBrightness(15);
			renderer.renderTopFace(par1Block, 0.0D, 0.0D, 0.0D, ((NetherOre) par1Block).getIconOreOverlay(metadata));
		}
		var4.draw();

		var4.startDrawingQuads();
		var4.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderEastFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(2, metadata));
		if (metadata != NetherOre.netherStone) {
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 0.0F, -1.0F);
			var4.setBrightness(15);
			renderer.renderEastFace(par1Block, 0.0D, 0.0D, 0.0D, ((NetherOre) par1Block).getIconOreOverlay(metadata));
		}
		var4.draw();
		var4.startDrawingQuads();
		var4.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderWestFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(3, metadata));
		if (metadata != NetherOre.netherStone) {
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 0.0F, 1.0F);
			var4.setBrightness(15);
			renderer.renderWestFace(par1Block, 0.0D, 0.0D, 0.0D, ((NetherOre) par1Block).getIconOreOverlay(metadata));
		}
		var4.draw();
		var4.startDrawingQuads();
		var4.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderNorthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(4, metadata));
		if (metadata != NetherOre.netherStone) {
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(-1.0F, 0.0F, 0.0F);
			var4.setBrightness(15);
			renderer.renderNorthFace(par1Block, 0.0D, 0.0D, 0.0D, ((NetherOre) par1Block).getIconOreOverlay(metadata));
		}
		var4.draw();
		var4.startDrawingQuads();
		var4.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderSouthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(5, metadata));
		if (metadata != NetherOre.netherStone) {
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(1.0F, 0.0F, 0.0F);
			var4.setBrightness(15);
			renderer.renderSouthFace(par1Block, 0.0D, 0.0D, 0.0D, ((NetherOre) par1Block).getIconOreOverlay(metadata));
		}
		var4.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess iBlockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		renderer.renderStandardBlock(block, x, y, z);
		int nMetadata = iBlockAccess.getBlockMetadata(x, y, z);
		if (nMetadata != NetherOre.netherStone) {
			Tessellator var8 = Tessellator.instance;

			// renderer.enableAO = false;
			// renderer.aoType = 0;
			var8.setBrightness(1024);
			var8.setColorOpaque_F(1.0F, 1.0F, 1.0F);

			if (block.shouldSideBeRendered(iBlockAccess, x, y - 1, z, 0))
				renderer.renderBottomFace(block, x, y, z, ((NetherOre) block).getIconOreOverlay(nMetadata));
			if (block.shouldSideBeRendered(iBlockAccess, x, y + 1, z, 1))
				renderer.renderTopFace(block, x, y, z, ((NetherOre) block).getIconOreOverlay(nMetadata));
			if (block.shouldSideBeRendered(iBlockAccess, x, y, z + 1, 3))
				renderer.renderWestFace(block, x, y, z, ((NetherOre) block).getIconOreOverlay(nMetadata));
			if (block.shouldSideBeRendered(iBlockAccess, x, y, z - 1, 2))
				renderer.renderEastFace(block, x, y, z, ((NetherOre) block).getIconOreOverlay(nMetadata));
			if (block.shouldSideBeRendered(iBlockAccess, x - 1, y, z, 4))
				renderer.renderNorthFace(block, x, y, z, ((NetherOre) block).getIconOreOverlay(nMetadata));
			if (block.shouldSideBeRendered(iBlockAccess, x + 1, y, z, 5))
				renderer.renderSouthFace(block, x, y, z, ((NetherOre) block).getIconOreOverlay(nMetadata));
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return renderId;
	}
}
