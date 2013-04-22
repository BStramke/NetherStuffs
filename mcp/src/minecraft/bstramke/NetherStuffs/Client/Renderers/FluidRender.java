package bstramke.NetherStuffs.Client.Renderers;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import bstramke.NetherStuffs.Liquids.LiquidBase;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class FluidRender implements ISimpleBlockRenderingHandler {

	public static int fluidModel = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		// Inventory should be an item. renderer is not here!
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		return renderFluids(renderer, world, block, x, y, z);
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return fluidModel;
	}

	public boolean renderFluids(RenderBlocks renderer, IBlockAccess world, Block block, int x, int y, int z) {
		Tessellator tessellator = Tessellator.instance;
		int l = block.colorMultiplier(world, x, y, z);
		float f = (float) (l >> 16 & 255) / 255.0F;
		float f1 = (float) (l >> 8 & 255) / 255.0F;
		float f2 = (float) (l & 255) / 255.0F;
		boolean flag = block.shouldSideBeRendered(world, x, y + 1, z, 1);
		boolean flag1 = block.shouldSideBeRendered(world, x, y - 1, z, 0);
		boolean[] aboolean = new boolean[] { block.shouldSideBeRendered(world, x, y, z - 1, 2), block.shouldSideBeRendered(world, x, y, z + 1, 3),
				block.shouldSideBeRendered(world, x - 1, y, z, 4), block.shouldSideBeRendered(world, x + 1, y, z, 5) };

		if (!flag && !flag1 && !aboolean[0] && !aboolean[1] && !aboolean[2] && !aboolean[3]) {
			return false;
		} else {
			boolean flag2 = false;
			float f3 = 0.5F;
			float f4 = 1.0F;
			float f5 = 0.8F;
			float f6 = 0.6F;
			double d0 = 0.0D;
			double d1 = 1.0D;
			Material material = block.blockMaterial;
			int i1 = world.getBlockMetadata(x, y, z);
			double d2 = (double) renderer.getFluidHeight(x, y, z, material);
			double d3 = (double) renderer.getFluidHeight(x, y, z + 1, material);
			double d4 = (double) renderer.getFluidHeight(x + 1, y, z + 1, material);
			double d5 = (double) renderer.getFluidHeight(x + 1, y, z, material);
			double d6 = 0.0010000000474974513D;
			float f7;

			if (renderer.renderAllFaces || flag) {
				flag2 = true;
				Icon icon = block.getBlockTexture(world, x, y, z, 1);// renderer.func_94165_a(block, 1, i1);
				float f8 = (float) LiquidBase.getFlowDirection(world, x, y, z, material);

				if (f8 > -999.0F) {
					icon = block.getBlockTexture(world, x, y, z, 2);// renderer.func_94165_a(block, 2, i1);
				}

				d2 -= d6;
				d3 -= d6;
				d4 -= d6;
				d5 -= d6;
				double d7;
				double d8;
				double d9;
				double d10;
				double d11;
				double d12;
				double d13;
				double d14;

				if (f8 < -999.0F) {
					d8 = (double) icon.getInterpolatedU(0.0D);
					d12 = (double) icon.getInterpolatedV(0.0D);
					d7 = d8;
					d11 = (double) icon.getInterpolatedV(16.0D);
					d10 = (double) icon.getInterpolatedU(16.0D);
					d14 = d11;
					d9 = d10;
					d13 = d12;
				} else {
					f7 = MathHelper.sin(f8) * 0.25F;
					float f9 = MathHelper.cos(f8) * 0.25F;
					d8 = (double) icon.getInterpolatedU((double) (8.0F + (-f9 - f7) * 16.0F));
					d12 = (double) icon.getInterpolatedV((double) (8.0F + (-f9 + f7) * 16.0F));
					d7 = (double) icon.getInterpolatedU((double) (8.0F + (-f9 + f7) * 16.0F));
					d11 = (double) icon.getInterpolatedV((double) (8.0F + (f9 + f7) * 16.0F));
					d10 = (double) icon.getInterpolatedU((double) (8.0F + (f9 + f7) * 16.0F));
					d14 = (double) icon.getInterpolatedV((double) (8.0F + (f9 - f7) * 16.0F));
					d9 = (double) icon.getInterpolatedU((double) (8.0F + (f9 - f7) * 16.0F));
					d13 = (double) icon.getInterpolatedV((double) (8.0F + (-f9 - f7) * 16.0F));
				}

				tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
				f7 = 1.0F;
				tessellator.setColorOpaque_F(f4 * f7 * f, f4 * f7 * f1, f4 * f7 * f2);
				tessellator.addVertexWithUV((double) (x + 0), (double) y + d2, (double) (z + 0), d8, d12);
				tessellator.addVertexWithUV((double) (x + 0), (double) y + d3, (double) (z + 1), d7, d11);
				tessellator.addVertexWithUV((double) (x + 1), (double) y + d4, (double) (z + 1), d10, d14);
				tessellator.addVertexWithUV((double) (x + 1), (double) y + d5, (double) (z + 0), d9, d13);
			}

			if (renderer.renderAllFaces || flag1) {
				tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y - 1, z));
				float f10 = 1.0F;
				tessellator.setColorOpaque_F(f3 * f10, f3 * f10, f3 * f10);
				renderer.renderBottomFace(block, (double) x, (double) y + d6, (double) z, block.getBlockTexture(world, x, y, z, 0));
				flag2 = true;
			}

			for (int side = 0; side < 4; ++side) {
				int xPos = x;
				int zPos = z;

				if (side == 0) {
					zPos = z - 1;
				}

				if (side == 1) {
					++zPos;
				}

				if (side == 2) {
					xPos = x - 1;
				}

				if (side == 3) {
					++xPos;
				}

				Icon icon1 = block.getBlockTexture(world, x, y, z, side + 2);// renderer.func_94165_a(block, j1 + 2, i1);

				if (renderer.renderAllFaces || aboolean[side]) {
					double d15;
					double d16;
					double d17;
					double d18;
					double d19;
					double d20;

					if (side == 0) {
						d15 = d2;
						d17 = d5;
						d16 = (double) x;
						d18 = (double) (x + 1);
						d19 = (double) z + d6;
						d20 = (double) z + d6;
					} else if (side == 1) {
						d15 = d4;
						d17 = d3;
						d16 = (double) (x + 1);
						d18 = (double) x;
						d19 = (double) (z + 1) - d6;
						d20 = (double) (z + 1) - d6;
					} else if (side == 2) {
						d15 = d3;
						d17 = d2;
						d16 = (double) x + d6;
						d18 = (double) x + d6;
						d19 = (double) (z + 1);
						d20 = (double) z;
					} else {
						d15 = d5;
						d17 = d4;
						d16 = (double) (x + 1) - d6;
						d18 = (double) (x + 1) - d6;
						d19 = (double) z;
						d20 = (double) (z + 1);
					}

					flag2 = true;
					float f11 = icon1.getInterpolatedU(0.0D);
					f7 = icon1.getInterpolatedU(8.0D);
					float f12 = icon1.getInterpolatedV((1.0D - d15) * 16.0D * 0.5D);
					float f13 = icon1.getInterpolatedV((1.0D - d17) * 16.0D * 0.5D);
					float f14 = icon1.getInterpolatedV(8.0D);
					tessellator.setBrightness(block.getMixedBrightnessForBlock(world, xPos, y, zPos));
					float f15 = 1.0F;

					if (side < 2) {
						f15 *= f5;
					} else {
						f15 *= f6;
					}

					tessellator.setColorOpaque_F(f4 * f15 * f, f4 * f15 * f1, f4 * f15 * f2);
					tessellator.addVertexWithUV(d16, (double) y + d15, d19, (double) f11, (double) f12);
					tessellator.addVertexWithUV(d18, (double) y + d17, d20, (double) f7, (double) f13);
					tessellator.addVertexWithUV(d18, (double) (y + 0), d20, (double) f7, (double) f14);
					tessellator.addVertexWithUV(d16, (double) (y + 0), d19, (double) f11, (double) f14);
				}
			}

			renderer.renderMinY = d0;
			renderer.renderMaxY = d1;
			return flag2;
		}
	}

}
