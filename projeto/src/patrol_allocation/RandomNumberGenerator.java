package patrol_allocation;
import java.util.Random;

class RandomNumberGenerator {
	private static final RandomNumberGenerator instance = new RandomNumberGenerator();

	final Random random = new Random(7);

	private RandomNumberGenerator() {}
	public static RandomNumberGenerator getInstance() {
		return instance;
	}

	double getExp(double m) {
		if (!Double.isFinite(m)) throw new IllegalArgumentException("Non-finite mean value for the exponential distribution");
		double next = random.nextDouble();
		double result = -m*Math.log(1.0-next);
		return result;
	}
}
