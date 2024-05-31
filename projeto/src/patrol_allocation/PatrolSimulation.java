package patrol_allocation;

import evolution_simulation.*;
import java.util.*;
import discrete_stochastic_simulation.*;

public class PatrolSimulation {

	static RandomNumberGenerator rng = RandomNumberGenerator.getInstance();

	HashSet<SimulationObserver> observers = new HashSet<SimulationObserver>();
	EvolutionEngine<DistributionIndividual> evolutionEngine;
	PendingEventContainer<SimulationEvent> pec;

	final int[][] timeMatrix;
	final int patrolCount;
	final int systemCount;
	private final double simDuration;
	private final int initialPopulation;
	private final int maxPopulation;
	private final double deathParam;
	private final double reproductionParam;
	private final double mutationParam;

	final boolean improved;

	private final double observationInterval;
	private int observationIndex = 1;
	private TimedEvent<SimulationEvent> nextObservationEvent;

	private DistributionIndividual bestIndividualEver = null;

	private int totalEventCount = 0;

	/**
	 * 
	 * TODO - document parameters
	 */
	public PatrolSimulation(int[][] timeMatrix, double simDuration, int initialPopulation, int maxPopulation, double deathParam, double reproductionParam, double mutationParam, boolean improved) {
		if (timeMatrix.length == 0 || timeMatrix[0].length == 0) {
			throw new IllegalArgumentException("Matrix has 0 size");
		}
		patrolCount = timeMatrix.length;
		systemCount = timeMatrix[0].length;
		for (int[] row : timeMatrix) {
			if (row.length != systemCount) {
				throw new IllegalArgumentException("Matrix row sizes differ");
			}
		}
		if (simDuration <= 0) {
			throw new IllegalArgumentException("The simulation duration must be a positive number");
		}
		if (initialPopulation <= 0) {
			throw new IllegalArgumentException("The initial population count must be a positive integer");
		}
		if (maxPopulation <= 0) {
			throw new IllegalArgumentException("The maximum population count must be a positive integer");
		}
		if (deathParam <= 0 || reproductionParam <= 0 || mutationParam <= 0) {
			throw new IllegalArgumentException("The death, reproduction and mutation parameters must all be positive integers");
		}

		this.timeMatrix = timeMatrix;
		this.simDuration = simDuration;
		this.initialPopulation = initialPopulation;
		this.maxPopulation = maxPopulation;
		this.deathParam = deathParam;
		this.reproductionParam = reproductionParam;
		this.mutationParam = mutationParam;
		this.improved = improved;

		observationInterval = simDuration / 20;

		this.evolutionEngine = new evolution_simulation.DefaultEvolutionEngine<DistributionIndividual>(maxPopulation);
		this.pec = new discrete_stochastic_simulation.PriorityQueuePendingEventContainer<SimulationEvent>();
	}

	public void run() {
		// Initialize the popoulation with randomly distributions
		for (int i = 0; i < initialPopulation; i++) {
			DistributionIndividual individual = new DistributionIndividual(this, Distribution.newRandom(patrolCount, systemCount));
			if (!updateBestIndividualEver(individual)) {
				break;
			}
			prepareIndividual(individual);
			evolutionEngine.addIndividual(individual);
		}
		performObservation(true);

		// Setup the first observation event. A new observation will be scheduled once this event fires
		nextObservationEvent = new TimedEvent<SimulationEvent>(observationInterval, new ObservationEvent(this));
		pec.addEvent(nextObservationEvent);

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
			this,
			observationInterval * observationIndex,
			totalEventCount,
			evolutionEngine.populationCount(),
			evolutionEngine.epidemicCount(),
			evolutionEngine.bestUniqueIndividuals(6)
		);
		emitObservation(observation);
		observationIndex++;
		if (!finalObservation) {
			double time = observationInterval * observationIndex;
			if (time < simDuration) {
				nextObservationEvent = new TimedEvent<SimulationEvent>(time, new ObservationEvent(this));
				pec.addEvent(nextObservationEvent);
			}
		}
	}

	/**
	 * @return whether the simulation should continue
	 */
	private boolean updateBestIndividualEver(DistributionIndividual individual) {
		if (bestIndividualEver == null || individual.comfort() > bestIndividualEver.comfort()) {
			bestIndividualEver = individual;
		}
		if (individual.comfort() >= 1) {
			patrol_allocation.Debug.log("Found individual with comfort = 1. Stopping the simulation!");
			pec.stop();
			return false;
		}
		return true;
	}

	public DistributionIndividual bestIndividualEver() {
		return bestIndividualEver;
	}

	void scheduleReproduction(DistributionIndividual individual) {
		double time = pec.currentEventTime() + rng.getExp((1 - Math.log(individual.comfort())) * reproductionParam);
		if (time < individual.deathTime && time < simDuration) {
			patrol_allocation.Debug.log("Individual " + individual.hashCode() + " will reproduce at " + time);
			TimedEvent<SimulationEvent> event = new TimedEvent<SimulationEvent>(time, new ReproductionEvent(this, individual));
			individual.reproductionEvent = event;
			pec.addEvent(event);
		} else {
			individual.reproductionEvent = null;
		}
	}
	void scheduleMutation(DistributionIndividual individual) {
		double time = pec.currentEventTime() + rng.getExp((1 - Math.log(individual.comfort())) * mutationParam);
		if (time < individual.deathTime && time < simDuration) {
			patrol_allocation.Debug.log("Individual " + individual.hashCode() + " will mutate at " + time);
			TimedEvent<SimulationEvent> event = new TimedEvent<SimulationEvent>(time, new MutationEvent(this, individual));
			individual.mutationEvent = event;
			pec.addEvent(event);
		} else {
			individual.mutationEvent = null;
		}
	}

	void prepareIndividual(DistributionIndividual individual) {
		double comfort = individual.comfort();
		double deathTime = pec.currentEventTime() + rng.getExp((1 - Math.log(1 - comfort)) * deathParam);
		individual.deathTime = deathTime;
		if (deathTime < simDuration) {
			TimedEvent<SimulationEvent> event = new TimedEvent<SimulationEvent>(deathTime, new DeathEvent(this, individual));
			individual.deathEvent = event;
			pec.addEvent(event);
		} else {
			individual.deathEvent = null;
		}
		patrol_allocation.Debug.log("Added individual " + individual.hashCode() + ", which will die at " + deathTime);

		scheduleReproduction(individual);
		scheduleMutation(individual);
	}

	/**
	 * 
	 * @param individual
	 */
	void performDeath(DistributionIndividual individual) {
		patrol_allocation.Debug.log("Individual " + individual.hashCode() + " died");
		totalEventCount++;
		evolutionEngine.removeIndividual(individual);
		if (evolutionEngine.populationCount() == 0) {
			onPopulationExtinct();
		}
	}

	/**
	 * 
	 * @param individual
	 */
	void performReproduction(DistributionIndividual individual) {
		totalEventCount++;
		DistributionIndividual offspring = individual.reproduce();
		patrol_allocation.Debug.log("Individual " + individual.hashCode() + " reproduced, producing individual " + offspring.hashCode());
		if (updateBestIndividualEver(offspring)) {
			prepareIndividual(offspring);
			evolutionEngine.addIndividual(offspring);
			scheduleReproduction(offspring);
		}
	}

	/**
	 * 
	 * @param individual
	 */
	void performMutation(DistributionIndividual individual) {
		patrol_allocation.Debug.log("Individual " + individual.hashCode() + " mutated");
		totalEventCount++;
		individual.mutateInPlace();
		if (updateBestIndividualEver(individual)) {
			scheduleMutation(individual);
		}
	}


	void onPopulationExtinct() {
		patrol_allocation.Debug.log("The population has become extinct!");
		// Removing the next observation event allows the simulation event loop to exit
		pec.removeEvent(nextObservationEvent);
	}

}