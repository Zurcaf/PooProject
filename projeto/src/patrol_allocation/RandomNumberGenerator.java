package patrol_allocation;
import java.util.Random;

class RandomNumberGenerator {
	private static final RandomNumberGenerator instance = new RandomNumberGenerator();

	final Random random = new Random();

	private RandomNumberGenerator() {}
	public static RandomNumberGenerator getInstance() {
		return instance;
	}

	double getExp(double m) {
		double next = random.nextDouble();
		return -m*Math.log(1.0-next);
	}
}
