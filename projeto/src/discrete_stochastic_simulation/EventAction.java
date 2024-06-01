package discrete_stochastic_simulation;

/**
 * Represents the action of an event (i.e. what happens when an event is fired) in a discrete stochastic simulation.
 */
public interface EventAction {

    /**
     * Executes the action associated with this event.
     */
    void execute();
}
