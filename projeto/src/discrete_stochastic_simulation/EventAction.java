package discrete_stochastic_simulation;

/**
 * Represents an action that can be executed during an event in a discrete stochastic simulation.
 */
public interface EventAction {

    /**
     * Executes the action associated with this event.
     */
    void execute();
}
