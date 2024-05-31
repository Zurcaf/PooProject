package evolution_simulation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import java.util.Iterator;
public class DefaultEvolutionEngine<I extends Individual<I>> implements EvolutionEngine<I> {

	private final Random random;

	private final int maxPopulation;

	private HashSet<I> population = new HashSet<I>();
	private int epidemicCount = 0;

	/**
	 * 
	 * @param individual
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
	 * 
	 * @param individual
	 */
	public void removeIndividual(I individual) {
		population.remove(individual);
	}

	/**
	 * Gets the {@code count} best individuals with unique solutions, as determined by {@code Individual.isSolutionEqual(I other)}.
	 * @param count
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

	public int epidemicCount() {
		return epidemicCount;
	}

	private void doEpidemic() {
		patrol_allocation.Debug.log("Population size exceeded the maximum. Unleashing an epidemic!");
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
	 * 
	 * @param maxPopulation the maximum number of individuals in the population
	 * @param random the random number generator to use when deciding which individuals die during an epidemic
	 */
	public DefaultEvolutionEngine(int maxPopulation, Random random) {
		this.maxPopulation = maxPopulation;
		this.random = random;
	}

	public int populationCount() {
		return population.size();
	}

}