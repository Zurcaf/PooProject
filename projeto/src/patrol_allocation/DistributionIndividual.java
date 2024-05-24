package patrol_allocation;

import evolution_simulation.*;

public class DistributionIndividual implements Individual {

	private Distribution distribution;

	DistributionIndividual(Distribution distribution) {
		this.distribution = distribution;
	}

	public Distribution distribution() {
		return distribution;
	}

	public float comfort() {
		// TODO - implement DistributionIndividual.comfort
		return (float) 0.1234;
	}

	public int policingTime() {
		// TODO - implement DistributionIndividual.policingTime
		return 123;
	}

	public void mutateInPlace() {
		// TODO - implement DistributionIndividual.mutateInPlace
		throw new UnsupportedOperationException();
	}

	public Individual reproduce() {
		// TODO - implement DistributionIndividual.reproduce
		throw new UnsupportedOperationException();
	}

}