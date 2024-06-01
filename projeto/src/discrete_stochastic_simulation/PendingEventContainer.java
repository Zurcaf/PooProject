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
     * Returns the time of the event currently being executed.
     *
     * @return The time of the current event.
     */
    double currentEventTime();

    /**
     * Starts the execution of events. This function should return whenever there are no longer any events left to process or
     * when the {@link PendingEventContainer#stop()} method is called. If {@link PendingEventContainer#stop()} was already called,
     * this method should do nothing and return immediately.
     */
    void run();

    /**
     * Stops the execution of events. No events should be processed after this function is called.
     */
    void stop();
}
