package bstramke.NetherStuffs.Liquids;

import java.text.NumberFormat;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import bstramke.NetherStuffs.Blocks.BlockRegistry;
import bstramke.NetherStuffs.Client.Renderers.FluidRender;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LiquidBase extends Block {

	public static String[] textureNames = new String[] { "SoulEnergy", "Demonic" };
	public static Icon[] stillIcons;
	public static Icon[] flowIcons;

	public LiquidBase(int par1, Material par2Material) {
		super(par1, par2Material);
		setHardness(100.0F);
		setLightOpacity(3);
		setUnlocalizedName("NetherStuffsLiquid");
		for (int i = 0; i < LiquidItemBlock.getMetadataSize(); i++)
			LanguageRegistry.instance().addStringLocalization("tile.NetherStuffsLiquid." + LiquidItemBlock.blockNames[i] + ".name", LiquidItemBlock.blockDisplayNames[i]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.stillIcons = new Icon[textureNames.length];
		this.flowIcons = new Icon[textureNames.length];
		for (int i = 0; i < stillIcons.length; i++) {
			stillIcons[i] = par1IconRegister.registerIcon(CommonProxy.getIconLocation("liquid_" + textureNames[i]));
			flowIcons[i] = par1IconRegister.registerIcon(CommonProxy.getIconLocation("liquid_" + textureNames[i] + "_flow"));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if(meta >= textureNames.length)
			return null;
		
		if (side == 0 || side == 1)
			return (stillIcons[meta]);
		return flowIcons[meta];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess par1iBlockAccess, int x, int y, int z, int side) {
		TileEntity tile = par1iBlockAccess.getBlockTileEntity(x, y, z);
		if (tile != null && tile instanceof LiquidTextureLogic)
		{
			if (side == 1 || side == 0)
				return stillIcons[((LiquidTextureLogic) tile).getLiquidType()];
			else
				return flowIcons[((LiquidTextureLogic) tile).getLiquidType()];
		}
		
		int meta = par1iBlockAccess.getBlockMetadata(x, y, z);
		return getIcon(side, meta);
	}

	@Override
	public int getRenderType() {
		return FluidRender.fluidModel;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
	    return 1;
	}

	@SideOnly(Side.CLIENT)
	public static double getFlowDirection(IBlockAccess par0IBlockAccess, int par1, int par2, int par3, Material par4Material) {
		Vec3 var5 = ((LiquidBase) BlockRegistry.LiquidFlowing).getFlowVector(par0IBlockAccess, par1, par2, par3);
		return var5.xCoord == 0.0D && var5.zCoord == 0.0D ? -1000.0D : Math.atan2(var5.zCoord, var5.xCoord) - (Math.PI / 2D);
	}

	private Vec3 getFlowVector(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		Vec3 var5 = par1IBlockAccess.getWorldVec3Pool().getVecFromPool(0.0D, 0.0D, 0.0D);
		int var6 = this.getEffectiveFlowDecay(par1IBlockAccess, par2, par3, par4);

		for (int var7 = 0; var7 < 4; ++var7) {
			int var8 = par2;
			int var10 = par4;

			if (var7 == 0) {
				var8 = par2 - 1;
			}

			if (var7 == 1) {
				var10 = par4 - 1;
			}

			if (var7 == 2) {
				++var8;
			}

			if (var7 == 3) {
				++var10;
			}

			int var11 = this.getEffectiveFlowDecay(par1IBlockAccess, var8, par3, var10);
			int var12;

			if (var11 < 0) {
				if (!par1IBlockAccess.getBlockMaterial(var8, par3, var10).blocksMovement()) {
					var11 = this.getEffectiveFlowDecay(par1IBlockAccess, var8, par3 - 1, var10);

					if (var11 >= 0) {
						var12 = var11 - (var6 - 8);
						var5 = var5.addVector((double) ((var8 - par2) * var12), (double) ((par3 - par3) * var12), (double) ((var10 - par4) * var12));
					}
				}
			} else if (var11 >= 0) {
				var12 = var11 - var6;
				var5 = var5.addVector((double) ((var8 - par2) * var12), (double) ((par3 - par3) * var12), (double) ((var10 - par4) * var12));
			}
		}

		if (par1IBlockAccess.getBlockMetadata(par2, par3, par4) >= 8) {
			boolean var13 = false;

			if (var13 || this.isBlockSolid(par1IBlockAccess, par2, par3, par4 - 1, 2)) {
				var13 = true;
			}

			if (var13 || this.isBlockSolid(par1IBlockAccess, par2, par3, par4 + 1, 3)) {
				var13 = true;
			}

			if (var13 || this.isBlockSolid(par1IBlockAccess, par2 - 1, par3, par4, 4)) {
				var13 = true;
			}

			if (var13 || this.isBlockSolid(par1IBlockAccess, par2 + 1, par3, par4, 5)) {
				var13 = true;
			}

			if (var13 || this.isBlockSolid(par1IBlockAccess, par2, par3 + 1, par4 - 1, 2)) {
				var13 = true;
			}

			if (var13 || this.isBlockSolid(par1IBlockAccess, par2, par3 + 1, par4 + 1, 3)) {
				var13 = true;
			}

			if (var13 || this.isBlockSolid(par1IBlockAccess, par2 - 1, par3 + 1, par4, 4)) {
				var13 = true;
			}

			if (var13 || this.isBlockSolid(par1IBlockAccess, par2 + 1, par3 + 1, par4, 5)) {
				var13 = true;
			}

			if (var13) {
				var5 = var5.normalize().addVector(0.0D, -6.0D, 0.0D);
			}
		}

		var5 = var5.normalize();
		return var5;
	}

	/**
	 * Returns the percentage of the fluid block that is air, based on the given flow decay of the fluid.
	 */
	public static float getFluidHeightPercent(int par0) {
		if (par0 >= 8) {
			par0 = 0;
		}

		return (float) (par0 + 1) / 9.0F;
	}

	/**
	 * Returns the amount of fluid decay at the coordinates, or -1 if the block at the coordinates is not the same material as the fluid.
	 */
	protected int getFlowDecay(World par1World, int par2, int par3, int par4) {
		return par1World.getBlockMaterial(par2, par3, par4) == this.blockMaterial ? par1World.getBlockMetadata(par2, par3, par4) : -1;
	}

	/**
	 * Returns the flow decay but converts values indicating falling liquid (values >=8) to their effective source block value of zero.
	 */
	protected int getEffectiveFlowDecay(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		if (par1IBlockAccess.getBlockMaterial(par2, par3, par4) != this.blockMaterial) {
			return -1;
		} else {
			int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

			if (var5 >= 8) {
				var5 = 0;
			}

			return var5;
		}
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean canCollideCheck(int par1, boolean par2) {
		return par2 && par1 == 0;
	}

	@Override
	public boolean isBlockSolid(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		Material var6 = par1iBlockAccess.getBlockMaterial(par2, par3, par4);
		return var6 == this.blockMaterial ? false : (par5 == 1 ? true : (var6 == Material.ice ? false : super.isBlockSolid(par1iBlockAccess, par2, par3, par4, par5)));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		Material var6 = par1iBlockAccess.getBlockMaterial(par2, par3, par4);
		return var6 == this.blockMaterial ? false : (par5 == 1 ? true : (var6 == Material.ice ? false : super.shouldSideBeRendered(par1iBlockAccess, par2, par3, par4, par5)));
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return null;
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return 0;
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}
	
	@Override
	public void velocityToAddToEntity(World par1World, int par2, int par3, int par4, Entity par5Entity, Vec3 par6Vec3) {
		Vec3 var7 = this.getFlowVector(par1World, par2, par3, par4);
		par6Vec3.xCoord += var7.xCoord;
		par6Vec3.yCoord += var7.yCoord;
		par6Vec3.zCoord += var7.zCoord;
	}

	/**
	 * How many world ticks before ticking
	 */
	public int tickRate() {
		return 30;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getMixedBrightnessForBlock(IBlockAccess par1iBlockAccess, int par2, int par3, int par4) {
		int var5 = par1iBlockAccess.getLightBrightnessForSkyBlocks(par2, par3, par4, 0);
		int var6 = par1iBlockAccess.getLightBrightnessForSkyBlocks(par2, par3 + 1, par4, 0);
		int var7 = var5 & 255;
		int var8 = var6 & 255;
		int var9 = var5 >> 16 & 255;
		int var10 = var6 >> 16 & 255;
		return (var7 > var8 ? var7 : var8) | (var9 > var10 ? var9 : var10) << 16;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getBlockBrightness(IBlockAccess par1iBlockAccess, int par2, int par3, int par4) {
		return 1f;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		int var6;

		if (this.blockMaterial == Material.water) {
			if (par5Random.nextInt(10) == 0) {
				var6 = par1World.getBlockMetadata(par2, par3, par4);

				if (var6 <= 0 || var6 >= 8) {
					par1World.spawnParticle("suspended", (double) ((float) par2 + par5Random.nextFloat()), (double) ((float) par3 + par5Random.nextFloat()),
							(double) ((float) par4 + par5Random.nextFloat()), 0.0D, 0.0D, 0.0D);
				}
			}

			for (var6 = 0; var6 < 0; ++var6) {
				int var7 = par5Random.nextInt(4);
				int var8 = par2;
				int var9 = par4;

				if (var7 == 0) {
					var8 = par2 - 1;
				}

				if (var7 == 1) {
					++var8;
				}

				if (var7 == 2) {
					var9 = par4 - 1;
				}

				if (var7 == 3) {
					++var9;
				}

				if (par1World.getBlockMaterial(var8, par3, var9) == Material.air
						&& (par1World.getBlockMaterial(var8, par3 - 1, var9).blocksMovement() || par1World.getBlockMaterial(var8, par3 - 1, var9).isLiquid())) {
					float var10 = 0.0625F;
					double var11 = (double) ((float) par2 + par5Random.nextFloat());
					double var13 = (double) ((float) par3 + par5Random.nextFloat());
					double var15 = (double) ((float) par4 + par5Random.nextFloat());

					if (var7 == 0) {
						var11 = (double) ((float) par2 - var10);
					}

					if (var7 == 1) {
						var11 = (double) ((float) (par2 + 1) + var10);
					}

					if (var7 == 2) {
						var15 = (double) ((float) par4 - var10);
					}

					if (var7 == 3) {
						var15 = (double) ((float) (par4 + 1) + var10);
					}

					double var17 = 0.0D;
					double var19 = 0.0D;

					if (var7 == 0) {
						var17 = (double) (-var10);
					}

					if (var7 == 1) {
						var17 = (double) var10;
					}

					if (var7 == 2) {
						var19 = (double) (-var10);
					}

					if (var7 == 3) {
						var19 = (double) var10;
					}

					par1World.spawnParticle("splash", var11, var13, var15, var17, 0.0D, var19);
				}
			}
		}

		if (this.blockMaterial == Material.water && par5Random.nextInt(64) == 0) {
			var6 = par1World.getBlockMetadata(par2, par3, par4);

			if (var6 > 0 && var6 < 8) {
				par1World.playSound((double) ((float) par2 + 0.5F), (double) ((float) par3 + 0.5F), (double) ((float) par4 + 0.5F), "liquid.water",
						par5Random.nextFloat() * 0.25F + 0.75F, par5Random.nextFloat() * 1.0F + 0.5F, false);
			}
		}

		double var21;
		double var23;
		double var22;

		if (this.blockMaterial == Material.lava && par1World.getBlockMaterial(par2, par3 + 1, par4) == Material.air && !par1World.isBlockOpaqueCube(par2, par3 + 1, par4)) {
			if (par5Random.nextInt(100) == 0) {
				var21 = (double) ((float) par2 + par5Random.nextFloat());
				var22 = (double) par3 + this.maxY;
				var23 = (double) ((float) par4 + par5Random.nextFloat());
				par1World.spawnParticle("lava", var21, var22, var23, 0.0D, 0.0D, 0.0D);
				par1World.playSound(var21, var22, var23, "liquid.lavapop", 0.2F + par5Random.nextFloat() * 0.2F, 0.9F + par5Random.nextFloat() * 0.15F, false);
			}

			if (par5Random.nextInt(200) == 0) {
				par1World.playSound((double) par2, (double) par3, (double) par4, "liquid.lava", 0.2F + par5Random.nextFloat() * 0.2F, 0.9F + par5Random.nextFloat() * 0.15F, false);
			}
		}

		if (par5Random.nextInt(10) == 0 && par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !par1World.getBlockMaterial(par2, par3 - 2, par4).blocksMovement()) {
			var21 = (double) ((float) par2 + par5Random.nextFloat());
			var22 = (double) par3 - 1.05D;
			var23 = (double) ((float) par4 + par5Random.nextFloat());

			par1World.spawnParticle("dripLava", var21, var22, var23, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new LiquidTextureLogic();
	}

	public boolean isMetaSensitive() {
		return false;
	}

	public int stillLiquidMeta() {
		return 0;
	}
}
