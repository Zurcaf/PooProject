package patrol_allocation;

import evolution_simulation.*;

public class MutationEvent extends IndividualEvent {

	private Individual individual;

	/**
	 * 
	 * @param simulation
	 * @param individual
	 */
	public MutationEvent(PatrolSimulation simulation, DistributionIndividual individual) {
		super(simulation, individual);
	}

	public void execute() {
		this.individual.mutateInPlace();
		this.simulation.performMutation(this.individual);
	}

}