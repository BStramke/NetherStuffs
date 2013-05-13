package bstramke.NetherStuffs.Common;

public class BlockActiveHelper {
	private static final int METADATA_BITMASK = 0x7;
	private static final int METADATA_ACTIVEBIT = 0x8;
	private static final int METADATA_CLEARACTIVEBIT = -METADATA_ACTIVEBIT - 1;

	public static int clearActiveOnMetadata(int metadata) {
		return metadata & METADATA_CLEARACTIVEBIT;
	}

	public static boolean isActiveSet(int metadata) {
		return (metadata & METADATA_ACTIVEBIT) != 0;
	}

	public static int setActiveOnMetadata(int metadata) {
		return metadata | METADATA_ACTIVEBIT;
	}

	public static int unmarkedMetadata(int metadata) {
		return metadata & METADATA_BITMASK;
	}
}
