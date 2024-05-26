package PEC;

import patrol_allocation_simulator.Individual;
import patrol_allocation_simulator.Simulation;

public class ReproductionEvent extends Event{
    public Individual ind;
    
    public ReproductionEvent(Individual newInd, Simulation ogSim, float newTime){
        super(newTime, ogSim);
        ind = newInd;
    }

    public void execute(){
        mySim.reproduceIndividual(ind);
    }
}
