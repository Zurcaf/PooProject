package patrol_allocation;

/**
 * Represents an event where an individual in the patrol simulation dies.
 * This event triggers the death of a specified individual in the simulation.
 */
public class DeathEventAction extends IndividualEventAction {

    /**
     * Constructs a new DeathEvent with the specified simulation and individual.
     *
     * @param simulation The simulation in which the event occurs.
     * @param individual The individual who is affected by the death event.
     */
    public DeathEventAction(PatrolSimulation simulation, DistributionIndividual individual) {
        super(simulation, individual);
    }

    /**
     * Executes the death event, triggering the simulation to perform the death operation on the individual.
     */
    public void execute() {
        this.simulation.performDeath(individual);
    }
}
