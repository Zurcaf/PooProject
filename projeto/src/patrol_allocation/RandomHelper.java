package patrol_allocation;
import java.util.Random;

class RandomHelper {
	final Random random;

	public RandomHelper(Random random) {
		this.random = random;
	}

	double getExp(double m) {
		if (!Double.isFinite(m)) throw new IllegalArgumentException("Non-finite mean value for the exponential distribution");
		double next = random.nextDouble();
		double result = -m*Math.log(1.0-next);
		return result;
	}
}
