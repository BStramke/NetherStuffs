package bstramke.NetherStuffs;

import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class OreConfigDefaults {
	public int MinXP;
	public int MaxXP;
	public int DropFragmentCountMin;
	public int DropFragmentCountMax;
	public float SmeltXP;
	public int SmeltResultCount;
	public int SmeltResultId;
	public int SmeltResultMeta;
	public int FragmentId;
	public int FragmentMeta;
	public int OreBlockId;
	public int OreBlockMeta;
	public ItemStack SmeltResult;

	public OreConfigDefaults(float SmeltXp, ItemStack FragmentItem_, ItemStack SmeltResult_, ItemStack OreBlock_) {
		this.MinXP = 0;
		this.MaxXP = 0;
		this.SmeltXP = SmeltXp;

		this.SmeltResult = SmeltResult_;
		if (SmeltResult_ != null) {
			this.SmeltResultCount = SmeltResult_.stackSize;
			this.SmeltResultId = SmeltResult_.itemID;
			this.SmeltResultMeta = SmeltResult_.getItemDamage();
		}
		this.FragmentMeta = FragmentItem_.getItemDamage();
		this.FragmentId = FragmentItem_.itemID;
		this.OreBlockId = OreBlock_.itemID;
		this.OreBlockMeta = OreBlock_.getItemDamage();
		this.DropFragmentCountMin = 1;
		this.DropFragmentCountMax = 3;
	}

	public OreConfigDefaults(ItemStack FragmentItem_, ItemStack SmeltResult_, ItemStack OreBlock_) {
		this.MinXP = 0;
		this.MaxXP = 0;
		this.SmeltXP = 0.25F;
		this.SmeltResult = SmeltResult_;
		if (SmeltResult_ != null) {
			this.SmeltResultCount = SmeltResult_.stackSize;
			this.SmeltResultId = SmeltResult_.itemID;
			this.SmeltResultMeta = SmeltResult_.getItemDamage();
		}
		this.FragmentMeta = FragmentItem_.getItemDamage();
		this.FragmentId = FragmentItem_.itemID;
		this.OreBlockId = OreBlock_.itemID;
		this.OreBlockMeta = OreBlock_.getItemDamage();
		this.DropFragmentCountMin = 1;
		this.DropFragmentCountMax = 3;
	}

}
