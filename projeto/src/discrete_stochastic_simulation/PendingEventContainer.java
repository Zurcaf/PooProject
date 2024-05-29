package discrete_stochastic_simulation;

public interface PendingEventContainer<A extends EventAction> {

	void removeEvent(TimedEvent<A> event);

	/**
	 * 
	 * @param time
	 * @param execution
	 */
	void addEvent(TimedEvent<A> event);

	public double currentEventTime();

	void run();

}