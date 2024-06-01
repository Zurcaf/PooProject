package evolution_simulation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * DefaultEvolutionEngine is an implementation of the EvolutionEngine interface.
 * It manages a population of individuals and enforces a maximum population limit
 * through periodic epidemics.
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
public class DefaultEvolutionEngine<I extends Individual<I>> implements EvolutionEngine<I> {

    private final Random random;
    private final int maxPopulation;
    private HashSet<I> population = new HashSet<I>();
    private int epidemicCount = 0;

    /**
     * Constructs a new DefaultEvolutionEngine with a specified maximum population and a random number generator.
     *
     * @param maxPopulation The maximum number of individuals in the population.
     * @param random The random number generator to use when deciding which individuals die during an epidemic.
     */
    public DefaultEvolutionEngine(int maxPopulation, Random random) {
        this.maxPopulation = maxPopulation;
        this.random = random;
    }

    /**
     * Adds an individual to the population. If the population exceeds the maximum limit,
     * an epidemic is triggered to reduce the population.
     *
     * @param individual The individual to be added to the population.
     * @return true if an epidemic was triggered, false otherwise.
     */
    public boolean addIndividual(I individual) {
        population.add(individual);
        if (population.size() > maxPopulation) {
            doEpidemic();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes an individual from the population.
     *
     * @param individual The individual to be removed from the population.
     */
    public void removeIndividual(I individual) {
        population.remove(individual);
    }

    /**
     * Gets the specified number of best individuals with unique solutions, as determined by 
     * {@code Individual.isSolutionEqual(I other)}. The best individuals are those with the highest comfort levels.
     *
     * @param count The number of unique best individuals to retrieve.
     * @return A list of the best unique individuals.
     */
    public List<I> bestUniqueIndividuals(int count) {
        if (count > population.size()) {
            count = population.size();
        }
    
        List<I> bestIndividuals = new ArrayList<I>();
        populationLoop:
        for (I individual : population) {
            int i;
            for (i = 0; i < bestIndividuals.size(); i++) {
                if (bestIndividuals.get(i).isSolutionEqual(individual)) {
                    continue populationLoop;
                }
                if (individual.comfort() > bestIndividuals.get(i).comfort()) {
                    bestIndividuals.add(i, individual);
                    if (bestIndividuals.size() > count) {
                        bestIndividuals.remove(count);
                    }
                    continue populationLoop;
                }
            }
            if (i < count) {
                bestIndividuals.add(individual);
            }
        }

        return bestIndividuals;
    }

    /**
     * Returns the number of epidemics that have occurred.
     *
     * @return The number of epidemics.
     */
    public int epidemicCount() {
        return epidemicCount;
    }

    /**
     * Triggers an epidemic to reduce the population size. Individuals who are not among the top 5 best unique individuals
     * have a 1/3 probability of being removed from the population.
     */
    private void doEpidemic() {
        epidemicCount++;
        List<I> luckyFew = bestUniqueIndividuals(5);
        Iterator<I> iterator = population.iterator();
        while (iterator.hasNext()) {
            I individual = iterator.next();
            // An individual who is not in the top 5 has a 1/3 probability of dying
            if (!luckyFew.contains(individual) && random.nextInt(3) < 1) {
                iterator.remove();
                individual.onEpidemicDeath();
            }
        }
    }

    /**
     * Returns the current population count.
     *
     * @return The number of individuals in the population.
     */
    public int populationCount() {
        return population.size();
    }
}
