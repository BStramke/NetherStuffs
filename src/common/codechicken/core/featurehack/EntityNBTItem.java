package codechicken.core.featurehack;

import java.io.IOException;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityNBTItem extends EntityItem implements IEntityAdditionalSpawnData
{
	public EntityNBTItem(World world)
	{
		super(world);
	}

	private EntityNBTItem(World world, double x, double y, double z, ItemStack stack)
	{
		super(world, x, y, z, stack);
	}

	public static boolean hasCustomEntity(ItemStack stack)
	{
		return stack.getTagCompound() != null;
	}

	public static Entity createEntity(World world, Entity location, ItemStack itemstack)
	{
		if(location instanceof EntityNBTItem)
			return null;
		
		EntityItem newItem = new EntityNBTItem(world, location.posX, location.posY, location.posZ, itemstack);
		newItem.delayBeforeCanPickup = ((EntityItem)location).delayBeforeCanPickup;
		newItem.motionX = location.motionX;
		newItem.motionY = location.motionY;
		newItem.motionZ = location.motionZ;
		return newItem;
	}

	@Override
	public void writeSpawnData(ByteArrayDataOutput data)
	{
		try
		{
			data.writeShort(item.itemID);
			data.writeByte(item.stackSize);
			data.writeShort(item.getItemDamage());
			NBTBase.writeNamedTag(item.getTagCompound(), data);
		}
		catch(IOException e)
		{
			FMLCommonHandler.instance().raiseException(e, "Error creating EntityNBTItem", true);
		}		
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data)
	{
		try
		{
			item = new ItemStack(data.readShort(), data.readByte(), data.readShort());
			item.setTagCompound((NBTTagCompound) NBTBase.readNamedTag(data));
		}
		catch(IOException e)
		{
			FMLCommonHandler.instance().raiseException(e, "Error creating EntityNBTItem", true);
		}
	}
}
