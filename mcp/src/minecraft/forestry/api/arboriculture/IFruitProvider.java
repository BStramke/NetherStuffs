package forestry.api.arboriculture;

import forestry.api.genetics.IFruitFamily;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public interface IFruitProvider {
	
	IFruitFamily getFamily();
	
	int getColour(ITreeGenome genome, IBlockAccess world, int x, int y, int z, int ripeningTime);
	
	boolean markAsFruitLeaf(ITreeGenome genome, World world, int x, int y, int z);
	
	int getRipeningPeriod();
	
	// / Products, Chance
	ItemStack[] getProducts();

	// / Specialty, Chance
	ItemStack[] getSpecialty();

	ItemStack[] getFruits(ITreeGenome genome, World world, int x, int y, int z, int ripeningTime);
	
	/**
	 * @return Short, human-readable identifier used in the treealyzer.
	 */
	String getDescription();

	/* TEXTURE OVERLAY */
	
	/**
	 * @param genome
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param ripeningTime  Elapsed ripening time for the fruit.
	 * @param fancy
	 * @return Icon index of the texture to overlay on the leaf block.
	 */
	int getTextureIndex(ITreeGenome genome, IBlockAccess world, int x, int y, int z, int ripeningTime, boolean fancy);
	
	/**
	 * @return false to use the texture returned by getTextureFile for rendering.
	 */
	boolean usesDefaultTexture();
	
	/**
	 * @return Texture file to use, ignored when useDefaultTexture is true.
	 */
	String getTextureFile();

}
