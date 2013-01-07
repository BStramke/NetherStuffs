package bstramke.NetherStuffs.Items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import bstramke.NetherStuffs.Blocks.NetherBlocks;
import bstramke.NetherStuffs.Blocks.NetherPuddle;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherSoulGlassBottle extends ItemGlassBottle {
	public static String[] itemNames = new String[] { "SoulglassBottle" };
	public static String[] itemDisplayNames = new String[] { "Soulglass Bottle" };

	public NetherSoulGlassBottle(int par1) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabBrewing);
		// this.setHasSubtypes(true);
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.ITEMS_PNG;
	}

	@Override
	public int getIconFromDamage(int par1) {
		return 16;
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
					int bottleMetaData = 0;
					switch (metadata) {
					case NetherPuddle.hellfire:
						bottleMetaData = NetherPotionBottle.hellfire;
						break;
					case NetherPuddle.acid:
						bottleMetaData = NetherPotionBottle.acid;
						break;
					case NetherPuddle.death:
						bottleMetaData = NetherPotionBottle.death;
						break;
					default:
						return par1ItemStack; // --> as this means its a unknown type, exit
					}
					--par1ItemStack.stackSize;
					NetherPuddle.removePuddle(par2World, var5, var6, var7);

					if (par1ItemStack.stackSize <= 0) {
						return new ItemStack(NetherItems.NetherPotionBottle.itemID, 1, bottleMetaData);
					}

					if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(NetherItems.NetherPotionBottle.itemID, 1, bottleMetaData))) {
						par3EntityPlayer.dropPlayerItem(new ItemStack(NetherItems.NetherPotionBottle.itemID, 1, bottleMetaData));
					}
				}
			}

			return par1ItemStack;
		}
	}
}
