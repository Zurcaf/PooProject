package PEC;

import patrol_allocation_simulator.Simulation;


public class ObservationEvent extends Event{

    public ObservationEvent(float newTime, Simulation ogSim){
        super(newTime,ogSim);
    }

    public void execute(){
        mySim.emitObservation();
    }
}
