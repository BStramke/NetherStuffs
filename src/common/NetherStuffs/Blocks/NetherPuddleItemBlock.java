package NetherStuffs.Blocks;

import NetherStuffs.Items.NetherItems;
import NetherStuffs.Items.NetherStonePotionBowl;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class NetherPuddleItemBlock extends ItemBlock {
	public static String[] blockNames = new String[] { "Hellfire", "Acid", "Death" };
	public static String[] blockDisplayNames = new String[] { "Hellfire Puddle", "Acid Puddle", "Death Puddle" };

	public NetherPuddleItemBlock(int par1) {
		super(par1);
		setHasSubtypes(true);
	}

	public static int getMetadataSize() {
		return blockNames.length;
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Gets an icon index based on an item's damage value
	 */
	@Override
	public int getIconFromDamage(int par1) {
		return NetherBlocks.netherPuddle.getBlockTextureFromSideAndMetadata(NetherBlocks.sideTop, par1);
	}

	@Override
	public String getItemNameIS(ItemStack is) {
		String name = "";
		if (is.getItemDamage() < getMetadataSize() && is.getItemDamage() >= 0)
			name = blockNames[is.getItemDamage()];
		else
			name = blockNames[0];

		return getItemName() + "." + name;
	}

	@Override
	public String getTextureFile() {
		return "/puddles.png";
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);
		if (var4 == null) {
			return par1ItemStack;
		} else {
			if (var4.typeOfHit == EnumMovingObjectType.TILE) {
				int var5 = var4.blockX;
				int var6 = var4.blockY;
				int var7 = var4.blockZ;

				if (par2World.getBlockId(var5, var6, var7) == NetherBlocks.netherPuddle.blockID && NetherPuddle.getSizeFromMetadata(par2World.getBlockMetadata(var5, var6, var7)) < 3) {
					int nUsedMetadata = par1ItemStack.getItemDamage();
					if (nUsedMetadata != NetherPuddle.unmarkedMetadata(par2World.getBlockMetadata(var5, var6, var7)))
						return par1ItemStack;
					--par1ItemStack.stackSize;
					NetherPuddle.growPuddle(par2World, var5, var6, var7);
				}
			}

			return par1ItemStack;
		}
	}

}