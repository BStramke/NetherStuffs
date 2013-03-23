package bstramke.NetherStuffs.Blocks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import bstramke.NetherStuffs.NetherStuffs;
import bstramke.NetherStuffs.Common.CommonProxy;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherSoulGlassPane extends BlockPane {

	public NetherSoulGlassPane(int par1, Material par4Material, boolean par5) {
		super(par1, CommonProxy.getIconLocation("SoulGlass"),  CommonProxy.getIconLocation("SoulGlassTop"), par4Material, par5);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		Block.opaqueCubeLookup[this.blockID] = true;
		Block.opaqueCubeLookup[Block.thinGlass.blockID] = true;

		try {
			Class[] args = new Class[1];
			args[0] = int.class;
			Method m = BlockPane.class.getDeclaredMethod("addToConnectList", args);
			m.invoke(null, this.blockID);
		} catch (NoSuchMethodException e) {

		} catch (SecurityException e) {

		} catch (IllegalAccessException e) {

		} catch (IllegalArgumentException e) {

		} catch (InvocationTargetException e) {

		}
	}
	
	/*private boolean canThisPaneConnectToThisBlockID_2(int par1) {
		return Block.opaqueCubeLookup[par1] || par1 == this.blockID || par1 == Block.glass.blockID || ;
	}

	public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
   {
       boolean flag = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2, par3, par4 - 1));
       boolean flag1 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2, par3, par4 + 1));
       boolean flag2 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2 - 1, par3, par4));
       boolean flag3 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2 + 1, par3, par4));

       if ((!flag2 || !flag3) && (flag2 || flag3 || flag || flag1))
       {
           if (flag2 && !flag3)
           {
               this.setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
               super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
           }
           else if (!flag2 && flag3)
           {
               this.setBlockBounds(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
               super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
           }
       }
       else
       {
           this.setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
           super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
       }

       if ((!flag || !flag1) && (flag2 || flag3 || flag || flag1))
       {
           if (flag && !flag1)
           {
               this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
               super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
           }
           else if (!flag && flag1)
           {
               this.setBlockBounds(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
               super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
           }
       }
       else
       {
           this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
           super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
       }
   }*/
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		list.add(new ItemStack(par1, 1, 0));
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		return true;
	}
}
