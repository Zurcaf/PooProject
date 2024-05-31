package evolution_simulation;

/**
 * Interface representing an individual in the evolution simulation. 
 * Each individual must provide methods to evaluate its comfort, 
 * determine if its solution is equal to the solution of another
 * individual of the same type and respond to an epidemic death.
 *
 * @param <I> The type of individual, which must implement the {@link Individual} interface.
 */
public interface Individual<I extends Individual<I>> {

    /**
     * Gets the comfort level (or fitness) of this individual. This is used to find the best
     * individuals among the population.
     *
     * @return The comfort level of this individual.
     */
    double comfort();

    /**
     * Determines if the solution of this individual is equal to the solution of another individual of the same type.
     *
     * @param other The other individual to compare with.
     * @return true if the solutions are equal, false otherwise.
     */
    boolean isSolutionEqual(I other);

    /**
     * Responds to an epidemic death. This method is called when an individual 
     * is removed from the population due to an epidemic.
     */
    void onEpidemicDeath();
}
