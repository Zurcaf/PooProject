package patrol_allocation;

import discrete_stochastic_simulation.EventAction;

/**
 * Represents an abstract base class for events that occur in the patrol simulation.
 * All simulation events must extend this class and implement the {@link EventAction} interface.
 */
public abstract class SimulationEvent implements EventAction {

    protected final PatrolSimulation simulation;

    /**
     * Constructs a new SimulationEvent with the specified simulation.
     *
     * @param simulation The simulation in which the event occurs.
     */
    public SimulationEvent(PatrolSimulation simulation) {
        this.simulation = simulation;
    }
}
