package evolution_simulation;

import java.util.List;

public interface EvolutionEngine<I extends Individual<I>> {

	/**
	 * 
	 * @param individual the individual to add
	 * @return whether an epidemic occurred because of this operation
	 */
	boolean addIndividual(I individual);

	/**
	 * 
	 * @param individual
	 */
	void removeIndividual(I individual);

	/**
	 * 
	 * @param count
	 */
	List<I> bestUniqueIndividuals(int count);

	int epidemicCount();

	int populationCount();

}