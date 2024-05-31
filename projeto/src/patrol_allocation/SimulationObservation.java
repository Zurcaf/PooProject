package patrol_allocation;

import java.util.List;

public class SimulationObservation {

	private final PatrolSimulation simulation;
	private final double instant;
	private final int totalEventCount;
	private final int populationCount;
	private final int epidemicCount;
	private final List<DistributionIndividual> bestIndividuals;

	SimulationObservation(
		PatrolSimulation simulation,
		double instant,
		int totalEventCount,
		int populationCount,
		int epidemicCount,
		List<DistributionIndividual> bestIndividuals
	) {
		this.simulation = simulation;
		this.instant = instant;
		this.totalEventCount = totalEventCount;
		this.populationCount = populationCount;
		this.epidemicCount = epidemicCount;
		this.bestIndividuals = bestIndividuals;
	}

	/** @return The simulation this observation belongs to */
	public PatrolSimulation simulation() {
		return simulation;
	}

	public double currentInstant() {
		return instant;
	}

	public int totalEventCount() {
		return totalEventCount;
	}

	public int populationCount() {
		return populationCount;
	}

	public int epidemicCount() {
		return epidemicCount;
	}

	public List<DistributionIndividual> bestIndividuals() {
		return bestIndividuals;
	}

}