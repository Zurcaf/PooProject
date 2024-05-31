package patrol_allocation;

/**
 * Represents an event where an observation is performed in the patrol simulation.
 */
public class ObservationEvent extends SimulationEvent {

    /**
     * Constructs a new ObservationEvent with the specified simulation.
     *
     * @param simulation The simulation in which the event occurs.
     */
    ObservationEvent(PatrolSimulation simulation) {
        super(simulation);
    }

    /**
     * Executes the observation event, triggering the simulation to perform the observation.
     */
    public void execute() {
        simulation.performObservation(false);
    }
}
