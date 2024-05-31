package discrete_stochastic_simulation;

/**
 * Represents an event that is scheduled to occur at a specific time in a discrete stochastic simulation.
 *
 * @param <A> The type of event action that this timed event handles, which must extend the {@link EventAction} interface.
 */
public class TimedEvent<A extends EventAction> implements Comparable<TimedEvent<A>> {

    A action;
    double time;

    /**
     * Constructs a new TimedEvent with the specified time and action.
     *
     * @param newTime The time at which the event is scheduled to occur.
     * @param newAction The action to be executed when the event occurs.
     */
    public TimedEvent(double newTime, A newAction) {
        time = newTime;
        action = newAction;
    }

    /**
     * Compares this timed event with another timed event for order based on their scheduled times.
     *
     * @param other The other timed event to be compared.
     * @return A negative integer, zero, or a positive integer as this event is scheduled to occur
     *         before, at the same time, or after the specified event.
     */
    public int compareTo(TimedEvent<A> other) {
        return Double.compare(this.time, other.time);
    }
}
