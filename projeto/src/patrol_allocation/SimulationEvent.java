package patrol_allocation;

import discrete_stochastic_simulation.EventAction;

public abstract class SimulationEvent implements EventAction {

	protected final PatrolSimulation simulation;

	/**
	 * 
	 * @param simulation
	 */
	public SimulationEvent(PatrolSimulation simulation) {
		this.simulation = simulation;
	}

}