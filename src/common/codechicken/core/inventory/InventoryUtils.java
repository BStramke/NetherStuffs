package codechicken.core.inventory;

import com.google.common.base.Objects;

import codechicken.core.Vector3;
import codechicken.nei.PositionedStack;
import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryLargeChest;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.World;
import net.minecraftforge.common.ForgeDirection;

public class InventoryUtils
{
	public static ItemStack decrStackSize(IInventory inv, int slot, int size)
	{
		ItemStack item = inv.getStackInSlot(slot);    	
    	
        if(item != null)
        {
            if(item.stackSize <= size)
            {
                ItemStack itemstack = item;
                inv.setInventorySlotContents(slot, null);
                inv.onInventoryChanged();
                return itemstack;
            }
            ItemStack itemstack1 = item.splitStack(size);
            if(item.stackSize == 0)
            {
            	inv.setInventorySlotContents(slot, null);
            }
            inv.onInventoryChanged();
            return itemstack1;
        } 
        else
        {
            return null;
        }
	}

	public static ItemStack getStackInSlotOnClosing(IInventory inv, int slot)
	{
		ItemStack stack = inv.getStackInSlot(slot);
		inv.setInventorySlotContents(slot, null);
		return stack;
	}

	/**
	 * 
	 * @param base
	 * @param addition
	 * @return The quantity of items from addition that can be added to base
	 */
	public static int incrStackSize(ItemStack base, ItemStack addition)
	{
		if (canStack(base, addition))
        {
            return incrStackSize(base, addition.stackSize);
        }
		
		return 0;
	}
	
	/**
     * 
     * @param base
     * @param addition
     * @return The quantity of items from addition that can be added to base
     */
    public static int incrStackSize(ItemStack base, int addition)
    {
        int totalSize = base.stackSize + addition;

        if (totalSize <= base.getMaxStackSize())
        {
            return addition;
        }
        else if (base.stackSize < base.getMaxStackSize())
        {
            return base.getMaxStackSize() - base.stackSize;
        }
        
        return 0;
    }

	public static NBTTagList writeItemStacksToTag(ItemStack[] items)
	{
		NBTTagList tagList = new NBTTagList();
		for(int i = 0; i < items.length; i++)
		{
			if (items[i] != null)
            {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setShort("Slot", (short) i);
                items[i].writeToNBT(tag);
                tagList.appendTag(tag);
            }
		}
		return tagList;
	}
	
	public static void readItemStacksFromTag(ItemStack[] items, NBTTagList tagList)
	{
		for(int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			items[tag.getShort("Slot")] = ItemStack.loadItemStackFromNBT(tag);
		}
	}

	public static void dropItem(ItemStack stack, World world, Vector3 dropLocation)
	{
		EntityItem item = new EntityItem(world, dropLocation.x, dropLocation.y, dropLocation.z, stack);
        item.motionX = world.rand.nextGaussian() * 0.05;
        item.motionY = world.rand.nextGaussian() * 0.05 + 0.2F;
        item.motionZ = world.rand.nextGaussian() * 0.05;
        world.spawnEntityInWorld(item);
	}

    public static ItemStack copyStack(ItemStack stack, int quantity)
    {
        stack = stack.copy();
        stack.stackSize = quantity;
        return stack;
    }
    
    /**
     * {@link ItemStack}s with damage -1 are wildcards allowing all damages. Eg all colours of wool are allowed to create Beds.
     * @param stack1 The {@link ItemStack} being compared.
     * @param stack2 The {@link ItemStack} to compare to.
     * @return whether the two items are the same from the perspective of a crafting inventory.
     */
    public static boolean areStacksSameTypeCrafting(ItemStack stack1, ItemStack stack2)
    {
        if(stack1 == null || stack2 == null)
            return false;
        
        return stack1.itemID == stack2.itemID && 
                (stack1.getItemDamage() == stack2.getItemDamage() || stack1.getItemDamage() == -1 || stack2.getItemDamage() == -1 || stack1.getItem().isDamageable());
    }

    public static boolean mergeItemStack(IInventory inv, int fslot, int lslot, ItemStack stack, boolean doMerge)
    {
        if(doMerge && !mergeItemStack(inv, fslot, lslot, stack, false))
            return false;
        
        stack = stack.copy();
        for(int slot = fslot; slot < lslot; slot++)
        {
            ItemStack base = inv.getStackInSlot(slot);
            if(base == null)
            {
                int quantity = Math.min(inv.getInventoryStackLimit(), stack.stackSize);
                if(doMerge)
                    inv.setInventorySlotContents(slot, copyStack(stack, quantity));
                stack.stackSize-=quantity;
            }
            else
            {
                int added = incrStackSize(base, stack);
                if(added == 0)
                    continue;
                stack.stackSize-=added;
                if(doMerge)
                {
                    base.stackSize+=added;
                    inv.setInventorySlotContents(slot, base);
                }
            }
            if(stack.stackSize == 0)
                return true;
        }
        return false;
    }

    public static boolean areStacksIdentical(ItemStack stack1, ItemStack stack2)
    {
        if(stack1 == null || stack2 == null)
            return stack1 == stack2;
        return stack1.itemID == stack2.itemID
                && stack1.getItemDamage() == stack2.getItemDamage()
                && stack1.stackSize == stack2.stackSize
                && Objects.equal(stack1.getTagCompound(), stack2.getTagCompound());
    }
    
    public static IInventory getInventory(World world, int x, int y, int z)
    {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if(!(tile instanceof IInventory))
            return null;
        
        if(tile instanceof TileEntityChest)
            return getChest((TileEntityChest) tile);
        return (IInventory)tile;
        
    }
    
    public static final ForgeDirection[] chestSides = new ForgeDirection[]{ForgeDirection.WEST, ForgeDirection.EAST, ForgeDirection.NORTH, ForgeDirection.SOUTH};
    public static IInventory getChest(TileEntityChest chest)
    {
        for(ForgeDirection fside : chestSides)
        {
            if(chest.worldObj.getBlockId(chest.xCoord+fside.offsetX, chest.yCoord+fside.offsetY, chest.zCoord+fside.offsetZ) == chest.getBlockType().blockID)
                return new InventoryLargeChest("container.chestDouble", 
                        (TileEntityChest)chest.worldObj.getBlockTileEntity(chest.xCoord+fside.offsetX, chest.yCoord+fside.offsetY, chest.zCoord+fside.offsetZ), chest);
        }
        return chest;
    }

    public static boolean canStack(ItemStack stack1, ItemStack stack2)
    {
        return stack1 != null && stack2 != null && stack1.itemID == stack2.itemID && (!stack2.getHasSubtypes() || stack2.getItemDamage() == stack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(stack2, stack1);
    }
}
