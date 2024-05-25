package patrol_allocation;

import evolution_simulation.*;
import java.util.*;
import discrete_stochastic_simulation.*;

public class PatrolSimulation {

	HashSet<SimulationObserver> observers = new HashSet<SimulationObserver>();
	private EvolutionEngine<DistributionIndividual> evolutionEngine;
	private PendingEventContainer<SimulationEvent> pec;

	private int[][] timeMatrix;
	private float simDuration;
	private int initialPopulation;
	private int maxPopulation;
	private float deathParam;
	private float reproductionParam;
	private float mutationParam;


	/**
	 * 
	 * TODO - document parameters
	 */
	public PatrolSimulation(int[][] timeMatrix, float simDuration, int initialPopulation, int maxPopulation, float deathParam, float reproductionParam, float mutationParam) {
		this.timeMatrix = timeMatrix;
		this.simDuration = simDuration;
		this.initialPopulation = initialPopulation;
		this.maxPopulation = maxPopulation;
		this.deathParam = deathParam;
		this.reproductionParam = reproductionParam;
		this.mutationParam = mutationParam;

		this.evolutionEngine = new evolution_simulation.DefaultEvolutionEngine<>(maxPopulation);
		this.pec = new discrete_stochastic_simulation.PriorityQueuePendingEventContainer<SimulationEvent>();
	}

	public void run() {
		performObservation();

		// TODO - implement PatrolSimulation.run
		throw new UnsupportedOperationException();
	}

	private void emitObservation(SimulationObservation observation) {
		for (SimulationObserver observer : observers) {
			observer.onObservation(observation);
		}
	}

	/**
	 * 
	 * @param observer
	 */
	public void addObserver(SimulationObserver observer) {
		observers.add(observer);
	}

	void performObservation() {
		// TODO - Example for emitting simulation statistics
		for (int i = 0; i < 10; i++)
			evolutionEngine.addIndividual(new DistributionIndividual(new Distribution(new int[][] {{1,3,4,6},{2,5},{}})));
		SimulationObservation observation = new SimulationObservation((float) 3.1415926535897932, 123, 456, evolutionEngine.populationCount(), evolutionEngine.bestIndividuals(6));
		emitObservation(observation);
	}

	/**
	 * 
	 * @param individual
	 */
	void performDeath(DistributionIndividual individual) {
		// TODO - implement PatrolSimulation.performDeath
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param individual
	 */
	void performReproduction(DistributionIndividual individual) {
		// TODO - implement PatrolSimulation.performReproduction
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param individual
	 */
	void performMutation(DistributionIndividual individual) {
		// TODO - implement PatrolSimulation.performMutation
		throw new UnsupportedOperationException();
	}

}