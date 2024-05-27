package patrol_allocation;

import discrete_stochastic_simulation.Event;

public abstract class SimulationEvent implements Event {

	protected final PatrolSimulation simulation;

	/**
	 * 
	 * @param simulation
	 */
	public SimulationEvent(PatrolSimulation simulation) {
		this.simulation = simulation;
	}

}