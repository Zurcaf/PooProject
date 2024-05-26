package patrol_allocation;

import java.util.Arrays;
import java.util.Random;

import evolution_simulation.*;

public class DistributionIndividual implements Individual {

	private Distribution distribution;
	Random r = new Random();
	DistributionIndividual(Distribution distribution) {
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
		// TODO - implement DistributionIndividual.reproduce
		throw new UnsupportedOperationException();
	}

}