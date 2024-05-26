package PEC;

import patrol_allocation_simulator.Simulation;

public abstract class Event implements Comparable<Event>{
    public float time;
    protected final Simulation mySim;

    public Event(float simTime, Simulation ogSim){
        time = simTime;
        mySim = ogSim;
    }

    public float getTime(){
        return this.time;
    }    
    
    public abstract void execute();

    @Override
    public int compareTo(Event other){
        return Float.compare(this.time, other.getTime());
    }
}
