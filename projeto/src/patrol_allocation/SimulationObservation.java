package patrol_allocation;

import java.util.List;

/**
 * Represents an observation of the state of the patrol simulation at a specific point in time.
 */
public class SimulationObservation {

    private final PatrolSimulation simulation;
    private final double instant;
    private final int totalEventCount;
    private final int populationCount;
    private final int epidemicCount;
    private final List<DistributionIndividual> bestIndividuals;

    /**
     * Constructs a new SimulationObservation with the specified parameters.
     *
     * @param simulation The simulation this observation belongs to.
     * @param instant The current instant of the simulation when the observation was made.
     * @param totalEventCount The total number of events that have occurred up to this point.
     * @param populationCount The current population count of the simulation.
     * @param epidemicCount The number of epidemics that have occurred up to this point.
     * @param bestIndividuals The list of the best individuals in the simulation at this point.
     */
    SimulationObservation(
        PatrolSimulation simulation,
        double instant,
        int totalEventCount,
        int populationCount,
        int epidemicCount,
        List<DistributionIndividual> bestIndividuals
    ) {
        this.simulation = simulation;
        this.instant = instant;
        this.totalEventCount = totalEventCount;
        this.populationCount = populationCount;
        this.epidemicCount = epidemicCount;
        this.bestIndividuals = bestIndividuals;
    }

    /**
     * Returns the simulation this observation belongs to.
     *
     * @return The simulation this observation belongs to.
     */
    public PatrolSimulation simulation() {
        return simulation;
    }

    /**
     * Returns the current instant of the simulation when the observation was made.
     *
     * @return The current instant of the simulation.
     */
    public double currentInstant() {
        return instant;
    }

    /**
     * Returns the total number of events that have occurred up to this point.
     *
     * @return The total number of events.
     */
    public int totalEventCount() {
        return totalEventCount;
    }

    /**
     * Returns the current population count of the simulation.
     *
     * @return The population count.
     */
    public int populationCount() {
        return populationCount;
    }

    /**
     * Returns the number of epidemics that have occurred up to this point.
     *
     * @return The epidemic count.
     */
    public int epidemicCount() {
        return epidemicCount;
    }

    /**
     * Returns the list of the best individuals in the simulation at this point.
     *
     * @return The list of the best individuals.
     */
    public List<DistributionIndividual> bestIndividuals() {
        return bestIndividuals;
    }
}
