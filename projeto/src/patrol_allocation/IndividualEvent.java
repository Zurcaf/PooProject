package patrol_allocation;

/**
 * Represents an event in the patrol simulation that involves a specific individual.
 * This is an abstract class that other individual-specific event classes can extend.
 */
public abstract class IndividualEvent extends SimulationEvent {

    protected DistributionIndividual individual;

    /**
     * Constructs a new IndividualEvent with the specified simulation and individual.
     *
     * @param simulation The simulation in which the event occurs.
     * @param individual The individual who is involved in the event.
     */
    public IndividualEvent(PatrolSimulation simulation, DistributionIndividual individual) {
        super(simulation);
        this.individual = individual;
    }
}
