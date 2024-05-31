package evolution_simulation;

public interface Individual<I extends Individual<I>> {

	double comfort();

	boolean isSolutionEqual(I other);

	void onEpidemicDeath();

}