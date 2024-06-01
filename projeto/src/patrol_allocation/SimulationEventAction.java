package patrol_allocation;

import discrete_stochastic_simulation.EventAction;

/**
 * Represents an abstract base class for event actions that occur in a patrol simulation.
 * All simulation events must extend this class and implement the {@link EventAction} interface.
 */
public abstract class SimulationEventAction implements EventAction {

    protected final PatrolSimulation simulation;

    /**
     * Constructs a new SimulationEventAction for the specified simulation.
     *
     * @param simulation The simulation in which the event occurs.
     */
    public SimulationEventAction(PatrolSimulation simulation) {
        this.simulation = simulation;
    }
}
