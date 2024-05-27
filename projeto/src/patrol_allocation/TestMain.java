package patrol_allocation;

import java.util.Set;

public class TestMain {
	public static void main(String[] args) {
		// Distribution d = new Distribution(new int[][] {new int[] {1,3,4,6}, new int[] {2,5}, new int[] {}});
		// System.out.println(d);
		// for (Set<Integer> s : d.asList()) {
		// 	System.out.println(s);
		// }
		PatrolSimulation s = new PatrolSimulation(new int[][] { new int[] {}, new int[] {3,4,5} }, 0, 0, 0, 0, 0, 0);
	}
}
