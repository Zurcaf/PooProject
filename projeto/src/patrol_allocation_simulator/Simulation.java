package patrol_allocation_simulator;

import java.util.HashSet;

public class Simulation {
	int[][] timeMatrix;
	double simDuration;
	int initialPopulation;
	int maxPopulation;
	double deathParam;
	double reproductionParam;
	double mutationParam;

	private HashSet<Individual> population = new HashSet<Individual>();

	private HashSet<SimulationObserver> observationObservers = new HashSet<SimulationObserver>();

	public Simulation(
		int[][] timeMatrix,
		double simDuration,
		int initialPopulation,
		int maxPopulation,
		double deathParam,
		double reproductionParam,
		double mutationParam
	) {
		this.timeMatrix = timeMatrix;
		this.simDuration = simDuration;
		this.initialPopulation = initialPopulation;
		this.maxPopulation = maxPopulation;
		this.deathParam = deathParam;
		this.reproductionParam = reproductionParam;
		this.mutationParam = mutationParam;
	}

	public void run() {
		// TODO

		// Example for emitting simulation statistics
		for (int i = 0; i < 10; i++)
			population.add(new Individual());
		SimulationObservation observation = new SimulationObservation(3.1415926535897932, 123, 456, population, new Individual[]{new Individual(), new Individual(), new Individual(), new Individual(), new Individual(), new Individual()});
		emitObservation(observation);
	}

	public void subscribeToObservations(SimulationObserver observer) {
		observationObservers.add(observer);
	}
	public void unsubscribeToObservations(SimulationObserver observer) {
		observationObservers.remove(observer);
	}
	private void emitObservation(SimulationObservation observation) {
		for (SimulationObserver observer : observationObservers) {
			observer.update(observation);
		}
	}
}
