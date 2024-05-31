package patrol_allocation;

import java.util.Random;

/**
 * A helper class for generating random numbers following specific distributions.
 */
class RandomHelper {
    final Random random;

    /**
     * Constructs a new RandomHelper with the specified random number generator.
     *
     * @param random The random number generator to use for generating random values.
     */
    public RandomHelper(Random random) {
        this.random = random;
    }

    /**
     * Generates a random number following an exponential distribution with the specified mean.
     *
     * @param mean The mean value for the exponential distribution.
     * @return A random number following an exponential distribution with the specified mean.
     * @throws IllegalArgumentException if the mean value is not finite.
     */
    double getExp(double mean) {
        if (!Double.isFinite(mean)) throw new IllegalArgumentException("Non-finite mean value for the exponential distribution");
        double next = random.nextDouble();
        double result = -mean * Math.log(1.0 - next);
        return result;
    }
}

