package bstramke.NetherStuffs.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import bstramke.NetherStuffs.Blocks.NetherBlocks;
import bstramke.NetherStuffs.Blocks.NetherWood;
import bstramke.NetherStuffs.Blocks.NetherWoodPuddle;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.NetherWoodPuddle.TileNetherWoodPuddle;
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
	public void func_94581_a(IconRegister iconRegister)
	{
		iconIndex = iconRegister.func_94245_a(CommonProxy.getIconLocation("NetherSoulglassBottle"));
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

				TileEntity tile = par2World.getBlockTileEntity(var5, var6, var7);
				if(tile instanceof TileNetherWoodPuddle)
				{
					if (((TileNetherWoodPuddle)tile).harvestPuddle()) {
						int meta = par2World.getBlockMetadata(var5, var6, var7) & 3;
						int bottleMetaData = 0;
						switch (meta) {
						case NetherWood.hellfire:
							bottleMetaData = NetherPotionBottle.hellfire;
							break;
						case NetherWood.acid:
							bottleMetaData = NetherPotionBottle.acid;
							break;
						case NetherWood.death:
							bottleMetaData = NetherPotionBottle.death;
							break;
						default:
							return par1ItemStack; // --> as this means its a unknown type, exit
						}
						
						--par1ItemStack.stackSize;
						if (par1ItemStack.stackSize <= 0) {
							return new ItemStack(NetherItems.NetherPotionBottle.itemID, 1, bottleMetaData);
						}
						
						if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(NetherItems.NetherPotionBottle.itemID, 1, bottleMetaData))) {
							par3EntityPlayer.dropPlayerItem(new ItemStack(NetherItems.NetherPotionBottle.itemID, 1, bottleMetaData));
						}
					}
				}
			}

			return par1ItemStack;
		}
	}
}
