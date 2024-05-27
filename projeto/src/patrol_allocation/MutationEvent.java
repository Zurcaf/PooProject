package patrol_allocation;

public class MutationEvent extends IndividualEvent {

	/**
	 * 
	 * @param simulation
	 * @param individual
	 */
	public MutationEvent(PatrolSimulation simulation, DistributionIndividual individual) {
		super(simulation, individual);
	}

	public void execute() {
		this.simulation.performMutation(individual);
	}

}