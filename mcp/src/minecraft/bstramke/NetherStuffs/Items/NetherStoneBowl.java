package bstramke.NetherStuffs.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.Wood;
import bstramke.NetherStuffs.Blocks.puddles.TileNetherWoodPuddle;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherstoneBowl extends Item {
	public static String[] itemDisplayNames = new String[] { "Netherstone Bowl" };

	public NetherstoneBowl(int par1) {
		super(par1);
		this.setCreativeTab(NetherStuffs.tabNetherStuffs);
		setUnlocalizedName("NetherStoneBowl");
	}

	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon(CommonProxy.getIconLocation("NetherStoneBowl"));
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
						int bowlMetaData = 0;
						switch (meta) {
						case Wood.hellfire:
							bowlMetaData = PotionBottle.hellfire;
							break;
						case Wood.acid:
							bowlMetaData = PotionBottle.acid;
							break;
						case Wood.death:
							bowlMetaData = PotionBottle.death;
							break;
						default:
							return par1ItemStack; // --> as this means its a unknown type, exit
						}
						
						--par1ItemStack.stackSize;
						if (par1ItemStack.stackSize <= 0) {
							return new ItemStack(ItemRegistry.NetherStonePotionBowl.itemID, 1, bowlMetaData);
						}

						if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(ItemRegistry.NetherStonePotionBowl.itemID, 1, bowlMetaData))) {
							par3EntityPlayer.dropPlayerItem(new ItemStack(ItemRegistry.NetherStonePotionBowl.itemID, 1, bowlMetaData));
						}
					}
				}
			}

			return par1ItemStack;
		}
	}

	@Override
	public String getItemDisplayName(ItemStack is) {
		if (is.getItemDamage() < itemDisplayNames.length && is.getItemDamage() >= 0)
			return itemDisplayNames[is.getItemDamage()];
		else
			return  itemDisplayNames[0];
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < itemDisplayNames.length; metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}
