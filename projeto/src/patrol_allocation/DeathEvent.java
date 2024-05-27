package patrol_allocation;

import discrete_stochastic_simulation.*;
import evolution_simulation.*;

public class DeathEvent extends IndividualEvent implements Event {

	/**
	 * 
	 * @param simulation
	 * @param individual
	 */
	public DeathEvent(PatrolSimulation simulation, DistributionIndividual individual) {
		super(simulation, individual);
	}

	public void execute() {
		this.simulation.performDeath(this.individual);
	}

}