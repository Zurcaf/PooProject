package discrete_stochastic_simulation;

public interface PendingEventContainer<E extends Event> {

	void removeEvent(TimedEvent oldTimedEvent);

	/**
	 * 
	 * @param time
	 * @param execution
	 */
	void addEvent(double time, Event execution);

	TimedEvent getNextEvent();
	public double currentEventTime();

	void run();

}