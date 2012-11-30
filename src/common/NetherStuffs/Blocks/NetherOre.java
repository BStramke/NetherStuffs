package NetherStuffs.Blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class NetherOre extends Block {
	public static final int demonicOre = 0;
	public static final int netherStone = 1;
	public static final int netherOreIron = 2;
	public static final int netherOreGold = 3;
	public static final int netherOreDiamond = 4;
	public static final int netherOreRedstone = 5;
	public static final int netherOreEmerald = 6;
	public static final int netherOreCoal = 7;
	public static final int netherOreObsidian = 8;
	public static final int netherOreLapis = 9;
	public static final int netherOreCobblestone = 10;

	public NetherOre(int par1, int par2) {
		super(par1, par2, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setRequiresSelfNotify();
		this.setStepSound(soundStoneFootstep);
	}

	@Override
	public String getTextureFile() {
		return "/blocks.png";
	}

	public int getMetadataSize() {
		return NetherOreItemBlock.blockNames.length;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		switch (meta) {
		case demonicOre:
			return 0;
		case netherStone:
			return 1;
		case netherOreIron:
			return 2;
		case netherOreGold:
			return 3;
		case netherOreDiamond:
			return 4;
		case netherOreRedstone:
			return 5;
		case netherOreEmerald:
			return 6;
		case netherOreCoal:
			return 7;
		case netherOreObsidian:
			return 8;
		case netherOreLapis:
			return 9;
		case netherOreCobblestone:
			return 10;
		default:
			return 0;
		}
	}

	@Override
	public int idDropped(int meta, Random par2Random, int par3) {
		if(meta == netherOreCobblestone) //cobble may drop as cobble
			return Block.cobblestone.blockID;
		else
			return super.idDropped(meta, par2Random, par3);
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		for (int metaNumber = 0; metaNumber < getMetadataSize(); metaNumber++) {
			list.add(new ItemStack(par1, 1, metaNumber));
		}
	}
}
