package bstramke.NetherStuffs.Blocks;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Random;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.BlockContainer;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Client.ClientPacketHandler.PacketType;
import bstramke.NetherStuffs.Common.BlockNotifyType;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Common.NetherWoodMaterial;
import bstramke.NetherStuffs.Common.ServerPacketHandler;
import bstramke.NetherStuffs.NetherWoodPuddle.TileNetherWoodPuddle;

public class NetherWoodPuddle extends BlockContainer {
	private Icon icoHellfireWoodPuddles[];
	private Icon icoAcidWoodPuddles[];
	private Icon icoDeathWoodPuddles[];

	public NetherWoodPuddle(int par1) {
		super(par1, NetherWoodMaterial.netherWood);
		this.setStepSound(soundWoodFootstep);
		// this.setRequiresSelfNotify();
		this.setTickRandomly(true);
		this.setBurnProperties(this.blockID, 0, 0);
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
				case NetherWood.hellfire:
					return icoHellfireWoodPuddles[puddleSize];
				case NetherWood.acid:
					return icoAcidWoodPuddles[puddleSize];
				case NetherWood.death:
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
		if (side == NetherBlocks.sideBottom || side == NetherBlocks.sideTop)
			return blocksList[NetherStuffs.NetherWoodBlockId].getIcon(side, meta);
		else
			return blocksList[NetherStuffs.NetherWoodBlockId].getIcon(NetherBlocks.sideEast, meta);
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
}
