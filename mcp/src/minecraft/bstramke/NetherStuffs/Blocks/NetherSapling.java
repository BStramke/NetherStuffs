package bstramke.NetherStuffs.Blocks;

import static net.minecraftforge.common.EnumPlantType.Cave;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.WorldGen.WorldGenNetherStuffsTrees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherSapling extends BlockSapling /* implements IPlantable */{

	private static final int METADATA_BITMASK = 0x7;
	private static final int METADATA_MARKBIT = 0x8;

	private static boolean isMarkedMetadata(int metadata) {
		return (metadata & METADATA_MARKBIT) != 0;
	}

	private static int markedMetadata(int metadata) {
		return metadata | METADATA_MARKBIT;
	}

	private static int unmarkedMetadata(int metadata) {
		return metadata & METADATA_BITMASK;
	}

	public static final int hellfire = 0;
	public static final int acid = 1;
	public static final int death = 2;
	private Icon icoSaplingHellfire;
	private Icon icoSaplingAcid;
	private Icon icoSaplingDeath;

	public NetherSapling(int par1) {
		super(par1);
		this.setRequiresSelfNotify();
		this.setStepSound(soundGrassFootstep);
	}

	@Override
	public int damageDropped(int meta) {
		return unmarkedMetadata(meta);
	}

	@Override
	public EnumPlantType getPlantType(World world, int x, int y, int z) {
		return Cave;
	}

	@Override
	public void func_94332_a(IconRegister par1IconRegister)
	{
		icoSaplingHellfire = par1IconRegister.func_94245_a(CommonProxy.getIconLocation("SaplingHellfire"));
		icoSaplingAcid = par1IconRegister.func_94245_a(CommonProxy.getIconLocation("SaplingAcid"));
		icoSaplingDeath = par1IconRegister.func_94245_a(CommonProxy.getIconLocation("SaplingDeath"));		 
	}
	
	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	@Override
	public Icon getBlockTextureFromSideAndMetadata(int side, int meta) {
		switch (unmarkedMetadata(meta)) {
		case hellfire:
			return icoSaplingHellfire;
		case acid:
			return icoSaplingAcid;
		case death:
			return icoSaplingDeath;
		default:
			return icoSaplingHellfire;
		}
	}

	/**
	 * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of blockID passed in. Args: blockID
	 */
	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int par1) {
		return par1 == Block.netherrack.blockID;
	}

	/**
	 * Can this block stay at this position. Similar to canPlaceBlockAt except gets checked often with plants.
	 */
	@Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
		if (par1World.provider.isHellWorld)
			return this.canThisPlantGrowOnThisBlockID(par1World.getBlockId(par2, par3 - 1, par4));
		else
			return false;
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int sideClicked, float par7, float par8, float par9) {

		if (par5EntityPlayer.getHeldItem() != null && par5EntityPlayer.getHeldItem().getItemDamage() == 15 && par1World.getBlockId(par2, par3, par4) == this.blockID) {
			par5EntityPlayer.getHeldItem().stackSize--;
			fertilize(par1World, par2, par3, par4);
		}
		return false;
	}

	public void fertilize(World par1World, int par2, int par3, int par4) {
		Random random = new Random();
		growTree(par1World, par2, par3, par4, random);
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (!par1World.isRemote) {
			super.updateTick(par1World, par2, par3, par4, par5Random);

			if (par5Random.nextInt(7) == 0) {
				int var6 = par1World.getBlockMetadata(par2, par3, par4);

				if (!isMarkedMetadata(var6)) {
					par1World.setBlockMetadataWithNotify(par2, par3, par4, markedMetadata(var6));
				} else {
					this.growTree(par1World, par2, par3, par4, par5Random);
				}
			}
		}
	}

	/**
	 * Attempts to grow a sapling into a tree
	 */
	@Override
	public void growTree(World par1World, int par2, int par3, int par4, Random par5Random) {
		int meta = unmarkedMetadata(par1World.getBlockMetadata(par2, par3, par4));

		par1World.setBlock(par2, par3, par4, 0);
		if ((new WorldGenNetherStuffsTrees(true, meta)).generate(par1World, par5Random, par2, par3, par4)) {
			par1World.setBlockAndMetadata(par2, par3, par4, NetherStuffs.NetherWoodBlockId, meta);
		}
	}

	public int getMetadataSize() {
		return NetherSaplingItemBlock.blockNames.length;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}
