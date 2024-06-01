package discrete_stochastic_simulation;

import java.util.Arrays;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * A discrete stochastic simulation that uses a priority queue (heap) as the pending event container.
 *
 * @param <A> The type of event action that this container handles, which must extend the {@link EventAction} interface.
 */
public class PriorityQueueDiscreteStochasticSimulation<A extends EventAction> implements DiscreteStochasticSimulation<A>, Iterable<TimedEvent<A>> {

    private PriorityQueue<TimedEvent<A>> pec = new PriorityQueue<TimedEvent<A>>();
    private double currentEventTime = 0;
    private boolean stopped = false;

    /**
     * Removes the specified event.
     *
     * @param oldTimedEvent The event to be removed.
     */
    public void removeEvent(TimedEvent<A> oldTimedEvent) {
        pec.remove(oldTimedEvent);
    }

    /**
     * Adds the specified event.
     *
     * @param event The event to be added.
     */
    public void addEvent(TimedEvent<A> event) {
        pec.add(event);
    }

    /**
     * Starts the execution of events in the container. Events are executed in order of their scheduled times.
     * The execution stops (and this method returns) when there are no more events or when the simulation is stopped using the {@link PriorityQueueDiscreteStochasticSimulation#stop()} method is called.
     */
    public void run() {
        while (!stopped) {
            TimedEvent<A> event = pec.poll();
            if (event == null) break;
            currentEventTime = event.time;
            event.action.execute();
        }
        currentEventTime = -1;
    }

    /**
     * Stops the execution of events.
     */
    public void stop() {
        stopped = true;
    }

    /**
     * Returns the time of the event currently being executed.
     *
     * @return The time of the current event.
     */
    public double currentEventTime() {
        return currentEventTime;
    }

    /**
     * Returns an iterator over the pending events.
     *
     * @return An iterator over the pending events.
     */
    public Iterator<TimedEvent<A>> iterator() {
        return pec.iterator();
    }
}
