package solver_comparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

import patrol_allocation.DistributionIndividual;
import patrol_allocation.PatrolSimulation;

public class Main {

	public static void main(String[] args) {
		if (!args[0].equals("-r") && !args[0].equals("-f")) {
			System.err.println("Expected first argument to be either -r or -f");
			return;
		}

		int patrolCount = 0;
		int systemCount = 0;
		int[][] timeMatrix = null;
		double simDuration = 0;
		int initialPopulation = 0;
		int maxPopulation = 0;
		double deathParam = 0;
		double reproductionParam = 0;
		double mutationParam = 0;

		int iterations = 0;
		String resultsFile = null;

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
			iterations = Integer.parseInt(args[9]);
			resultsFile = args[10];

			System.err.println("Não especificar uma matriz pode causar demasiada variância nos resultados.");

		} else if (args[0].equals("-f")) {
			File file = new File(args[1]);
			try {
				Scanner scanner = new Scanner(file);
				scanner.useLocale(Locale.US);

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

			iterations = Integer.parseInt(args[2]);
			resultsFile = args[3];
		}

		try {
			FileWriter writer = new FileWriter(resultsFile);
			writer.write("v1 comfort,v1 policing time,v2 comfort,v2 policing time\n");

			for (int i = 0; i < iterations; i++) {
				System.out.println("Iteration "+i);

				int[][] realTimeMatrix;
				if (timeMatrix == null) {
					realTimeMatrix = new int[patrolCount][systemCount];
					Random random = new Random();
					for (int p = 0; p < patrolCount; p++) {
						for (int s = 0; s < systemCount; s++) {
							realTimeMatrix[p][s] = random.nextInt(1, 11);
						}
					}
				} else {
					realTimeMatrix = timeMatrix;
				}
	
				{
					PatrolSimulation sim = new PatrolSimulation(realTimeMatrix, simDuration, initialPopulation, maxPopulation, deathParam, reproductionParam, mutationParam, false);
					sim.run();
					DistributionIndividual best = sim.bestIndividualEver();
					System.out.println(String.format("v1 simulation:   comfort = %.3f   policing time = %d", best.comfort(), best.policingTime()));
					writer.write(best.comfort()+","+best.policingTime()+",");
				}
				{
					PatrolSimulation sim = new PatrolSimulation(realTimeMatrix, simDuration, initialPopulation, maxPopulation, deathParam, reproductionParam, mutationParam, true);
					sim.run();
					DistributionIndividual best = sim.bestIndividualEver();
					System.out.println(String.format("v2 simulation:   comfort = %.3f   policing time = %d", best.comfort(), best.policingTime()));
					writer.write(best.comfort()+","+best.policingTime()+"\n");
				}
			}

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}