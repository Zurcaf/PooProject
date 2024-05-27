package patrol_allocation;

public class DeathEvent extends IndividualEvent {

	/**
	 * 
	 * @param simulation
	 * @param individual
	 */
	public DeathEvent(PatrolSimulation simulation, DistributionIndividual individual) {
		super(simulation, individual);
	}

	public void execute() {
		this.simulation.performDeath(individual);
	}

}