package discrete_stochastic_simulation;

import java.util.*;
import java.util.PriorityQueue;

public class PriorityQueuePendingEventContainer<E extends Event> implements PendingEventContainer<E> {

	private PriorityQueue<TimedEvent> pec;
	private double currentEventTime = 0;

	public PriorityQueuePendingEventContainer(){
		pec = new PriorityQueue<TimedEvent>();
	}


	public void removeEvent(TimedEvent oldTimedEvent) {
		pec.remove(oldTimedEvent);
	}	

	/**
	 * 
	 * @param time
	 * @param execution
	 */
	public void addEvent(double time, Event execution) {
		TimedEvent newEvent = new TimedEvent(time, execution);
		pec.add(newEvent);
	}

	public TimedEvent getNextEvent() {
		return pec.poll();
	}

	public void run() {
		while (true) {
			TimedEvent timedEvent = pec.poll();
			if (timedEvent == null) break;
			patrol_allocation.DebugLogger.log("[" + timedEvent.time + "]");
			currentEventTime = timedEvent.time;
			timedEvent.action.execute();
		}
		currentEventTime = -1;
	}

	public double currentEventTime() {
		return currentEventTime;
	}

}