package evolution_simulation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import java.util.Iterator;
public class DefaultEvolutionEngine<I extends Individual> implements EvolutionEngine<I> {

	private static final Random random = new Random();

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
	 * 
	 * @param count
	 */
	public List<I> bestIndividuals(int count) {
		if (count > population.size()) {
			count = population.size();
		}
	
		List<I> bestIndividuals = new ArrayList<I>();
		for (I individual : population) {
			int i;
			for (i = 0; i < bestIndividuals.size(); i++) {
				if (individual.comfort() > bestIndividuals.get(i).comfort()) {
					bestIndividuals.add(i, individual);
					if (bestIndividuals.size() > count) {
						bestIndividuals.remove(count);
					}
					break;
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
		epidemicCount++;
		List<I> luckyFew = bestIndividuals(5);
		Iterator<I> iterator = population.iterator();
		while (iterator.hasNext()) {
			I individual = iterator.next();
			// An individual who is not in the top 5 has a 1/3 probability of dying
			if (!luckyFew.contains(individual) && random.nextInt(3) < 1) {
				iterator.remove();
			}
		}
	}

	/**
	 * 
	 * @param maxPopulation
	 */
	public DefaultEvolutionEngine(int maxPopulation) {
		this.maxPopulation = maxPopulation;
	}

	public int populationCount() {
		return population.size();
	}

}