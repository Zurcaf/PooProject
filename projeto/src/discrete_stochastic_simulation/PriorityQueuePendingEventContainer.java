package discrete_stochastic_simulation;

import java.util.Arrays;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * A container for pending events in a discrete stochastic simulation that uses a priority queue to manage events.
 *
 * @param <A> The type of event action that this container handles, which must extend the {@link EventAction} interface.
 */
public class PriorityQueuePendingEventContainer<A extends EventAction> implements PendingEventContainer<A>, Iterable<TimedEvent<A>> {

    private PriorityQueue<TimedEvent<A>> pec = new PriorityQueue<TimedEvent<A>>();
    private double currentEventTime = 0;
    private boolean stopped = false;

    /**
     * Removes the specified event from the container.
     *
     * @param oldTimedEvent The event to be removed.
     */
    public void removeEvent(TimedEvent<A> oldTimedEvent) {
        pec.remove(oldTimedEvent);
    }

    /**
     * Adds the specified event to the container.
     *
     * @param event The event to be added.
     */
    public void addEvent(TimedEvent<A> event) {
        pec.add(event);
    }

    /**
     * Starts the execution of events in the container. Events are executed in order of their scheduled times.
     * The execution stops when there are no more events or when the container is stopped.
     * This method logs the current event time and the next 10 events left in the queue.
     */
    public void run() {
        while (!stopped) {
            TimedEvent<A> event = pec.poll();
            if (event == null) break;
            patrol_allocation.Debug.log("\n[" + event.time + "]");
            currentEventTime = event.time;
            event.action.execute();
            patrol_allocation.Debug.log("Next 10 events left in queue: " + Arrays.deepToString(pec.stream().limit(10).map(ev -> ev.time + " " + ev.action.getClass().getName()).toArray()));
        }
        currentEventTime = -1;
    }

    /**
     * Stops the execution of events in the container.
     */
    public void stop() {
        stopped = true;
    }

    /**
     * Returns the current event time.
     *
     * @return The time of the current event.
     */
    public double currentEventTime() {
        return currentEventTime;
    }

    /**
     * Returns an iterator over the pending events in the container.
     *
     * @return An iterator over the pending events.
     */
    public Iterator<TimedEvent<A>> iterator() {
        return pec.iterator();
    }
}
