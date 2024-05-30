package discrete_stochastic_simulation;

import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Iterator;

public class PriorityQueuePendingEventContainer<A extends EventAction> implements PendingEventContainer<A>, Iterable<TimedEvent<A>> {

	private PriorityQueue<TimedEvent<A>> pec;
	private double currentEventTime = 0;

	public PriorityQueuePendingEventContainer(){
		pec = new PriorityQueue<TimedEvent<A>>();
	}


	public void removeEvent(TimedEvent<A> oldTimedEvent) {
		pec.remove(oldTimedEvent);
	}	

	/**
	 * 
	 * @param time
	 * @param execution
	 */
	public void addEvent(TimedEvent<A> event) {
		pec.add(event);
	}

	public void run() {
		while (true) {
			TimedEvent<A> event = pec.poll();
			if (event == null) break;
			patrol_allocation.DebugLogger.log("\n[" + event.time + "]");
			currentEventTime = event.time;
			event.action.execute();
			patrol_allocation.DebugLogger.log("Next 10 events left in queue: " + Arrays.deepToString(pec.stream().limit(10).map(ev -> ev.time + " " + ev.action.getClass().getName()).toArray()));
		}
		currentEventTime = -1;
	}

	public double currentEventTime() {
		return currentEventTime;
	}

	public Iterator<TimedEvent<A>> iterator() {
		return pec.iterator();
	}

}