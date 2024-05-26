package PEC;

import patrol_allocation_simulator.Individual;
import patrol_allocation_simulator.Simulation;

public class MutationEvent extends Event{
    public Individual ind;

    public MutationEvent(Individual newInd, Simulation ogSim, float newTime){
        super(newTime, ogSim);
        ind = newInd;
    }

    public void execute(){
        ind.mutationInPlace();
    }
}
