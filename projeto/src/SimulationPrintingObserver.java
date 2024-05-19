import patrol_allocation_simulator.Individual;
import patrol_allocation_simulator.SimulationObservation;
import patrol_allocation_simulator.SimulationObserver;

public class SimulationPrintingObserver implements SimulationObserver {
	static int observationCount = 0;

	public void update(SimulationObservation observation) {
		Individual[] bestIndividuals = observation.bestIndividuals();
		
		StringBuilder sb = new StringBuilder(1000);
		sb.append("Observation ");
		sb.append(observationCount + 1);
		sb.append(":\n");
		sb.append("    Present instant:                  ");
		sb.append(observation.currentInstant());
		sb.append("\n");
		sb.append("    Number of realized events:        ");
		sb.append(observation.eventCount());
		sb.append("\n");
		sb.append("    Population size:                  ");
		sb.append(observation.population().size());
		sb.append("\n");
		sb.append("    Number of epidemics:              ");
		sb.append(observation.epidemicCount());
		sb.append("\n");
		sb.append("    Best distribution of the patrols: ");
		sb.append(bestIndividuals[0].distribution());
		sb.append("\n");
		sb.append("    Empire policing time:             ");
		sb.append(bestIndividuals[0].policingTime());
		sb.append("\n");
		sb.append("    Comfort:                          ");
		sb.append(bestIndividuals[0].comfort());
		sb.append("\n");
		sb.append("    Other candidate distributions:    ");
		for (int i = 1; i < 6; i++) {
			sb.append(bestIndividuals[i].distribution());
			sb.append(" : ");
			sb.append(bestIndividuals[i].policingTime());
			sb.append(" : ");
			sb.append(bestIndividuals[i].comfort());
			sb.append("\n");
			if (i < 5) {
				sb.append("                                      ");
			}
		}
		System.out.println(sb);
	}
}
