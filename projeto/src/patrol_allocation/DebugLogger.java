package patrol_allocation;

public class DebugLogger {
	static final boolean ENABLED = true;

	static public void log(String str) {
		if (ENABLED) System.err.println(str);
	}
}
