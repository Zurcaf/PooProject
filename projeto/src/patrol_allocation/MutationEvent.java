package patrol_allocation;

/**
 * Represents an event where an individual's distribution undergoes a mutation in the patrol simulation.
 */
public class MutationEvent extends IndividualEvent {

    /**
     * Constructs a new MutationEvent with the specified simulation and individual.
     *
     * @param simulation The simulation in which the event occurs.
     * @param individual The individual whose distribution is to be mutated.
     */
    public MutationEvent(PatrolSimulation simulation, DistributionIndividual individual) {
        super(simulation, individual);
    }

    /**
     * Executes the mutation event, triggering the simulation to perform the mutation on the individual's distribution.
     */
    public void execute() {
        this.simulation.performMutation(individual);
    }
}
