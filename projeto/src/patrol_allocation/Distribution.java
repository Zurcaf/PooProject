package patrol_allocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/** An immutable class representing a distribution of planetary systems among patrols for a specific simulation. */
public class Distribution {
    final PatrolSimulation sim;
	static final Random r = new Random(2);
	final int[][] array;

    static Distribution newRandom(PatrolSimulation sim) {
        int[] allocS = new int[sim.systemCount]; 
        int[] allocP = new int[sim.patrolCount];
        for (int i = 0; i<sim.systemCount; i++) {
            int systemP = r.nextInt(0,sim.patrolCount);
            allocS[i] = systemP;
            allocP[systemP]++;
        }
        int[][] array = new int[sim.patrolCount][];
        for (int i = 0; i < sim.patrolCount; i++) {
            array[i] = new int[allocP[i]];
        }  
        int[] patrolIndexes = new int[sim.patrolCount];  
        for (int i = 0; i < sim.systemCount; i++) {
            int patrol = allocS[i];
            array[patrol][patrolIndexes[patrol]] = i;
            patrolIndexes[patrol]++;
        }
        return new Distribution(sim, array);
    }

	Distribution(PatrolSimulation simulation, int[][] array) {
        this.sim = simulation;
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

    public int policingTime() {
		int[] timeArray = new int[sim.patrolCount];
		int maxTime = 0;
		for(int i=0;i<sim.patrolCount;i++){
			timeArray[i] = 0;
			for(int j=0;j<array[i].length;j++){
				timeArray[i] += sim.timeMatrix[i][array[i][j]];	
			}
			if(timeArray[i]>maxTime){
				maxTime = timeArray[i];
			}
		}
		return maxTime;
	}

    public double comfort() {
        int sumMin = 0;
		for(int i=0; i<sim.systemCount; i++){
			int min = sim.timeMatrix[0][i];
			for(int j=1; j<sim.patrolCount; j++){
				if(sim.timeMatrix[j][i]<min){
					min = sim.timeMatrix[j][i];
				}
			}
			sumMin += min;
		}
		double tMin = sumMin/(sim.patrolCount);
		double tz = (double) policingTime();
		return tMin/tz;
    }

}