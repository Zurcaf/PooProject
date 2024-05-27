package patrol_allocation;

import evolution_simulation.*;
import java.util.*;
import discrete_stochastic_simulation.*;

public class PatrolSimulation {

	static RandomNumberGenerator rng = RandomNumberGenerator.getInstance();

	HashSet<SimulationObserver> observers = new HashSet<SimulationObserver>();
	private EvolutionEngine<DistributionIndividual> evolutionEngine;
	private PendingEventContainer<SimulationEvent> pec;

	private int[][] timeMatrix;
	private int patrolCount;
	private int systemCount;
	private double simDuration;
	private int initialPopulation;
	private int maxPopulation;
	private double deathParam;
	private double reproductionParam;
	private double mutationParam;

	private int totalEventCount = 0;

	/**
	 * 
	 * TODO - document parameters
	 */
	public PatrolSimulation(int[][] timeMatrix, double simDuration, int initialPopulation, int maxPopulation, double deathParam, double reproductionParam, double mutationParam) {
		if (timeMatrix.length == 0 || timeMatrix[0].length == 0) {
			throw new Error("Matrix has 0 size");
		}
		patrolCount = timeMatrix.length;
		systemCount = timeMatrix[0].length;
		for (int[] row : timeMatrix) {
			if (row.length != systemCount) {
				throw new Error("Matrix row sizes differ");
			}
		}
		if (simDuration <= 0) {
			throw new Error("The simulation duration must be a positive number");
		}
		if (simDuration <= 0) {
			throw new Error("The initial population count must be a positive integer");
		}
		if (maxPopulation <= 0) {
			throw new Error("The maximum population count must be a positive integer");
		}
		// TODO - check other parametes

		this.timeMatrix = timeMatrix;
		this.simDuration = simDuration;
		this.initialPopulation = initialPopulation;
		this.maxPopulation = maxPopulation;
		this.deathParam = deathParam;
		this.reproductionParam = reproductionParam;
		this.mutationParam = mutationParam;

		this.evolutionEngine = new evolution_simulation.DefaultEvolutionEngine<DistributionIndividual>(maxPopulation);
		this.pec = new discrete_stochastic_simulation.PriorityQueuePendingEventContainer<SimulationEvent>();
	}

	public void run() {
		// Initialize the popoulation with randomly distributions
		for (int i = 0; i < initialPopulation; i++) {
			DistributionIndividual individual = new DistributionIndividual(timeMatrix, Distribution.newRandom(patrolCount, systemCount));
			prepareIndividual(individual);
			evolutionEngine.addIndividual(individual);
		}

		// Setup the observation events at the specified interval. The final observation is excluded
		double observationInterval = simDuration / 20;
		for (int i = 1; i < 20; i++) {
			double time = observationInterval * i;
			pec.addEvent(time, new ObservationEvent(this));
		}

		pec.run();

		// Perform the final observation
		performObservation(true);
	}

	private void emitObservation(SimulationObservation observation) {
		for (SimulationObserver observer : observers) {
			observer.onObservation(observation);
		}
	}

	/**
	 * Adds an observer which will be notified of simulation observations.
	 * @param observer
	 */
	public void addObserver(SimulationObserver observer) {
		observers.add(observer);
	}

	void performObservation(boolean finalObservation) {
		SimulationObservation observation = new SimulationObservation(
			finalObservation ? simDuration : pec.currentEventTime(),
			totalEventCount,
			evolutionEngine.populationCount(),
			evolutionEngine.epidemicCount(),
			evolutionEngine.bestIndividuals(6)
		);
		emitObservation(observation);
	}

	void scheduleReproduction(DistributionIndividual individual) {
		double time = pec.currentEventTime() + rng.getExp((1 - Math.log(individual.comfort())) * reproductionParam);
		if (time < individual.deathTime && time < simDuration) {
			patrol_allocation.DebugLogger.log("Individual " + individual.hashCode() + " will reproduce at " + time);
			pec.addEvent(time, new ReproductionEvent(this, individual));
		}
	}
	void scheduleMutation(DistributionIndividual individual) {
		double time = pec.currentEventTime() + rng.getExp((1 - Math.log(individual.comfort())) * mutationParam);
		if (time < individual.deathTime && time < simDuration) {
			patrol_allocation.DebugLogger.log("Individual " + individual.hashCode() + " will mutate at " + time);
			pec.addEvent(time, new MutationEvent(this, individual));
		}
	}

	void prepareIndividual(DistributionIndividual individual) {
		double comfort = individual.comfort();
		double deathTime = pec.currentEventTime() + rng.getExp((1 - Math.log(1 - comfort)) * deathParam);
		individual.deathTime = deathTime;
		if (deathTime < simDuration) {
			pec.addEvent(deathTime, new DeathEvent(this, individual));
		}
		patrol_allocation.DebugLogger.log("Added individual " + individual.hashCode() + ", which will die at " + deathTime);

		scheduleReproduction(individual);
		scheduleMutation(individual);
	}

	/**
	 * 
	 * @param individual
	 */
	void performDeath(DistributionIndividual individual) {
		patrol_allocation.DebugLogger.log("Individual " + individual.hashCode() + " died");
		totalEventCount++;
		evolutionEngine.removeIndividual(individual);
	}

	/**
	 * 
	 * @param individual
	 */
	void performReproduction(DistributionIndividual individual) {
		totalEventCount++;
		DistributionIndividual offspring = individual.reproduce();
		patrol_allocation.DebugLogger.log("Individual " + individual.hashCode() + " reproduced, producing individual " + offspring.hashCode());
		prepareIndividual(offspring);
		evolutionEngine.addIndividual(offspring);
		scheduleReproduction(offspring);
	}

	/**
	 * 
	 * @param individual
	 */
	void performMutation(DistributionIndividual individual) {
		patrol_allocation.DebugLogger.log("Individual " + individual.hashCode() + " mutated");
		totalEventCount++;
		individual.mutateInPlace();
		scheduleMutation(individual);
	}

}