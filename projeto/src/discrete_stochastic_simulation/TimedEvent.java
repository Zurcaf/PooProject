package discrete_stochastic_simulation;

public class TimedEvent implements Comparable<TimedEvent>{

	Event action;
	private float time;

	public TimedEvent(float newTime, Event newAction){
		time = newTime;
		action = newAction;
	}

	@Override
    public int compareTo(TimedEvent other){
        return Float.compare(this.time, other.getTime());
    }

	public float getTime(){
		return this.time;
	}

}