package patrol_allocation;

/**
 * Represents an event action where an individual reproduces.
 */
public class ReproductionEventAction extends IndividualEventAction {

    /**
     * Constructs a new ReproductionEventAction for the specified simulation and individual.
     *
     * @param simulation The simulation in which the event occurs.
     * @param individual The individual that will reproduce.
     */
    public ReproductionEventAction(PatrolSimulation simulation, DistributionIndividual individual) {
        super(simulation, individual);
    }

    /**
     * Executes the reproduction event, triggering the simulation to perform the reproduction operation on the individual.
     */
    public void execute() {
        this.simulation.performReproduction(individual);
    }
}
