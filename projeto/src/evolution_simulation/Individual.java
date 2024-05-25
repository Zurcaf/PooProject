package evolution_simulation;

public interface Individual {

	float comfort();

	void mutateInPlace();

	Individual reproduce();

}