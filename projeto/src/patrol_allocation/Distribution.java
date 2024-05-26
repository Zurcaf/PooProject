package patrol_allocation;

import java.util.Arrays;
import java.util.Random;

public class Distribution {
	Random r = new Random();
	private final int[][] array;

	public Distribution(int patrols, int systems) {
        int[] allocS = new int [systems]; 
        int[] allocP = new int [patrols];
        for (int i = 0; i<systems; i++){
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
	    this.array = array;
	}

	public String toString() {
		return Arrays.deepToString(array)
			.replace("[", "{")
			.replace("]", "}")
			.replace(", ", ",");
	}

	public int[][] getArray() {
		return array;
	}

    public void changeArray(int[][] newArray){
        for (int i = 0; i < newArray.length; i++) {
            array[i] = new int[newArray[i].length];
            for (int j = 0; j < newArray[i].length; j++) {
                array[i][j] = newArray[i][j];
            }
        }
    }

}