package bstramke.NetherStuffs.SoulEngine;

import java.util.LinkedList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import bstramke.NetherStuffs.Blocks.NetherBlocks;
import bstramke.NetherStuffs.Common.NetherStuffsCore;
import buildcraft.api.core.Position;
import buildcraft.api.gates.IOverrideDefaultTriggers;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerFramework;
import buildcraft.api.power.PowerProvider;
import buildcraft.api.transport.IPipeConnection;

public class TileEngine extends TileEntity implements IPowerReceptor, IInventory, ITankContainer, IOverrideDefaultTriggers, IPipeConnection 
{
	private boolean init = false;
	Engine engine;
	int progressPart = 0;
	float serverPistonSpeed = 0;
	boolean isActive = false; // Used for SMP synch

	boolean lastPower = false;

	public int orientation;

	IPowerProvider provider;

	public boolean isRedstonePowered = false;

	public TileEngine() {
		provider = PowerFramework.currentFramework.createPowerProvider();
	}

	public void sendNetworkUpdate() {
		if (!worldObj.isRemote) {
			sendToPlayers(getUpdatePacket(), worldObj, xCoord, yCoord, zCoord, 128);
		}
	}
	
	public void sendToPlayers(Packet packet, World world, int x, int y, int z, int maxDistance) {
		if (packet != null) {
			for (int j = 0; j < world.playerEntities.size(); j++) {
				EntityPlayerMP player = (EntityPlayerMP) world.playerEntities.get(j);

				if (Math.abs(player.posX - x) <= maxDistance && Math.abs(player.posY - y) <= maxDistance && Math.abs(player.posZ - z) <= maxDistance) {
					player.playerNetServerHandler.sendPacketToPlayer(packet);
				}
			}
		}
	}
	
	public void initialize() {
		if (!worldObj.isRemote) {
			if (engine == null) {
				createEngineIfNeeded();
			}

			engine.orientation = ForgeDirection.VALID_DIRECTIONS[orientation];
			provider.configure(0, engine.minEnergyReceived(), engine.maxEnergyReceived(), 1, engine.maxEnergy);
			checkRedstonePower();
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (!init && !isInvalid()) {
			initialize();
			init = true;
		}

		if (this instanceof IPowerReceptor) {
			IPowerReceptor receptor = ((IPowerReceptor) this);

			receptor.getPowerProvider().update(receptor);
		}

		
		if (engine == null)
			return;

		if (worldObj.isRemote) {
			if (progressPart != 0) {
				engine.progress += serverPistonSpeed;

				if (engine.progress > 1) {
					progressPart = 0;
					engine.progress = 0;
				}
			} else if (this.isActive) {
				progressPart = 1;
			}

			return;
		}

		engine.update();

		float newPistonSpeed = engine.getPistonSpeed();
		if (newPistonSpeed != serverPistonSpeed) {
			serverPistonSpeed = newPistonSpeed;
			sendNetworkUpdate();
		}

		if (progressPart != 0) {
			engine.progress += engine.getPistonSpeed();

			if (engine.progress > 0.5 && progressPart == 1) {
				progressPart = 2;

				Position pos = new Position(xCoord, yCoord, zCoord, engine.orientation);
				pos.moveForwards(1.0);
				TileEntity tile = worldObj.getBlockTileEntity((int) pos.x, (int) pos.y, (int) pos.z);

				if (isPoweredTile(tile)) {
					IPowerProvider receptor = ((IPowerReceptor) tile).getPowerProvider();

					float extracted = engine.extractEnergy(receptor.getMinEnergyReceived(), receptor.getMaxEnergyReceived(), true);

					if (extracted > 0) {
						receptor.receiveEnergy(extracted, engine.orientation.getOpposite());
					}
				}
			} else if (engine.progress >= 1) {
				engine.progress = 0;
				progressPart = 0;
			}
		} else if (isRedstonePowered && engine.isActive()) {

			Position pos = new Position(xCoord, yCoord, zCoord, engine.orientation);
			pos.moveForwards(1.0);
			TileEntity tile = worldObj.getBlockTileEntity((int) pos.x, (int) pos.y, (int) pos.z);

			if (isPoweredTile(tile)) {
				IPowerProvider receptor = ((IPowerReceptor) tile).getPowerProvider();

				if (engine.extractEnergy(receptor.getMinEnergyReceived(), receptor.getMaxEnergyReceived(), false) > 0) {
					progressPart = 1;
					setActive(true);
				} else {
					setActive(false);
				}
			} else {
				setActive(false);
			}

		} else {
			setActive(false);
		}

		engine.burn();
	}

	private void setActive(boolean isActive) {
		if (this.isActive == isActive)
			return;

		this.isActive = isActive;
		sendNetworkUpdate();
	}

	private void createEngineIfNeeded() {
		if (engine == null) {
			int kind = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);

			engine = newEngine(kind);

			engine.orientation = ForgeDirection.VALID_DIRECTIONS[orientation];
			worldObj.notifyBlockChange(xCoord, yCoord, zCoord, NetherBlocks.SoulEngine.blockID);
		}
	}

	public void switchOrientation() {
		for (int i = orientation + 1; i <= orientation + 6; ++i) {
			ForgeDirection o = ForgeDirection.values()[i % 6];

			Position pos = new Position(xCoord, yCoord, zCoord, o);

			pos.moveForwards(1);

			TileEntity tile = worldObj.getBlockTileEntity((int) pos.x, (int) pos.y, (int) pos.z);

			if (isPoweredTile(tile)) {
				if (engine != null) {
					engine.orientation = o;
				}
				orientation = o.ordinal();
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, worldObj.getBlockId(xCoord, yCoord, zCoord));
				break;
			}
		}
	}

	public void delete() {
		if (engine != null) {
			engine.delete();
		}
	}

	public Engine newEngine(int meta) {
		return new EngineSoulEnergy(this);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);

		int kind = nbttagcompound.getInteger("kind");

		engine = newEngine(kind);

		orientation = nbttagcompound.getInteger("orientation");

		if (engine != null) {
			engine.progress = nbttagcompound.getFloat("progress");
			engine.energy = nbttagcompound.getFloat("energyF");
			engine.orientation = ForgeDirection.values()[orientation];
		}

		if (engine != null) {
			engine.readFromNBT(nbttagcompound);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);

		nbttagcompound.setInteger("kind", worldObj.getBlockMetadata(xCoord, yCoord, zCoord));

		if (engine != null) {
			nbttagcompound.setInteger("orientation", orientation);
			nbttagcompound.setFloat("progress", engine.progress);
			nbttagcompound.setFloat("energyF", engine.energy);
		}

		if (engine != null) {
			engine.writeToNBT(nbttagcompound);
		}
	}

	/* IINVENTORY IMPLEMENTATION */
	@Override
	public int getSizeInventory() {
		if (engine != null)
			return engine.getSizeInventory();
		else
			return 0;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (engine != null)
			return engine.getStackInSlot(i);
		else
			return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (engine != null)
			return engine.decrStackSize(i, j);
		else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (engine != null)
			return engine.getStackInSlotOnClosing(i);
		else
			return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		if (engine != null) {
			engine.setInventorySlotContents(i, itemstack);
		}
	}

	@Override
	public String getInvName() {
		return "Engine";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this;
	}

	/* STATE INFORMATION */
	public boolean isBurning() {
		return engine != null && engine.isBurning();
	}

	public int getScaledBurnTime(int i) {
		if (engine != null)
			return engine.getScaledBurnTime(i);
		else
			return 0;
	}

	/* SMP UPDATING */
	@Override
	public Packet getDescriptionPacket() {
		createEngineIfNeeded();

		return super.getDescriptionPacket();
	}

	@Override
	public Packet getUpdatePacket() {
		if (engine != null) {
			serverPistonSpeed = engine.getPistonSpeed();
		}

		return super.getUpdatePacket();
	}

	@Override
	public void handleDescriptionPacket(PacketUpdate packet) {
		createEngineIfNeeded();

		super.handleDescriptionPacket(packet);
	}

	@Override
	public void handleUpdatePacket(PacketUpdate packet) {
		createEngineIfNeeded();

		super.handleUpdatePacket(packet);
	}

	@Override
	public void setPowerProvider(IPowerProvider provider) {
		this.provider = provider;
	}

	@Override
	public IPowerProvider getPowerProvider() {
		return provider;
	}

	@Override
	public void doWork() {
		if (worldObj.isRemote)
			return;

		engine.addEnergy(provider.useEnergy(1, engine.maxEnergyReceived(), true) * 0.95F);
	}

	public boolean isPoweredTile(TileEntity tile) {
		if (tile instanceof IPowerReceptor) {
			IPowerProvider receptor = ((IPowerReceptor) tile).getPowerProvider();

			return receptor != null && receptor.getClass().getSuperclass().equals(PowerProvider.class);
		}

		return false;
	}

	@Override
	public void openChest() {

	}

	@Override
	public void closeChest() {

	}

	@Override
	public int powerRequest() {
		return 0;
	}

	public Engine getEngine() {
		return engine;
	}

	@Override
	public LinkedList<ITrigger> getTriggers() {
		LinkedList<ITrigger> triggers = new LinkedList<ITrigger>();

		triggers.add(NetherStuffsCore.triggerBlueEngineHeat);
		triggers.add(NetherStuffsCore.triggerGreenEngineHeat);
		triggers.add(NetherStuffsCore.triggerYellowEngineHeat);
		triggers.add(NetherStuffsCore.triggerRedEngineHeat);

		triggers.add(NetherStuffsCore.triggerEmptyLiquid);
		triggers.add(NetherStuffsCore.triggerContainsLiquid);
		triggers.add(NetherStuffsCore.triggerSpaceLiquid);
		triggers.add(NetherStuffsCore.triggerFullLiquid);
		
		return triggers;
	}

	@Override
	public boolean isPipeConnected(ForgeDirection with) {

		return with.ordinal() != orientation;
	}

	public void checkRedstonePower() {
		isRedstonePowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}

	/* ILIQUIDCONTAINER */
	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {
		if (engine == null)
			return 0;
		return engine.fill(from, resource, doFill);
	}

	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public LiquidTank[] getTanks(ForgeDirection direction) {
		if (engine == null)
			return new LiquidTank[0];
		else
			return engine.getLiquidSlots();
	}

	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type) {
		return engine != null ? engine.getTank(direction, type) : null;
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return false;
	}
}