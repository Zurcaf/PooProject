package patrol_allocation;

import evolution_simulation.*;
import java.util.*;
import discrete_stochastic_simulation.*;

/**
 * Represents a patrol simulation where a population of individuals (patrol distributions) evolves over time.
 * 
 * Simulations emit periodic observations (see {@link SimulationObservation}) with a period of
 * 1/20th of the simulation duration. You can register a {@link SimulationObserver} to access the
 * observations as they happen.
 */
public class PatrolSimulation {

    final Random random;
    final RandomHelper randomHelper;

    final HashSet<SimulationObserver> observers = new HashSet<SimulationObserver>();
    final EvolutionEngine<DistributionIndividual> evolutionEngine;
    final DiscreteStochasticSimulation<SimulationEventAction> pec;

    final int patrolCount;
    final int systemCount;
    final int[][] timeMatrix;
    final double policingTimeLowerBound;
    private final double simDuration;
    private final int initialPopulation;
    private final double deathParam;
    private final double reproductionParam;
    private final double mutationParam;

    private final double observationInterval;
    private int observationIndex = 1;
    private TimedEvent<SimulationEventAction> nextObservationEvent;

    /** The best distribution found so far. This is updated by {@link PatrolSimulation#updateBestDistributionEver(Distribution)}. */
    private Distribution bestDistributionEver = null;

    private int totalEventCount = 0;

    /**
     * Constructs a new simulation with the specified parameters.
     *
     * @param timeMatrix The time matrix representing the time required for patrols to reach each system (C).
     * @param simDuration The final instant of the simulation (tau).
     * @param initialPopulation The number of individuals in the initial population (nu).
     * @param maxPopulation The maximum number of individuals in the population (nu_max).
     * @param deathParam Parameter controlling the death rate of individuals (mu).
     * @param reproductionParam Parameter controlling the reproduction rate of individuals (rho).
     * @param mutationParam Parameter controlling the mutation rate of individuals (delta).
     * @param random The random number generator to be used by all operations which require randomness in this simulation.
     */
    public PatrolSimulation(int[][] timeMatrix, double simDuration, int initialPopulation, int maxPopulation, double deathParam, double reproductionParam, double mutationParam, Random random) {
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
        if (simDuration <= 0.0) {
            throw new IllegalArgumentException("The simulation duration must be a positive number");
        }
        if (initialPopulation <= 0) {
            throw new IllegalArgumentException("The initial population count must be a positive integer");
        }
        if (maxPopulation <= 0) {
            throw new IllegalArgumentException("The maximum population count must be a positive integer");
        }
        if (initialPopulation > maxPopulation) {
            throw new IllegalArgumentException("The initial population count must be less than or equal to the maximum population count");
        }
        if (deathParam <= 0.0 || reproductionParam <= 0.0 || mutationParam <= 0.0) {
            throw new IllegalArgumentException("The death, reproduction and mutation parameters must all be positive integers");
        }

        this.timeMatrix = timeMatrix;
        this.simDuration = simDuration;
        this.initialPopulation = initialPopulation;
        this.deathParam = deathParam;
        this.reproductionParam = reproductionParam;
        this.mutationParam = mutationParam;
        this.policingTimeLowerBound = calculatePolicingTimeLowerBound();
        if (this.policingTimeLowerBound <= 0.0) {
            throw new IllegalArgumentException("The matrix must not contain null entries on all columns");
        }

        this.observationInterval = simDuration / 20;

        this.evolutionEngine = new evolution_simulation.DefaultEvolutionEngine<DistributionIndividual>(maxPopulation, random);
        this.pec = new discrete_stochastic_simulation.PriorityQueueDiscreteStochasticSimulation<SimulationEventAction>();

        this.random = random;
        this.randomHelper = new RandomHelper(random);
    }

    /**
     * Calculates a lower bound for the best possible policing time, used to calculate the comfort of the individuals.
     * @return A lower bound for the best possible policing time.
     */
    private double calculatePolicingTimeLowerBound() {
        int sumMin = 0;
        for (int i = 0; i < systemCount; i++) {
            int min = timeMatrix[0][i];
            for (int j = 1; j < patrolCount; j++) {
                if (timeMatrix[j][i] < min) {
                    min = timeMatrix[j][i];
                }
            }
            sumMin += min;
        }
        return (double) sumMin / patrolCount;
    }

    /**
     * Runs the simulation. The {@link SimulationObserver#onObservation(SimulationObservation)} method
     * of registered observers will be called while this method is running.
     */
    public void run() {
        // Initialize the population with random distributions
        for (int i = 0; i < initialPopulation; i++) {
            DistributionIndividual individual = new DistributionIndividual(this, Distribution.newRandom(this));
            evolutionEngine.addIndividual(individual);
            if (updateBestDistributionEver(individual.distribution())) break;
            prepareIndividual(individual);
        }

        // Setup the first observation event. A new observation will be scheduled once this event fires
        nextObservationEvent = new TimedEvent<SimulationEventAction>(observationInterval, new ObservationEventAction(this));
        pec.addEvent(nextObservationEvent);

        // Run the simulation event loop
        // If this individual has a comfort of 1, pec.stop() was already called, so this should do nothing.
        pec.run();

        // Perform the final observation
        performObservation(true);
    }

    /**
     * Emits an observation to all registered observers.
     *
     * @param observation The observation to be emitted.
     */
    private void emitObservation(SimulationObservation observation) {
        for (SimulationObserver observer : observers) {
            observer.onObservation(observation);
        }
    }

    /**
     * Adds an observer which will be notified of simulation observations.
     *
     * @param observer The observer to be added.
     */
    public void addObserver(SimulationObserver observer) {
        observers.add(observer);
    }

    /**
     * Performs an observation, collecting data about the current state of the simulation.
     *
     * @param finalObservation Indicates if this is the final observation of the simulation.
     */
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
                nextObservationEvent = new TimedEvent<SimulationEventAction>(time, new ObservationEventAction(this));
                pec.addEvent(nextObservationEvent);
            }
        }
    }

    /**
     * Updates the best distribution ever found in the simulation.
     *
     * @param distribution The distribution to be compared.
     * @return true if the distribution has a comfort level of 1, indicating the simulation should stop.
     */
    private boolean updateBestDistributionEver(Distribution distribution) {
        if (bestDistributionEver == null || distribution.comfort() > bestDistributionEver.comfort()) {
            bestDistributionEver = distribution;
        }
        if (distribution.comfort() >= 1) {
            pec.stop();
            return true;
        }
        return false;
    }

    /**
     * Returns the best distribution ever found in the simulation.
     *
     * @return The best distribution ever found.
     */
    public Distribution bestDistributionEver() {
        return bestDistributionEver;
    }

    /**
     * Schedules a reproduction event for the specified individual.
     *
     * @param individual The individual to reproduce.
     */
    private void scheduleReproduction(DistributionIndividual individual) {
        double time = pec.currentEventTime() + randomHelper.getExp((1 - Math.log(individual.comfort())) * reproductionParam);
        if (time < individual.deathTime && time < simDuration) {
            TimedEvent<SimulationEventAction> event = new TimedEvent<SimulationEventAction>(time, new ReproductionEventAction(this, individual));
            individual.reproductionEvent = event;
            pec.addEvent(event);
        } else {
            individual.reproductionEvent = null;
        }
    }

    /**
     * Schedules a mutation event for the specified individual.
     *
     * @param individual The individual to mutate.
     */
    private void scheduleMutation(DistributionIndividual individual) {
        double time = pec.currentEventTime() + randomHelper.getExp((1 - Math.log(individual.comfort())) * mutationParam);
        if (time < individual.deathTime && time < simDuration) {
            TimedEvent<SimulationEventAction> event = new TimedEvent<SimulationEventAction>(time, new MutationEventAction(this, individual));
            individual.mutationEvent = event;
            pec.addEvent(event);
        } else {
            individual.mutationEvent = null;
        }
    }

    /**
     * Prepares an individual by scheduling its death, reproduction, and mutation events.
     *
     * @param individual The individual to prepare.
     */
    private void prepareIndividual(DistributionIndividual individual) {
        double comfort = individual.comfort();
        double deathTime = pec.currentEventTime() + randomHelper.getExp((1 - Math.log(1 - comfort)) * deathParam);
        individual.deathTime = deathTime;
        if (deathTime < simDuration) {
            TimedEvent<SimulationEventAction> event = new TimedEvent<SimulationEventAction>(deathTime, new DeathEventAction(this, individual));
            individual.deathEvent = event;
            pec.addEvent(event);
        } else {
            individual.deathEvent = null;
        }

        scheduleReproduction(individual);
        scheduleMutation(individual);
    }

    /**
     * Removes an individual from the population. Called by {@link DeathEventAction}.
     *
     * @param individual The individual that dies.
     */
    void performDeath(DistributionIndividual individual) {
        totalEventCount++;
        evolutionEngine.removeIndividual(individual);
        if (evolutionEngine.populationCount() == 0) {
            onPopulationExtinct();
        }
    }

    /**
     * Reproduces the specified individual and schedules a new reproduction event. Called by {@link ReproductionEventAction}.
     *
     * @param individual The individual that reproduces.
     */
    void performReproduction(DistributionIndividual individual) {
        totalEventCount++;
        DistributionIndividual offspring = individual.reproduce();
        evolutionEngine.addIndividual(offspring);
        if (updateBestDistributionEver(offspring.distribution())) return;
        prepareIndividual(offspring);
    }

    /**
     * Mutates the specified individual and schedules a new mutation event. Called by {@link MutationEventAction}.
     *
     * @param individual The individual that mutates.
     */
    void performMutation(DistributionIndividual individual) {
        totalEventCount++;
        individual.mutateInPlace();
        if (updateBestDistributionEver(individual.distribution())) return;
        scheduleMutation(individual);
    }

    /**
     * Handles the event when the population becomes extinct.
     */
    void onPopulationExtinct() {
        // Removing the next observation event allows the simulation event loop to exit
        pec.removeEvent(nextObservationEvent);
    }
}
