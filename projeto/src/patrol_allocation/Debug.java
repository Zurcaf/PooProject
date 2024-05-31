package patrol_allocation;

public class Debug {
	static final boolean ENABLED = false;

	static public void log(String str) {
		if (ENABLED) System.err.println(str);
	}

	static public void check(boolean invariant) {
		if (!invariant) {
			throw new AssertionError();
		}
	}
	static public void check(boolean invariant, String message) {
		if (!invariant) {
			throw new AssertionError(message);
		}
	}

}
