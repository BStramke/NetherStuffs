package bstramke.NetherStuffs.Blocks.puddles;

import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Blocks.BlockContainerBase;
import bstramke.NetherStuffs.Blocks.BlockRegistry;
import bstramke.NetherStuffs.Blocks.Wood;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Common.Materials.NetherMaterials;
import bstramke.NetherStuffs.Items.ItemRegistry;
import bstramke.NetherStuffs.Items.PotionBottle;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherWoodPuddle extends BlockContainerBase {
	private Icon icoHellfireWoodPuddles[];
	private Icon icoAcidWoodPuddles[];
	private Icon icoDeathWoodPuddles[];

	public NetherWoodPuddle(int par1) {
		super(par1, NetherMaterials.netherWood);
		setStepSound(soundWoodFootstep);
		setTickRandomly(true);
		setBurnProperties(this.blockID, 0, 0);
		setUnlocalizedName("NetherWoodPuddle");
		for (int i = 0; i < NetherWoodPuddleItemBlock.getMetadataSize(); i++) {
			LanguageRegistry.instance().addStringLocalization("tile.NetherWoodPuddle." + NetherWoodPuddleItemBlock.blockNames[i] + ".name",
					NetherWoodPuddleItemBlock.blockDisplayNames[i]);
		}
	}

	@Override
	public void updateTick(World par1World, int x, int y, int z, Random par5Random) {
		if (par1World.isRemote)
			return;

		if (par1World.provider.isHellWorld == false)
			return;

		TileEntity tile = par1World.getBlockTileEntity(x, y, z);
		if (tile instanceof TileNetherWoodPuddle) {
			((TileNetherWoodPuddle) tile).growPuddle(par5Random); // as this is random Ticked, it takes averagely 68 seconds to update (source: minecraft wiki)
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		icoHellfireWoodPuddles = new Icon[5];
		icoAcidWoodPuddles = new Icon[5];
		icoDeathWoodPuddles = new Icon[5];

		for (Integer i = 0; i < icoHellfireWoodPuddles.length; i++) {
			icoHellfireWoodPuddles[i] = par1IconRegister.registerIcon(CommonProxy.getIconLocation("WoodHellfirePuddle_" + i));
			icoAcidWoodPuddles[i] = par1IconRegister.registerIcon(CommonProxy.getIconLocation("WoodAcidPuddle_" + i));
			icoDeathWoodPuddles[i] = par1IconRegister.registerIcon(CommonProxy.getIconLocation("WoodDeathPuddle_" + i));
		}
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);

		if(tile_entity instanceof TileNetherWoodPuddle)
		{
			((TileNetherWoodPuddle)tile_entity).setPuddleDirection(world.rand);
		}
		
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int x, int y, int z, int side) {
		int meta = par1IBlockAccess.getBlockMetadata(x, y, z);
		int type = meta & 3;
		Icon textureIndex = getIcon(side, meta);

		TileEntity tile = par1IBlockAccess.getBlockTileEntity(x, y, z);
		if (tile instanceof TileNetherWoodPuddle) {
			int orientation = meta & 12;
			int puddleSize = ((TileNetherWoodPuddle) tile).puddleSize;

			boolean bDisplayPuddle = false;

			switch (orientation) {
			case 0:
				if (ForgeDirection.getOrientation(side) == ForgeDirection.NORTH)
					bDisplayPuddle = true;
				break;
			case 4:
				if (ForgeDirection.getOrientation(side) == ForgeDirection.SOUTH)
					bDisplayPuddle = true;
				break;
			case 8:
				if (ForgeDirection.getOrientation(side) == ForgeDirection.WEST)
					bDisplayPuddle = true;
				break;
			case 12:
				if (ForgeDirection.getOrientation(side) == ForgeDirection.EAST)
					bDisplayPuddle = true;
				break;
			}

			if (bDisplayPuddle == true) {
				switch (type) {
				case Wood.hellfire:
					return icoHellfireWoodPuddles[puddleSize];
				case Wood.acid:
					return icoAcidWoodPuddles[puddleSize];
				case Wood.death:
					return icoDeathWoodPuddles[puddleSize];
				default:
					return icoHellfireWoodPuddles[puddleSize];
				}
			}
		}
		return textureIndex;
	}

	@Override
	public Icon getIcon(int side, int meta) {
		meta = meta & 3; // remove any puddle orientation info
		if (side == BlockRegistry.sideBottom || side == BlockRegistry.sideTop)
			return blocksList[NetherStuffs.NetherWoodBlockId].getIcon(side, meta);
		else
			return blocksList[NetherStuffs.NetherWoodBlockId].getIcon(BlockRegistry.sideEast, meta);
	};

	@Override
	public int damageDropped(int meta) {
		return meta & 3;
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return NetherStuffs.NetherWoodBlockId;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileNetherWoodPuddle();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g, float t) {
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);

		if (tile_entity == null || player.isSneaking()) {
			return false;
		}

		if(tile_entity instanceof TileNetherWoodPuddle)
		{
			ItemStack heldItemStack = player.getHeldItem();
			if(heldItemStack == null)
				return false;
			
			if(heldItemStack.isItemEqual(new ItemStack(ItemRegistry.NetherSoulGlassBottleItem, 1, 0)) == false)
				return false;
						
			if (((TileNetherWoodPuddle)tile_entity).harvestPuddle()) {
				int meta = world.getBlockMetadata(x, y, z) & 3;
				int bottleMetaData = 0;
				switch (meta) {
				case Wood.hellfire:
					bottleMetaData = PotionBottle.hellfire;
					break;
				case Wood.acid:
					bottleMetaData = PotionBottle.acid;
					break;
				case Wood.death:
					bottleMetaData = PotionBottle.death;
					break;
				default:
					return false; // --> as this means its a unknown type, exit
				}
				
				--heldItemStack.stackSize;
				
				if (!player.inventory.addItemStackToInventory(new ItemStack(ItemRegistry.NetherPotionBottle.itemID, 1, bottleMetaData))) {
					player.dropPlayerItem(new ItemStack(ItemRegistry.NetherPotionBottle.itemID, 1, bottleMetaData));
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < NetherWoodPuddleItemBlock.getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}
