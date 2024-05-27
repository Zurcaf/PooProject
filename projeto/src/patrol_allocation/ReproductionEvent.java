package patrol_allocation;

public class ReproductionEvent extends IndividualEvent {
	/**
	 * 
	 * @param simulation
	 * @param individual
	 */
	public ReproductionEvent(PatrolSimulation simulation, DistributionIndividual individual) {
		super(simulation, individual);
	
	}

	public void execute() {
		this.simulation.performReproduction(individual);
	}

}