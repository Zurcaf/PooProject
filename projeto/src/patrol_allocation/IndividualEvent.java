package patrol_allocation;

public abstract class IndividualEvent extends SimulationEvent {

	protected DistributionIndividual individual;

	/**
	 * 
	 * @param simulation
	 * @param individual
	 */
	public IndividualEvent(PatrolSimulation simulation, DistributionIndividual individual) {
		super(simulation);
		this.individual = individual;
	}

}