/**
 * Copyright (c) SpaceToad, 2011
 * http://www.mod-buildcraft.com
 *
 * BuildCraft is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */

package bstramke.NetherStuffs.Client.Renderers;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import bstramke.NetherStuffs.Blocks.soulEngine.TileSoulEngine;
import bstramke.NetherStuffs.Blocks.soulEngine.TileSoulEngine.EnergyStage;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class RenderSoulEngine extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {
	
	private static final ResourceLocation BaseTexture = new ResourceLocation(CommonProxy.GFXFOLDERPREFIX + "base_soulengine.png");
	private static final ResourceLocation ChamberTexture = new ResourceLocation(CommonProxy.GFXFOLDERPREFIX+"chamber.png");
	
	private static final ResourceLocation TrunkBlue = new ResourceLocation(CommonProxy.GFXFOLDERPREFIX+"trunk_blue.png");
	private static final ResourceLocation TrunkGreen = new ResourceLocation(CommonProxy.GFXFOLDERPREFIX+"trunk_green.png");
	private static final ResourceLocation TrunkYellow = new ResourceLocation(CommonProxy.GFXFOLDERPREFIX+"trunk_yellow.png");
	private static final ResourceLocation TrunkRed = new ResourceLocation(CommonProxy.GFXFOLDERPREFIX+"trunk_red.png");
	
	private static int renderId;

	public static RenderSoulEngine instance = new RenderSoulEngine();
	
	private ModelBase model = new ModelBase() {
	};

	private ModelRenderer box;
	private ModelRenderer trunk;
	private ModelRenderer movingBox;
	private ModelRenderer chamber;
	private String baseTexture;

	public RenderSoulEngine() {

		this.renderId = RenderingRegistry.getNextAvailableRenderId();
		
		// constructor:
		box = new ModelRenderer(model, 0, 1);
		box.addBox(-8F, -8F, -8F, 16, 4, 16);
		box.rotationPointX = 8;
		box.rotationPointY = 8;
		box.rotationPointZ = 8;

		trunk = new ModelRenderer(model, 1, 1);
		trunk.addBox(-4F, -4F, -4F, 8, 12, 8);
		trunk.rotationPointX = 8F;
		trunk.rotationPointY = 8F;
		trunk.rotationPointZ = 8F;

		movingBox = new ModelRenderer(model, 0, 1);
		movingBox.addBox(-8F, -4, -8F, 16, 4, 16);
		movingBox.rotationPointX = 8F;
		movingBox.rotationPointY = 8F;
		movingBox.rotationPointZ = 8F;

		chamber = new ModelRenderer(model, 1, 1);
		chamber.addBox(-5F, -4, -5F, 10, 2, 10);
		chamber.rotationPointX = 8F;
		chamber.rotationPointY = 8F;
		chamber.rotationPointZ = 8F;
	}

	public RenderSoulEngine(String baseTexture) {
		this();
		//this.baseTexture = baseTexture;
		setTileEntityRenderer(TileEntityRenderer.instance);
	}

	public void inventoryRender(double x, double y, double z, float f, float f1) {
		render(EnergyStage.Blue, 0.25F, ForgeDirection.UP, x, y, z);
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		if (tileentity != null && tileentity instanceof TileSoulEngine) {
			render(((TileSoulEngine) tileentity).getEnergyStage(), ((TileSoulEngine) tileentity).progress, ((TileSoulEngine) tileentity).orientation, x, y, z);
		}
	}

	private void render(EnergyStage energy, float progress, ForgeDirection orientation, double x, double y, double z) {
		GL11.glPushMatrix();
		GL11.glDisable(2896 /* GL_LIGHTING */);

		GL11.glTranslatef((float) x, (float) y, (float) z);

		float step;

		if (progress > 0.5) {
			step = 7.99F - (progress - 0.5F) * 2F * 7.99F;
		} else {
			step = progress * 2F * 7.99F;
		}

		float[] angle = { 0, 0, 0 };
		float[] translate = { 0, 0, 0 };
		float translatefact = step / 16;

		switch (orientation) {
		case EAST:
			angle[2] = (float) -Math.PI / 2;
			translate[0] = 1;
			break;
		case WEST:
			angle[2] = (float) Math.PI / 2;
			translate[0] = -1;
			break;
		case UP:
			translate[1] = 1;
			break;
		case DOWN:
			angle[2] = (float) Math.PI;
			translate[1] = -1;
			break;
		case SOUTH:
			angle[0] = (float) Math.PI / 2;
			translate[2] = 1;
			break;
		case NORTH:
			angle[0] = (float) -Math.PI / 2;
			translate[2] = -1;
			break;
		default:
		}

		box.rotateAngleX = angle[0];
		box.rotateAngleY = angle[1];
		box.rotateAngleZ = angle[2];

		trunk.rotateAngleX = angle[0];
		trunk.rotateAngleY = angle[1];
		trunk.rotateAngleZ = angle[2];

		movingBox.rotateAngleX = angle[0];
		movingBox.rotateAngleY = angle[1];
		movingBox.rotateAngleZ = angle[2];

		chamber.rotateAngleX = angle[0];
		chamber.rotateAngleY = angle[1];
		chamber.rotateAngleZ = angle[2];

		float factor = (float) (1.0 / 16.0);

		this.func_110628_a(BaseTexture);
		
		box.render(factor);

		GL11.glTranslatef(translate[0] * translatefact, translate[1] * translatefact, translate[2] * translatefact);
		movingBox.render(factor);
		GL11.glTranslatef(-translate[0] * translatefact, -translate[1] * translatefact, -translate[2] * translatefact);

		this.func_110628_a(ChamberTexture);

		float chamberf = 2F / 16F;

		for (int i = 0; i <= step + 2; i += 2) {
			chamber.render(factor);
			GL11.glTranslatef(translate[0] * chamberf, translate[1] * chamberf, translate[2] * chamberf);
		}

		for (int i = 0; i <= step + 2; i += 2) {
			GL11.glTranslatef(-translate[0] * chamberf, -translate[1] * chamberf, -translate[2] * chamberf);
		}

		switch (energy) {
		case Blue:
			this.func_110628_a(TrunkBlue);
			break;
		case Green:
			this.func_110628_a(TrunkGreen);
			break;
		case Yellow:
			this.func_110628_a(TrunkYellow);
			break;
		default:
			this.func_110628_a(TrunkRed);
			break;
		}

		this.func_110628_a(ChamberTexture);

		trunk.render(factor);

		GL11.glEnable(2896 /* GL_LIGHTING */);
		GL11.glPopMatrix();
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		inventoryRender(-0.5, -0.5, -0.5, 0, 0);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		// TODO Auto-generated method stub
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