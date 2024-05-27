package evolution_simulation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DefaultEvolutionEngine<I extends Individual> implements EvolutionEngine<I> {

	private final int maxPopulation;

	private HashSet<I> population = new HashSet<I>();
	private int epidemicCount = 0;

	// TODO - epidemias

	/**
	 * 
	 * @param individual
	 */
	public void addIndividual(I individual) {
		population.add(individual);
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