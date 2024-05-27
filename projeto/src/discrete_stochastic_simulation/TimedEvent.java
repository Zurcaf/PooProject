package discrete_stochastic_simulation;

public class TimedEvent implements Comparable<TimedEvent>{

	Event action;
	double time;

	public TimedEvent(double newTime, Event newAction){
		time = newTime;
		action = newAction;
	}

	public int compareTo(TimedEvent other){
		return Double.compare(this.time, other.time);
	}

}