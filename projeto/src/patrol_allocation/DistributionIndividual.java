package patrol_allocation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import evolution_simulation.*;

public class DistributionIndividual implements Individual {

	private Distribution distribution;
	private float confortV;
	Random r = new Random();
	public DistributionIndividual(Distribution distribution) {
		this.distribution = distribution;
	}

	public Distribution distribution() {
		return distribution;
	}

	public float comfort(int matrixC[][]) {
		int sumMin = 0;
		for(int i=0; i<matrixC[0].length; i++){
			int min = matrixC[0][i];
			for(int j=1; j<matrixC.length; j++){
				if(matrixC[j][i]<min){
					min = matrixC[j][i];
				}
			}
			sumMin += min;
		}
		float tMin = sumMin/(matrixC.length);
		float tz = (float) policingTime(matrixC);
		this.confortV = tMin/tz;
		return tMin/tz;
	}

	public int policingTime(int matrixC[][]) {
		int[] timeArray = new int[matrixC.length];
		int maxTime = 0;
		int [][] arrayUsed = this.distribution.getArray();
		for(int i=0;i<matrixC.length;i++){
			timeArray[i] = 0;
			for(int j=0;j<arrayUsed[i].length;j++){
				timeArray[i] += matrixC[i][arrayUsed[i][j]];	
			}
			if(timeArray[i]>maxTime){
				maxTime = timeArray[i];
			}
		}
		return maxTime;
	}

	public void mutateInPlace(){
        int patrols = this.distribution.getArray().length;
//        int systems = array[0].length;
        int patrolChanged = r.nextInt(0, patrols);
        while(this.distribution.getArray()[patrolChanged].length==0){
            patrolChanged = r.nextInt(0, patrols);
        }            
        int systemChanged = r.nextInt(0, this.distribution.getArray()[patrolChanged].length);
        int newPatrol=-1;
        while(newPatrol==-1 || newPatrol==patrolChanged){
            newPatrol = r.nextInt(0, patrols);
            if(newPatrol == patrolChanged){
                newPatrol=-1;
            }
        }
        int[][] newArray = new int[patrols][];
        for (int i = 0; i < patrols; i++) {
            if (i == patrolChanged) {
                newArray[i] = new int[this.distribution.getArray()[i].length - 1];
                int index = 0;
                for (int j = 0; j < this.distribution.getArray()[i].length; j++) {
                    if (j == systemChanged) {
                        continue;
                    }
                    newArray[i][index++] = this.distribution.getArray()[i][j];
                }
            } else if (i == newPatrol) {
                newArray[i] = new int[this.distribution.getArray()[i].length + 1];
                for (int j = 0; j < this.distribution.getArray()[i].length; j++) {
                    newArray[i][j] = this.distribution.getArray()[i][j];
                }
                newArray[i][this.distribution.getArray()[i].length] = this.distribution.getArray()[patrolChanged][systemChanged];
                Arrays.sort(newArray[i]);
            } else {
                newArray[i] = new int[this.distribution.getArray()[i].length];
                for (int j = 0; j < this.distribution.getArray()[i].length; j++) {
                    newArray[i][j] = this.distribution.getArray()[i][j];
                }
            }
        }
        this.distribution.changeArray(newArray);        
    }

	public Individual reproduce() {
        int patrols = this.distribution.getArray().length;
        int m = 0; // Total number of systems
        int[] sysCounter = new int[patrols]; // Number of systems in each patrol
    
        // Calculate total number of systems and populate sysCounter
        for (int i = 0; i < patrols; i++) {
            m += this.distribution.getArray()[i].length;
            sysCounter[i] = this.distribution.getArray()[i].length;
        }
    
        // Generate altered systems
        int difSystems = (int) Math.floor((1 - this.confortV) * m);
        System.out.println("difSystems: " + difSystems);
        System.out.println("confort: " + confortV);

        int[] altSystems = this.generateUniqueRandomNumbers(difSystems, m); // Altered systems
    
        // Create a copy of the current array
        int[][] newArray = new int[patrols][];
        for (int i = 0; i < patrols; i++) {
            newArray[i] = Arrays.copyOf(this.distribution.getArray()[i], this.distribution.getArray()[i].length);
        }
    
        // Reallocate altered systems to new patrols
        for (int i = 0; i < difSystems; i++) {
            int oldPatrol = 0;
            // Find the patrol containing the altered system
            for (int j = 0; j < patrols; j++) {
                if (Arrays.binarySearch(this.distribution.getArray()[j], altSystems[i]) >= 0) {
                    oldPatrol = j;
                    break;
                }
            }
    
            int newPatrol;
            // Find a new patrol for the altered system
            do {
                newPatrol = r.nextInt(patrols);
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
        Distribution child = new Distribution(patrols, 0);
        child.changeArray(newArray);
		DistributionIndividual newChild = new DistributionIndividual(child);
		return newChild;
    }

	private int[] generateUniqueRandomNumbers(int difSystems, int upperBound) {
        Set<Integer> uniqueNumbers = new HashSet<>();
        while (uniqueNumbers.size() < difSystems) {
            int randomNumber = r.nextInt(upperBound);
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

}