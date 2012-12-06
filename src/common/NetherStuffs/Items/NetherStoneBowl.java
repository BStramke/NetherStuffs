package NetherStuffs.Items;

import java.util.List;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.World;
import NetherStuffs.Blocks.NetherBlocks;
import NetherStuffs.Blocks.NetherPuddle;
import NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class NetherStoneBowl extends Item {
	public static String[] itemNames = new String[] { "NetherstoneBowl" };
	public static String[] itemDisplayNames = new String[] { "Netherstone Bowl" };

	public NetherStoneBowl(int par1) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabBrewing);
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.ITEMS_PNG;
	}

	@Override
	public int getIconFromDamage(int par1) {
		return 19;
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

				if (par2World.getBlockId(var5, var6, var7) == NetherBlocks.netherPuddle.blockID && NetherPuddle.getSizeFromMetadata(par2World.getBlockMetadata(var5, var6, var7)) == 3) {
					int metadata = NetherPuddle.unmarkedMetadata(par2World.getBlockMetadata(var5, var6, var7));
					int bowlMetaData = 0;
					switch (metadata) {
					case NetherPuddle.hellfire:
						bowlMetaData = NetherStonePotionBowl.hellfire;
						break;
					case NetherPuddle.acid:
						bowlMetaData = NetherStonePotionBowl.acid;
						break;
					case NetherPuddle.death:
						bowlMetaData = NetherStonePotionBowl.death;
						break;
					default:
						return par1ItemStack; // --> as this means its a unknown type, exit
					}
					--par1ItemStack.stackSize;
					NetherPuddle.removePuddle(par2World, var5, var6, var7);

					if (par1ItemStack.stackSize <= 0) {
						return new ItemStack(NetherItems.NetherStonePotionBowl.shiftedIndex, 1, bowlMetaData);
					}

					if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(NetherItems.NetherStonePotionBowl.shiftedIndex, 1, bowlMetaData))) {
						par3EntityPlayer.dropPlayerItem(new ItemStack(NetherItems.NetherStonePotionBowl.shiftedIndex, 1, bowlMetaData));
					}
				}
			}

			return par1ItemStack;
		}
	}

	public static int getMetadataSize() {
		return itemNames.length;
	}

	@Override
	public String getItemNameIS(ItemStack is) {
		String name = "";
		if (is.getItemDamage() < getMetadataSize() && is.getItemDamage() >= 0)
			name = itemNames[is.getItemDamage()];
		else
			name = itemNames[0];

		return getItemName() + "." + name;
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}
