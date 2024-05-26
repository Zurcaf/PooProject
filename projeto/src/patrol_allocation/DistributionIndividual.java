package patrol_allocation;

import evolution_simulation.*;

public class DistributionIndividual implements Individual {

	private Distribution distribution;

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
		int [][] arrayUsed = this.distribution.array();
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

	public void mutateInPlace() {
		// TODO - implement DistributionIndividual.mutateInPlace
		throw new UnsupportedOperationException();
	}

	public Individual reproduce() {
		// TODO - implement DistributionIndividual.reproduce
		throw new UnsupportedOperationException();
	}

}