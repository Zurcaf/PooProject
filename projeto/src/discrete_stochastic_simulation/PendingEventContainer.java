package discrete_stochastic_simulation;

public interface PendingEventContainer<E extends Event> {

	void removeEvent(TimedEvent oldTimedEvent);

	/**
	 * 
	 * @param time
	 * @param execution
	 */
	void addEvent(float time, Event execution);

	TimedEvent getNextEvent();

	int totalEventCount();

}