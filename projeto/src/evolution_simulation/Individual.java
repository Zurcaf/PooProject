package evolution_simulation;

public interface Individual {

	float comfort(int matrixC[][]);

	public void mutateInPlace();

	Individual reproduce();

}