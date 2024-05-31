package discrete_stochastic_simulation;

public class TimedEvent<A extends EventAction> implements Comparable<TimedEvent<A>> {

	A action;
	double time;

	public TimedEvent(double newTime, A newAction){
		time = newTime;
		action = newAction;
	}

	public int compareTo(TimedEvent<A> other){
		return Double.compare(this.time, other.time);
	}

}