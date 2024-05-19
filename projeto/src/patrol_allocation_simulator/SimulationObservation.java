package patrol_allocation_simulator;

import java.util.Set;

public class SimulationObservation {
	final double currentInstant;
	final int eventCount;
	final int epidemicCount;
	final Set<Individual> population;
	final Individual[] bestIndividuals;

	SimulationObservation(double currentInstant, int eventCount, int epidemicCount, Set<Individual> population, Individual[] bestIndividuals) {
		this.currentInstant = currentInstant;
		this.eventCount = eventCount;
		this.epidemicCount = epidemicCount;
		this.population = population;
		this.bestIndividuals = bestIndividuals;
	}

	public double currentInstant() {
		return currentInstant;
	}
	public int eventCount() {
		return eventCount;
	}
	public int epidemicCount() {
		return eventCount;
	}
	public Set<Individual> population() {
		return population;
	}
	public Individual[] bestIndividuals() {
		return bestIndividuals;
	}
}
