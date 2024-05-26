package evolution_simulation;

public interface Individual {

	float comfort(int matrixC[][]);

	void mutateInPlace();

	Individual reproduce();

}