package bstramke.NetherStuffs.Blocks;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Random;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.BlockContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Client.ClientPacketHandler.PacketType;
import bstramke.NetherStuffs.Common.CommonProxy;
import bstramke.NetherStuffs.Common.NetherWoodMaterial;
import bstramke.NetherStuffs.Common.ServerPacketHandler;
import bstramke.NetherStuffs.NetherWoodPuddle.TileNetherWoodPuddle;

public class NetherWoodPuddle extends BlockContainer {
	
	public NetherWoodPuddle(int par1, int par2) {
		super(par1, par2, NetherWoodMaterial.netherWood);
		this.setStepSound(soundWoodFootstep);
		this.setRequiresSelfNotify();
		this.setTickRandomly(true);
		this.setBurnProperties(this.blockID, 0, 0);
	}
	
	@Override
	public String getTextureFile() {
		return CommonProxy.BLOCKS_PNG;
	}
		
	@Override
	public void updateTick(World par1World, int x, int y, int z, Random par5Random) {
		if (par1World.isRemote)
			return;
		
		if(par1World.provider.isHellWorld == false)
			return;
		
		TileEntity tile = par1World.getBlockTileEntity(x, y, z);			
		if(tile instanceof TileNetherWoodPuddle)
		{
			((TileNetherWoodPuddle) tile).growPuddle(par5Random); //as this is random Ticked, it takes averagely 68 seconds to update (source: minecraft wiki)
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBlockTexture(IBlockAccess par1IBlockAccess, int x, int y, int z, int side) {
		int meta = par1IBlockAccess.getBlockMetadata(x, y, z);
		int textureIndex = getBlockTextureFromSideAndMetadata(side, meta);
	
		TileEntity tile = par1IBlockAccess.getBlockTileEntity(x, y, z);
		if(tile instanceof TileNetherWoodPuddle) {
			int orientation = meta & 12;
			int puddleSize = ((TileNetherWoodPuddle)tile).puddleSize;
			
			if(puddleSize == -1) //uninitialized puddle size --> query the server for the size
			{
				queryPuddleSizeFromServer(x, y, z);
				return textureIndex;
			}
			
			switch(orientation)
			{
				case 0:
					if(ForgeDirection.getOrientation(side) == ForgeDirection.NORTH)
					{
						textureIndex += 3;
						textureIndex += puddleSize * 3;
						if(puddleSize == 4)
							textureIndex += 12; //this is located in the next row
					}
					break;
				case 4:
					if(ForgeDirection.getOrientation(side) == ForgeDirection.SOUTH)
					{
						textureIndex += 3;
						textureIndex += puddleSize * 3;
						if(puddleSize == 4)
							textureIndex += 13; //this is located in the next row
					}
					break;
				case 8:
					if(ForgeDirection.getOrientation(side) == ForgeDirection.WEST)
					{
						textureIndex += 3;
						textureIndex += puddleSize * 3;
						if(puddleSize == 4)
							textureIndex += 13; //this is located in the next row
					}
					break;
				case 12:
					if(ForgeDirection.getOrientation(side) == ForgeDirection.EAST)
					{
						textureIndex += 3;
						textureIndex += puddleSize * 3;
						if(puddleSize == 4)
							textureIndex += 13; //this is located in the next row
					}
					break;
			}
		}
		return textureIndex;
	}
	
	@SideOnly(Side.CLIENT)
	private void queryPuddleSizeFromServer(int x, int y, int z) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(bos);

		try {
			outputStream.writeShort(ServerPacketHandler.PacketType.NetherWoodPuddleSizeQuery.getValue());
			outputStream.writeInt(x);
			outputStream.writeInt(y);
			outputStream.writeInt(z);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "NetherStuffs";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToServer(packet);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		meta = meta & 3; //remove any orientation info
		return blocksList[NetherStuffs.NetherWoodBlockId].getBlockTextureFromSideAndMetadata(2, meta);
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
