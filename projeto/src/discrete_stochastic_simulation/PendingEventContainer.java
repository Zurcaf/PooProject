package discrete_stochastic_simulation;

/**
 * Interface representing a container for pending events in a discrete stochastic simulation.
 *
 * @param <A> The type of event action that this container handles, which must extend the {@link EventAction} interface.
 */
public interface PendingEventContainer<A extends EventAction> {

    /**
     * Removes the specified event from the container.
     *
     * @param event The event to be removed.
     */
    void removeEvent(TimedEvent<A> event);

    /**
     * Adds the specified event to the container.
     *
     * @param event The event to be added.
     */
    void addEvent(TimedEvent<A> event);

    /**
     * Returns the current event time.
     *
     * @return The time of the current event.
     */
    double currentEventTime();

    /**
     * Starts the execution of events in the container.
     */
    void run();

    /**
     * Stops the execution of events in the container.
     */
    void stop();
}
