package patrol_allocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Distribution {
	static Random r = new Random();
	final int[][] array;

    static Distribution newRandom(int patrols, int systems) {
        int[] allocS = new int[systems]; 
        int[] allocP = new int[patrols];
        for (int i = 0; i<systems; i++) {
            int systemP = r.nextInt(0,patrols);
            allocS[i] = systemP;
            allocP[systemP]++;
        }
        int[][] array = new int[patrols][];
        for (int i = 0; i < patrols; i++) {
            array[i] = new int[allocP[i]];
        }  
        int[] patrolIndexes = new int[patrols];  
        for (int i = 0; i < systems; i++) {
            int patrol = allocS[i];
            array[patrol][patrolIndexes[patrol]] = i;
            patrolIndexes[patrol]++;
        }
        return new Distribution(array);
    }

	Distribution(int[][] array) {
	    this.array = array;
	}
    
    /**
     * Returns a textual representation of the distribution
     */
	public String toString() {
		return Arrays.deepToString(array)
			.replace("[", "{")
			.replace("]", "}")
			.replace(", ", ",");
	}

    /**
     * Returns a read-only list that associates each patrol with the set of planetary systems it patrols
     */
	public List<Set<Integer>> asList() {
        ArrayList<Set<Integer>> list = new ArrayList<Set<Integer>>(array.length);
        for (int p = 0; p < array.length; p++) {
            list.add(p, Collections.unmodifiableSet(Arrays.stream(array[p]).boxed().collect(Collectors.toSet())));
        }
		return Collections.unmodifiableList(list);
	}

    public void changeArray(int[][] newArray){
        for (int i = 0; i < newArray.length; i++) {
            array[i] = new int[newArray[i].length];
            for (int j = 0; j < newArray[i].length; j++) {
                array[i][j] = newArray[i][j];
            }
        }
    }

    public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Distribution))
			return false;
        Distribution otherDistribution = (Distribution) obj;
        return Arrays.deepEquals(this.array, otherDistribution.array);
    }

}