package patrol_allocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/** 
 * An immutable class representing a distribution of planetary systems among patrols for a specific simulation.
 */
public class Distribution {
    final PatrolSimulation sim;
    final int[][] array;

    final int policingTime;
    final double comfort;

    /**
     * Creates a new random Distribution for the given simulation.
     *
     * @param sim The simulation for which the distribution is created.
     * @return A new random Distribution.
     */
    static Distribution newRandom(PatrolSimulation sim) {
        int[] allocS = new int[sim.systemCount];
        int[] allocP = new int[sim.patrolCount];
        for (int i = 0; i < sim.systemCount; i++) {
            int systemP = sim.random.nextInt(0, sim.patrolCount);
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

    /**
     * Constructs a new Distribution with the specified simulation and array.
     *
     * @param simulation The simulation for which the distribution is created.
     * @param array The array representing the distribution of planetary systems among patrols.
     */
    Distribution(PatrolSimulation simulation, int[][] array) {
        this.sim = simulation;
        this.array = array;

        policingTime = calculatePolicingTime();
        comfort = sim.policingTimeLowerBound / policingTime;
    }

    /**
     * Calculates the total policing time for this distribution.
     *
     * @return The maximum policing time among all patrols.
     */
    private int calculatePolicingTime() {
        int[] timeArray = new int[sim.patrolCount];
        int maxTime = 0;
        for (int i = 0; i < sim.patrolCount; i++) {
            timeArray[i] = 0;
            for (int j = 0; j < array[i].length; j++) {
                timeArray[i] += sim.timeMatrix[i][array[i][j]];
            }
            if (timeArray[i] > maxTime) {
                maxTime = timeArray[i];
            }
        }
        return maxTime;
    }

    /**
     * Returns the policing time of this distribution.
     *
     * @return The policing time.
     */
    public int policingTime() {
        return policingTime;
    }

    /**
     * Returns the comfort level of this distribution.
     *
     * @return The comfort level.
     */
    public double comfort() {
        return comfort;
    }

    /**
     * Returns a textual representation of the distribution.
     *
     * @return A string representation of the distribution.
     */
    public String toString() {
        return Arrays.deepToString(array)
            .replace("[", "{")
            .replace("]", "}")
            .replace(", ", ",");
    }

    /**
     * Returns a read-only list that associates each patrol with the set of planetary systems it patrols.
     *
     * @return An unmodifiable list of sets representing the distribution.
     */
    public List<Set<Integer>> asList() {
        ArrayList<Set<Integer>> list = new ArrayList<Set<Integer>>(array.length);
        for (int p = 0; p < array.length; p++) {
            list.add(p, Collections.unmodifiableSet(Arrays.stream(array[p]).boxed().collect(Collectors.toSet())));
        }
        return Collections.unmodifiableList(list);
    }

    /**
     * Compares this distribution with another object for equality.
     *
     * @param obj The object to compare with.
     * @return true if the distributions are equal, false otherwise.
     */
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
