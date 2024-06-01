package patrol_allocation;

/**
 * Represents an event action where an individual's distribution undergoes a mutation in the patrol simulation.
 */
public class MutationEventAction extends IndividualEventAction {

    /**
     * Constructs a new MutationEventAction for the specified simulation and individual.
     *
     * @param simulation The simulation in which the event occurs.
     * @param individual The individual whose distribution is to be mutated.
     */
    public MutationEventAction(PatrolSimulation simulation, DistributionIndividual individual) {
        super(simulation, individual);
    }

    /**
     * Executes the mutation event, triggering the simulation to perform the mutation on the individual's distribution.
     */
    public void execute() {
        this.simulation.performMutation(individual);
    }
}
