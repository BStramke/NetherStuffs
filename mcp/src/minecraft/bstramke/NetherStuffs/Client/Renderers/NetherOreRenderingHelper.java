package bstramke.NetherStuffs.Client.Renderers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import bstramke.NetherStuffs.Blocks.Ore;
import bstramke.NetherStuffs.Blocks.OreExtended;
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
		renderer.renderFaceYNeg(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(0, metadata));
		var4.draw();

		var4.startDrawingQuads();
		var4.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(1, metadata));
		var4.draw();

		var4.startDrawingQuads();
		var4.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(2, metadata));
		var4.draw();

		var4.startDrawingQuads();
		var4.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(3, metadata));
		var4.draw();

		var4.startDrawingQuads();
		var4.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(4, metadata));
		var4.draw();

		var4.startDrawingQuads();
		var4.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(5, metadata));
		var4.draw();
		
		Ore ore = (Ore) par1Block;
		
		if (metadata != Ore.netherStone) {
			var4.startDrawingQuads();
			var4.setNormal(0.0F, -1.0F, 0.0F);
			var4.setBrightness(1024);
			renderer.renderFaceYNeg(par1Block, 0.0D, 0.0D, 0.0D, ore.getIconOreOverlay(metadata));
			var4.draw();
			
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 1.0F, 0.0F);
			renderer.renderFaceYPos(par1Block, 0.0D, 0.0D, 0.0D, ore.getIconOreOverlay(metadata));
			var4.draw();
			
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 0.0F, -1.0F);
			renderer.renderFaceZNeg(par1Block, 0.0D, 0.0D, 0.0D, ore.getIconOreOverlay(metadata));
			var4.draw();
			
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 0.0F, 1.0F);
			renderer.renderFaceZPos(par1Block, 0.0D, 0.0D, 0.0D, ore.getIconOreOverlay(metadata));
			var4.draw();
			
			var4.startDrawingQuads();
			var4.setNormal(-1.0F, 0.0F, 0.0F);
			renderer.renderFaceXNeg(par1Block, 0.0D, 0.0D, 0.0D, ore.getIconOreOverlay(metadata));
			var4.draw();
			
			var4.startDrawingQuads();
			var4.setNormal(1.0F, 0.0F, 0.0F);
			renderer.renderFaceXPos(par1Block, 0.0D, 0.0D, 0.0D, ore.getIconOreOverlay(metadata));
			var4.draw();
		}

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess iBlockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		renderer.renderStandardBlock(block, x, y, z);
		int nMetadata = iBlockAccess.getBlockMetadata(x, y, z);
		if (nMetadata != Ore.netherStone) {
			Tessellator var8 = Tessellator.instance;
			var8.setBrightness(1024);
			var8.setColorOpaque_F(1.0F, 1.0F, 1.0F);

			Ore ore = (Ore) block;
			
			if (block.shouldSideBeRendered(iBlockAccess, x, y - 1, z, 0))
				renderer.renderFaceYNeg(block, x, y, z, ore.getIconOreOverlay(nMetadata));
			if (block.shouldSideBeRendered(iBlockAccess, x, y + 1, z, 1))
				renderer.renderFaceYPos(block, x, y, z, ore.getIconOreOverlay(nMetadata));
			if (block.shouldSideBeRendered(iBlockAccess, x, y, z + 1, 3))
				renderer.renderFaceZPos(block, x, y, z, ore.getIconOreOverlay(nMetadata));
			if (block.shouldSideBeRendered(iBlockAccess, x, y, z - 1, 2))
				renderer.renderFaceZNeg(block, x, y, z, ore.getIconOreOverlay(nMetadata));
			if (block.shouldSideBeRendered(iBlockAccess, x - 1, y, z, 4))
				renderer.renderFaceXNeg(block, x, y, z, ore.getIconOreOverlay(nMetadata));
			if (block.shouldSideBeRendered(iBlockAccess, x + 1, y, z, 5))
				renderer.renderFaceXPos(block, x, y, z, ore.getIconOreOverlay(nMetadata));
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
