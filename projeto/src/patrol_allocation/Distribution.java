package patrol_allocation;

import java.util.Arrays;

public class Distribution {

	private final int[][] array;

	Distribution(int[][] array) {
		this.array = array;
	}

	public String toString() {
		return Arrays.deepToString(array)
			.replace("[", "{")
			.replace("]", "}")
			.replace(", ", ",");
	}

	public int[][] array() {
		return array;
	}

}