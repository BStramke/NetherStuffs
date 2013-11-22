package bstramke.NetherStuffs;

import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

public class OreConfig {
	
	public ItemStack SmeltResult;
	public int HarvestXPMin;
	public int HarvestXPMax;
	public int DropFragmentCountMin;
	public int DropFragmentCountMax;
	protected float OreSmeltXP;
	public boolean DoOreDropFragments;
	public int BlockId;
	public int BlockMeta;
	public int FragmentMeta;
	public int FragmentId;
	public float Resistance;
	public float Hardness;
	public StepSound stepSound;
	public int SmeltResultCount;
	public int SmeltResultItemId;
	public int SmeltResultItemMeta;

	
	public OreConfig(Configuration config, String OreName, OreConfigDefaults defaults) {
		String Category = "Settings for " + OreName;
		
		HarvestXPMin = config.get(Category, OreName +" Harvest XP", defaults.MinXP).getInt(defaults.MinXP);
		HarvestXPMax = config.get(Category, OreName +" Harvest XP", defaults.MaxXP).getInt(defaults.MaxXP);
		DropFragmentCountMin = config.get(Category, OreName +" Harvest DropFragmentCount Minimum", defaults.DropFragmentCountMin).getInt(defaults.DropFragmentCountMin);
		DropFragmentCountMax = config.get(Category, OreName +" Harvest DropFragmentCount Maximum", defaults.DropFragmentCountMax).getInt(defaults.DropFragmentCountMax);
		OreSmeltXP = (float) config.get(Category, OreName +" Smelt XP", defaults.SmeltXP).getDouble(defaults.SmeltXP);
		SmeltResultCount = config.get(Category, OreName +" Number of Smelt Results", defaults.SmeltResultCount).getInt(defaults.SmeltResultCount);
		if(OreSmeltXP > 1.0F)
			OreSmeltXP = 1.0F;
		DoOreDropFragments = config.get(Category, OreName +" Drop as small Fragments", false).getBoolean(false);
		SmeltResultItemId = defaults.SmeltResultId;
		SmeltResultItemMeta = defaults.SmeltResultMeta;
		SmeltResult = defaults.SmeltResult;
		
		BlockId = defaults.OreBlockId;
		BlockMeta = defaults.OreBlockMeta;
		FragmentMeta = defaults.FragmentMeta;
		FragmentId = defaults.FragmentId;
	}
}