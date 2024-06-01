package patrol_allocation;

/**
 * Represents an event action where an observation is performed in the patrol simulation.
 */
public class ObservationEventAction extends SimulationEventAction {

    /**
     * Constructs a new ObservationEventAction for the specified simulation.
     *
     * @param simulation The simulation in which the event occurs.
     */
    ObservationEventAction(PatrolSimulation simulation) {
        super(simulation);
    }

    /**
     * Executes the observation event, triggering the simulation to perform the observation.
     */
    public void execute() {
        simulation.performObservation(false);
    }
}
