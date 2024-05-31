package patrol_allocation;

/**
 * Represents an event where an individual reproduces in the patrol simulation.
 */
public class ReproductionEvent extends IndividualEvent {

    /**
     * Constructs a new ReproductionEvent with the specified simulation and individual.
     *
     * @param simulation The simulation in which the event occurs.
     * @param individual The individual that will reproduce.
     */
    public ReproductionEvent(PatrolSimulation simulation, DistributionIndividual individual) {
        super(simulation, individual);
    }

    /**
     * Executes the reproduction event, triggering the simulation to perform the reproduction operation on the individual.
     */
    public void execute() {
        this.simulation.performReproduction(individual);
    }
}
