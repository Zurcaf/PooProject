package evolution_simulation;

import java.util.List;

public interface EvolutionEngine<I extends Individual> {

	/**
	 * 
	 * @param individual
	 */
	void addIndividual(I individual);

	/**
	 * 
	 * @param individual
	 */
	void removeIndividual(I individual);

	/**
	 * 
	 * @param count
	 */
	List<I> bestIndividuals(int count);

	int epidemicCount();

	int populationCount();

}