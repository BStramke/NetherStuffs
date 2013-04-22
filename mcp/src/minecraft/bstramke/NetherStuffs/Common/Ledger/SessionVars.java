package bstramke.NetherStuffs.Common.Ledger;

public class SessionVars {

	private static Class openedLedger;

	public static void setOpenedLedger(Class ledgerClass) {
		openedLedger = ledgerClass;
	}

	public static Class getOpenedLedger() {
		return openedLedger;
	}
}