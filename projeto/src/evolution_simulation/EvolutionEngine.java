package evolution_simulation;

import java.util.List;

/**
 * Interface representing an evolution engine that manages a population of individuals,
 * enforcing population limits and selecting the best individuals based on specific criteria.
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
