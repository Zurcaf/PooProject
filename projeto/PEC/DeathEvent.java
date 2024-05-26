package PEC;

import patrol_allocation_simulator.Individual;
import patrol_allocation_simulator.Simulation;

public class DeathEvent extends Event{
    public Individual ind;
    
    public DeathEvent(Individual newInd, Simulation ogSim, float newTime){
        super(newTime, ogSim);
        ind = newInd;
    }

    public void execute(){
        mySim.removeIndividual(ind);
    }
}
