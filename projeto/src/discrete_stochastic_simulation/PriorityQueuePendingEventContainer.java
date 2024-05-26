package discrete_stochastic_simulation;

import java.util.*;
import java.util.PriorityQueue;

public class PriorityQueuePendingEventContainer<E extends Event> implements PendingEventContainer<E> {

	private PriorityQueue<TimedEvent> pec;
	private int evCount = 0;

	public PriorityQueuePendingEventContainer(){
		pec = new PriorityQueue<TimedEvent>();
	}

	public TimedEvent removeEvent() {
		// TODO - implement PriorityQueuePendingEventContainer.removeEvent
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param time
	 * @param execution
	 */
	public void addEvent(float time, Event execution) {
		TimedEvent newEvent = new TimedEvent(time, execution);
		pec.add(newEvent);
	}

	public TimedEvent getNextEvent() {
		// TODO - implement PriorityQueuePendingEventContainer.getNextEvent
		throw new UnsupportedOperationException();
	}

	public int totalEventCount() {
		return evCount;
	}

}