package patrol_allocation;

import discrete_stochastic_simulation.*;
import evolution_simulation.*;

public class DeathEvent extends IndividualEvent implements Event {

	private Individual individual;

	/**
	 * 
	 * @param simulation
	 * @param individual
	 */
	public DeathEvent(PatrolSimulation simulation, DistributionIndividual individual) {
		super(simulation, individual);
		// TODO - implement DeathEvent.DeathEvent
		throw new UnsupportedOperationException();
	}

	public void execute() {
		// TODO - implement DeathEvent.execute
		throw new UnsupportedOperationException();
	}

}