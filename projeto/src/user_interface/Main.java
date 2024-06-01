package user_interface;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

import patrol_allocation.PatrolSimulation;

/**
 * The main class for running the patrol allocation simulation. It accepts command-line arguments
 * to configure the simulation parameters either randomly or from a file.
 */
public class Main {

    /**
     * The entry point of the application. Parses command-line arguments to set up and run the simulation.
     *
     * @param args Command-line arguments to configure the simulation.
     *             Usage:
     *             <pre>
     *             java -jar project.jar [ -seed &lt;seed&gt; ] ( -f &lt;filename&gt; | -r &lt;number of patrols&gt; &lt;number of planetary systems&gt; &lt;final instant&gt; &lt;initial population&gt; &lt;maximum population&gt; &lt;death parameter&gt; &lt;reproduction parameter&gt; &lt;mutation parameter&gt; )
     *             </pre>
     *             Options:
     *             <ul>
     *               <li>-seed &lt;seed&gt;: Sets the seed for the random number generator.</li>
     *               <li>-f &lt;filename&gt;: Reads simulation parameters from the specified file.</li>
     *               <li>-r &lt;number of patrols&gt; &lt;number of planetary systems&gt; &lt;final instant&gt; &lt;initial population&gt; &lt;maximum population&gt; &lt;death parameter&gt; &lt;reproduction parameter&gt; &lt;mutation parameter&gt;: Sets simulation parameters randomly.</li>
     *             </ul>
     */
    public static void main(String[] args) {
        boolean parametersSet = false;
        int patrolCount = 0;
        int systemCount = 0;
        int[][] timeMatrix = new int[0][0];
        double simDuration = 0;
        int initialPopulation = 0;
        int maxPopulation = 0;
        double deathParam = 0;
        double reproductionParam = 0;
        double mutationParam = 0;

        long seed = 0;

        for (int argIndex = 0; argIndex < args.length;) {
            if (args[argIndex].equals("-r")) {
                if (args.length - argIndex < 9) {
                    System.err.println("Expected 8 arguments after -r.");
                    return;
                }
                parametersSet = true;

                patrolCount = Integer.parseInt(args[argIndex + 1]);
                systemCount = Integer.parseInt(args[argIndex + 2]);
                simDuration = Double.parseDouble(args[argIndex + 3]);
                initialPopulation = Integer.parseInt(args[argIndex + 4]);
                maxPopulation = Integer.parseInt(args[argIndex + 5]);
                deathParam = Double.parseDouble(args[argIndex + 6]);
                reproductionParam = Double.parseDouble(args[argIndex + 7]);
                mutationParam = Double.parseDouble(args[argIndex + 8]);

                timeMatrix = new int[patrolCount][systemCount];
                Random random = new Random();
                for (int p = 0; p < patrolCount; p++) {
                    for (int s = 0; s < systemCount; s++) {
                        timeMatrix[p][s] = random.nextInt(1, 11);
                    }
                }

                argIndex += 9;
            } else if (args[argIndex].equals("-f")) {
                if (args.length - argIndex < 1) {
                    System.err.println("Expected 1 argument after -f.");
                    return;
                }
                parametersSet = true;

                File file = new File(args[argIndex + 1]);
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

                argIndex += 2;
            } else if (args[argIndex].equals("-seed")) {
                seed = Long.parseLong(args[argIndex + 1]);

                argIndex += 2;
            } else {
                System.err.println("Unrecognized option \"" + args[argIndex] + "\".");
                return;
            }
        }

        if (!parametersSet) {
            System.err.println(
                "Usage: java -jar project.jar [ -seed <seed> ] ( -f <filename> | -r <number of patrols> <number of planetary systems> <final instant> <initial population> <maximum population> <death parameter> <reproduction parameter> <mutation parameter> )"
            );
            return;
        }

        PatrolSimulation sim = new PatrolSimulation(timeMatrix, simDuration, initialPopulation, maxPopulation, deathParam, reproductionParam, mutationParam, seed == 0 ? new Random() : new Random(seed));
        PrintingObserver observer = new PrintingObserver();
        sim.addObserver(observer);
        sim.run();
    }
}
