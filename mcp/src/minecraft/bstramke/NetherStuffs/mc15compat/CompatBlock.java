package bstramke.NetherStuffs.mc15compat;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;

public class CompatBlock {
	public static final Block oreQuartz = new BlockQuartzOre(153, 255).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setBlockName("oreQuartz");
	public static final Block blockQuartz = new BlockQuartz(155, 254).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setBlockName("blockQuartz");
}
