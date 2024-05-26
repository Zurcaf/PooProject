package discrete_stochastic_simulation;

public class TimedEvent {

	Event action;
	private float time;

	public TimedEvent(float newTime, Event newAction){
		time = newTime;
		action = newAction;
	}

}