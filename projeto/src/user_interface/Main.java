package user_interface;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import patrol_allocation.PatrolSimulation;

public class Main {

	public static void main(String[] args) {
		if (!args[0].equals("-r") && !args[0].equals("-f")) {
			System.err.println("Expected first argument to be either -r or -f");
			return;
		}

		int patrolCount = 0;
		int systemCount = 0;
		int[][] timeMatrix = new int[0][0];
		double simDuration = 0;
		int initialPopulation = 0;
		int maxPopulation = 0;
		double deathParam = 0;
		double reproductionParam = 0;
		double mutationParam = 0;

		if (args[0].equals("-r")) {
			if (args.length < 9) {
				System.err.println("Expected 8 arguments after -r.");
				return;
			}

			patrolCount = Integer.parseInt(args[1]);
			systemCount = Integer.parseInt(args[2]);
			simDuration = Double.parseDouble(args[3]);
			initialPopulation = Integer.parseInt(args[4]);
			maxPopulation = Integer.parseInt(args[5]);
			deathParam = Double.parseDouble(args[6]);
			reproductionParam = Double.parseDouble(args[7]);
			mutationParam = Double.parseDouble(args[8]);

			timeMatrix = new int[patrolCount][systemCount];
			Random random = new Random();
			for (int p = 0; p < patrolCount; p++) {
				for (int s = 0; s < systemCount; s++) {
					timeMatrix[p][s] = random.nextInt(1, 11);
				}
			}

		} else if (args[0].equals("-f")) {
			File file = new File(args[1]);
			try {
				Scanner scanner = new Scanner(file);

				patrolCount = scanner.nextInt();
				systemCount = scanner.nextInt();
				simDuration = scanner.nextDouble();
				initialPopulation = scanner.nextInt();
				maxPopulation = scanner.nextInt();
				deathParam = scanner.nextDouble();
				reproductionParam = scanner.nextDouble();
				mutationParam = scanner.nextDouble();

				timeMatrix = new int[patrolCount][systemCount];
				for (int p = 0; p < patrolCount; p++) {
					for (int s = 0; s < systemCount; s++) {
						timeMatrix[p][s] = scanner.nextInt();
					}
				}

				scanner.close();
			} catch (FileNotFoundException e) {
				System.err.println("Couldn't find file " + args[1]);
				return;
			}
		}

		PatrolSimulation sim = new PatrolSimulation(timeMatrix, simDuration, initialPopulation, maxPopulation, deathParam, reproductionParam, mutationParam);
		PrintingObserver observer = new PrintingObserver();
		sim.addObserver(observer);
		sim.run();
	}

}