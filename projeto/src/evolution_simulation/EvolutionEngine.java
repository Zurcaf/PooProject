package evolution_simulation;

import java.util.List;

/**
 * Interface representing an evolution engine that manages a population of individuals,
 * enforcing population limits through epidemics.
 *
 * Individuals can be added to the population and removed from the population at any
 * time. Whe the population size grows above the maximum size, an epidemic happens.
 * Except for the 5 best individuals with unique solutions (as detemined by
 * {@link Individual#isSolutionEqual(Individual)}), each individual has a 1/3
 * probability of getting killed by an epidemic when one happens, in which case the
 * {@link Individual#onEpidemicDeath()} method of the individuals who die is called.
 * The 5 best individuals always survive the epidemic.
 *
 * @param <I> The type of individuals managed by this evolution engine, which must implement the {@link Individual} interface.
 */
public interface EvolutionEngine<I extends Individual<I>> {

    /**
     * Adds an individual to the population. If the population exceeds the maximum limit,
     * an epidemic is triggered to reduce the population.
     *
     * @param individual The individual to be added to the population.
     * @return true if an epidemic was triggered, false otherwise.
     */
    boolean addIndividual(I individual);

    /**
     * Removes an individual from the population.
     *
     * @param individual The individual to be removed from the population.
     */
    void removeIndividual(I individual);

    /**
     * Gets the specified number of best individuals with unique solutions, as determined by
     * {@code Individual.isSolutionEqual(I other)}. The best individuals are those with the highest comfort levels.
     *
     * @param count The number of unique best individuals to retrieve.
     * @return A list of the best unique individuals.
     */
    List<I> bestUniqueIndividuals(int count);

    /**
     * Returns the number of epidemics that have occurred.
     *
     * @return The number of epidemics.
     */
    int epidemicCount();

    /**
     * Returns the current population count.
     *
     * @return The number of individuals in the population.
     */
    int populationCount();
}
