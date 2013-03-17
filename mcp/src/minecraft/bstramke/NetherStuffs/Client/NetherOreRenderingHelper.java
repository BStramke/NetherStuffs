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

		int nOffset = 0;
		if (metadata == NetherOre.demonicOre)
			nOffset = 2;
		else
			nOffset = 1;

		var4.startDrawingQuads();
		var4.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderBottomFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(0, metadata));
		if (metadata != NetherOre.netherStone) {
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(0.0F, -1.0F, 0.0F);
			var4.setBrightness(1024);
			renderer.renderBottomFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(0, metadata + nOffset));
		}

		var4.draw();

		var4.startDrawingQuads();
		var4.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderTopFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(1, metadata));
		if (metadata != NetherOre.netherStone) {
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 1.0F, 0.0F);
			var4.setBrightness(1024);
			renderer.renderTopFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(1, metadata + nOffset));
		}
		var4.draw();

		var4.startDrawingQuads();
		var4.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderEastFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(2, metadata));
		if (metadata != NetherOre.netherStone) {
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 0.0F, -1.0F);
			var4.setBrightness(1024);
			renderer.renderEastFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(2, metadata + nOffset));
		}
		var4.draw();
		var4.startDrawingQuads();
		var4.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderWestFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(3, metadata));
		if (metadata != NetherOre.netherStone) {
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 0.0F, 1.0F);
			var4.setBrightness(1024);
			renderer.renderWestFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(3, metadata + nOffset));
		}
		var4.draw();
		var4.startDrawingQuads();
		var4.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderNorthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(4, metadata));
		if (metadata != NetherOre.netherStone) {
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(-1.0F, 0.0F, 0.0F);
			var4.setBrightness(1024);
			renderer.renderNorthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(4, metadata + nOffset));
		}
		var4.draw();
		var4.startDrawingQuads();
		var4.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderSouthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(5, metadata));
		if (metadata != NetherOre.netherStone) {
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(1.0F, 0.0F, 0.0F);
			var4.setBrightness(1024);
			renderer.renderSouthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(5, metadata + nOffset));
		}
		var4.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		renderer.renderStandardBlock(block, x, y, z);
		int nMetadata = world.getBlockMetadata(x, y, z);
		if (nMetadata != NetherOre.netherStone) {
			Tessellator var8 = Tessellator.instance;

			int nOffset = 0;
			if (nMetadata == NetherOre.demonicOre)
				nOffset = 2;
			else
				nOffset = 1;
			renderer.enableAO = false;
			renderer.aoType = 0;
			var8.setBrightness(1024);
			
			
			renderer.renderWestFace(block, x, y, z, block.getBlockTextureFromSideAndMetadata(5, nMetadata + nOffset));
			renderer.renderEastFace(block, x, y, z, block.getBlockTextureFromSideAndMetadata(5, nMetadata + nOffset));
			renderer.renderNorthFace(block, x, y, z, block.getBlockTextureFromSideAndMetadata(5, nMetadata + nOffset));
			renderer.renderSouthFace(block, x, y, z, block.getBlockTextureFromSideAndMetadata(5, nMetadata + nOffset));
			renderer.renderTopFace(block, x, y, z, block.getBlockTextureFromSideAndMetadata(5, nMetadata + nOffset));
			renderer.renderBottomFace(block, x, y, z, block.getBlockTextureFromSideAndMetadata(5, nMetadata + nOffset));
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
