package patrol_allocation;

/**
 * Represents an event action in the patrol simulation that involves a specific individual.
 * This is an abstract class that other specific event action classes can extend.
 */
public abstract class IndividualEventAction extends SimulationEventAction {

    protected DistributionIndividual individual;

    /**
     * Constructs a new IndividualEventAction for the specified simulation and individual.
     *
     * @param simulation The simulation in which the event occurs.
     * @param individual The individual who is involved in the event.
     */
    public IndividualEventAction(PatrolSimulation simulation, DistributionIndividual individual) {
        super(simulation);
        this.individual = individual;
    }
}
