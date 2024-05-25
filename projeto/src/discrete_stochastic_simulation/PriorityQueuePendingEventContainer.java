package discrete_stochastic_simulation;

import java.util.*;

public class PriorityQueuePendingEventContainer<E extends Event> implements PendingEventContainer<E> {

	private Collection<TimedEvent> timedEvents;

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
		// TODO - implement PriorityQueuePendingEventContainer.addEvent
		throw new UnsupportedOperationException();
	}

	public TimedEvent getNextEvent() {
		// TODO - implement PriorityQueuePendingEventContainer.getNextEvent
		throw new UnsupportedOperationException();
	}

	public int totalEventCount() {
		// TODO - implement PriorityQueuePendingEventContainer.totalEventCount
		throw new UnsupportedOperationException();
	}

}