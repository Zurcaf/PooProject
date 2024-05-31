package patrol_allocation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import discrete_stochastic_simulation.TimedEvent;
import evolution_simulation.*;

public class DistributionIndividual implements Individual<DistributionIndividual> {

    private final PatrolSimulation sim;
	private Distribution distribution;

    double deathTime;
    TimedEvent<SimulationEvent> deathEvent;
    TimedEvent<SimulationEvent> reproductionEvent;
    TimedEvent<SimulationEvent> mutationEvent;

	public DistributionIndividual(PatrolSimulation simulation, Distribution distribution) {
        this.sim = simulation;
		this.distribution = distribution;
	}

	public Distribution distribution() {
		return distribution;
	}

	public double comfort() {
        return distribution.comfort();
	}

	void mutateInPlace(){
        int patrols = this.distribution.array.length;
        int patrolChanged = sim.random.nextInt(0, patrols);
        while(this.distribution.array[patrolChanged].length==0){
            patrolChanged = sim.random.nextInt(0, patrols);
        }
        int systemChanged = sim.random.nextInt(0, this.distribution.array[patrolChanged].length);
        int newPatrol=-1;
        while(newPatrol==-1 || newPatrol==patrolChanged){
            newPatrol = sim.random.nextInt(0, patrols);
            if(newPatrol == patrolChanged){
                newPatrol=-1;
            }
        }
        int[][] newArray = new int[patrols][];
        for (int i = 0; i < patrols; i++) {
            if (i == patrolChanged) {
                newArray[i] = new int[this.distribution.array[i].length - 1];
                int index = 0;
                for (int j = 0; j < this.distribution.array[i].length; j++) {
                    if (j == systemChanged) {
                        continue;
                    }
                    newArray[i][index++] = this.distribution.array[i][j];
                }
            } else if (i == newPatrol) {
                newArray[i] = new int[this.distribution.array[i].length + 1];
                for (int j = 0; j < this.distribution.array[i].length; j++) {
                    newArray[i][j] = this.distribution.array[i][j];
                }
                newArray[i][this.distribution.array[i].length] = this.distribution.array[patrolChanged][systemChanged];
                Arrays.sort(newArray[i]);
            } else {
                newArray[i] = new int[this.distribution.array[i].length];
                for (int j = 0; j < this.distribution.array[i].length; j++) {
                    newArray[i][j] = this.distribution.array[i][j];
                }
            }
        }
        this.distribution = new Distribution(sim, newArray);        
    }

	DistributionIndividual reproduce() {
        int patrols = this.distribution.array.length;
        int m = 0; // Total number of systems
        int[] sysCounter = new int[patrols]; // Number of systems in each patrol
    
        // Calculate total number of systems and populate sysCounter
        for (int i = 0; i < patrols; i++) {
            m += this.distribution.array[i].length;
            sysCounter[i] = this.distribution.array[i].length;
        }
    
        // Generate altered systems
        int difSystems = (int) Math.floor((1 - this.distribution.comfort()) * m);
        // System.out.println("difSystems: " + difSystems);
        // System.out.println("confort: " + cachedComfort);

        int[] altSystems = this.generateUniqueRandomNumbers(difSystems, m); // Altered systems
    
        // Create a copy of the current array
        int[][] newArray = new int[patrols][];
        for (int i = 0; i < patrols; i++) {
            newArray[i] = Arrays.copyOf(this.distribution.array[i], this.distribution.array[i].length);
        }
    
        // Reallocate altered systems to new patrols
        for (int i = 0; i < difSystems; i++) {
            int oldPatrol = 0;
            // Find the patrol containing the altered system
            for (int j = 0; j < patrols; j++) {
                if (Arrays.binarySearch(this.distribution.array[j], altSystems[i]) >= 0) {
                    oldPatrol = j;
                    break;
                }
            }
    
            int newPatrol;
            // Find a new patrol for the altered system
            do {
                newPatrol = sim.random.nextInt(patrols);
            } while (Arrays.binarySearch(newArray[newPatrol], altSystems[i]) >= 0); // Check if the system is already in the new patrol
    
            // Remove the system from the old patrol
            int[] oldPatrolArray = newArray[oldPatrol];
            int[] tempOldPatrol = new int[oldPatrolArray.length - 1];
            int k = 0;
            for (int system : oldPatrolArray) {
                if (system != altSystems[i]) {
                    tempOldPatrol[k++] = system;
                }
            }
            newArray[oldPatrol] = tempOldPatrol;
    
            // Add the system to the new patrol
            int[] tempNewPatrol = Arrays.copyOf(newArray[newPatrol], newArray[newPatrol].length + 1);
            tempNewPatrol[tempNewPatrol.length - 1] = altSystems[i];
            Arrays.sort(tempNewPatrol);
            newArray[newPatrol] = tempNewPatrol;
        }
    
        // Create the child distribution
        Distribution newDistribution = new Distribution(sim, newArray);
		DistributionIndividual newChild = new DistributionIndividual(sim, newDistribution);
		return newChild;
    }

	private int[] generateUniqueRandomNumbers(int difSystems, int upperBound) {
        Set<Integer> uniqueNumbers = new HashSet<>();
        while (uniqueNumbers.size() < difSystems) {
            int randomNumber = sim.random.nextInt(upperBound);
            uniqueNumbers.add(randomNumber);
        }
        // Convert the set to an array
        int[] randomNumbers = new int[difSystems];
        int index = 0;
        for (int number : uniqueNumbers) {
            randomNumbers[index++] = number;
        }
        return randomNumbers;
    }

    public boolean isSolutionEqual(DistributionIndividual other) {
        return this.distribution.equals(other.distribution);
    }

    public void onEpidemicDeath() {
        patrol_allocation.Debug.log("Individual " + this.hashCode() + " was killed by an epidemic");
        sim.pec.removeEvent(deathEvent);
        sim.pec.removeEvent(reproductionEvent);
        sim.pec.removeEvent(mutationEvent);
        if (sim.evolutionEngine.populationCount() == 0) {
            sim.onPopulationExtinct();
        }
    }

}