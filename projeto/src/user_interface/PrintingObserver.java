package user_interface;

import java.util.ArrayList;
import java.util.List;
import patrol_allocation.*;

/**
 * A concrete implementation of the {@link SimulationObserver} interface that prints the details
 * of each observation made during the patrol simulation.
 */
public class PrintingObserver implements SimulationObserver {

    static int observationCount = 0;

    /**
     * Called when an observation is made in the simulation. This method prints the details
     * of the observation, including the best distribution of patrols, the current state of the population,
     * and the performance of other candidate distributions.
     *
     * @param observation The observation that was made.
     */
    public void onObservation(SimulationObservation observation) {
        Distribution bestDistributionEver = observation.simulation().bestDistributionEver();
        List<DistributionIndividual> bestIndividualsAlive = new ArrayList<DistributionIndividual>(observation.bestIndividuals());

        StringBuilder sb = new StringBuilder(1000);
        sb.append("Observation ");
        sb.append(observationCount + 1);
        sb.append(":\n");
        sb.append("    Present instant:                  ");
        sb.append(observation.currentInstant());
        sb.append("\n");
        sb.append("    Number of realized events:        ");
        sb.append(observation.totalEventCount());
        sb.append("\n");
        sb.append("    Population size:                  ");
        sb.append(observation.populationCount());
        sb.append("\n");
        sb.append("    Number of epidemics:              ");
        sb.append(observation.epidemicCount());
        sb.append("\n");
        sb.append("    Best distribution of the patrols: ");
        sb.append(bestDistributionEver);
        sb.append("\n");
        sb.append("    Empire policing time:             ");
        sb.append(bestDistributionEver.policingTime());
        sb.append("\n");
        sb.append("    Comfort:                          ");
        sb.append(bestDistributionEver.comfort());
        sb.append("\n");
        sb.append("    Other candidate distributions:    ");
        if (bestIndividualsAlive.size() == 0) {
            sb.append("(the population is extinct)");
        } else {
            if (bestIndividualsAlive.get(0).distribution().equals(bestDistributionEver)) {
                // If the best individual alive has the best distribution found so far, don't print that distribution again.
                bestIndividualsAlive.remove(0);
            } else if (bestIndividualsAlive.size() > 5) {
                // Limit the distributions shown to a maximum of 5 (note that bestIndividualsAlive never has more than 6 entries)
                bestIndividualsAlive.remove(5);
            }
            if (bestIndividualsAlive.size() == 0) {
                sb.append("(there are no individuals with other distributions alive)");
            }
            for (int i = 0; i < bestIndividualsAlive.size(); i++) {
                Distribution distribution = bestIndividualsAlive.get(i).distribution();
                sb.append(distribution);
                sb.append(" : ");
                sb.append(distribution.policingTime());
                sb.append(" : ");
                sb.append(distribution.comfort());
                sb.append("\n");
                if (i < bestIndividualsAlive.size() - 1) {
                    sb.append("                                      ");
                }
            }
        }
        System.out.println(sb);

        observationCount++;
    }
}
