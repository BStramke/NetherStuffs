package forestry.api.core;

import java.util.ArrayList;

public enum EnumHumidity {
	ARID("Arid", 2), NORMAL("Normal", 1), DAMP("Damp", 4);

	/**
	 * Populated by Forestry with vanilla biomes. Add additional arid biomes here. (ex. desert)
	 */
	public static ArrayList<Integer> aridBiomeIds = new ArrayList<Integer>();
	/**
	 * Populated by Forestry with vanilla biomes. Add additional damp biomes here. (ex. jungle)
	 */
	public static ArrayList<Integer> dampBiomeIds = new ArrayList<Integer>();
	/**
	 * Populated by Forestry with vanilla biomes. Add additional normal biomes here.
	 */
	public static ArrayList<Integer> normalBiomeIds = new ArrayList<Integer>();

	public final String name;
	public final int itemIcon;

	private EnumHumidity(String name, int itemIcon) {
		this.name = name;
		this.itemIcon = itemIcon;
	}

	public String getName() {
		return this.name;
	}

	public int getitemIcon() {
		return this.itemIcon;
	}

	public static ArrayList<Integer> getBiomeIds(EnumHumidity humidity) {
		switch (humidity) {
		case ARID:
			return aridBiomeIds;
		case DAMP:
			return dampBiomeIds;
		case NORMAL:
		default:
			return normalBiomeIds;
		}
	}
}
