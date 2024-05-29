package user_interface;

import java.util.List;

import patrol_allocation.*;

public class PrintingObserver implements SimulationObserver {

	static int observationCount = 0;

	public void onObservation(SimulationObservation observation) {
		List<DistributionIndividual> bestIndividuals = observation.bestIndividuals();
		
		StringBuilder sb = new StringBuilder(1000);
		sb.append("Observation ");
		sb.append(observationCount + 1);
		sb.append(":\n");
		sb.append("    Present instant:                  ");
		sb.append(observation.currentInstant());
		sb.append("\n");
		sb.append("    Number of realized events:        ");
		sb.append(observation.totalEventCount());
		sb.append("\n");
		sb.append("    Population size:                  ");
		sb.append(observation.populationCount());
		sb.append("\n");
		sb.append("    Number of epidemics:              ");
		sb.append(observation.epidemicCount());
		sb.append("\n");
		sb.append("    Best distribution of the patrols: ");
		sb.append(bestIndividuals.get(0).distribution());
		sb.append("\n");
		sb.append("    Empire policing time:             ");
		sb.append(bestIndividuals.get(0).policingTime());
		sb.append("\n");
		sb.append("    Comfort:                          ");
		sb.append(bestIndividuals.get(0).comfort());
		sb.append("\n");
		sb.append("    Other candidate distributions:    ");
		for (int i = 0; i < bestIndividuals.size(); i++) {
			sb.append(bestIndividuals.get(i).distribution());
			sb.append(" : ");
			sb.append(bestIndividuals.get(i).policingTime());
			sb.append(" : ");
			sb.append(bestIndividuals.get(i).comfort());
			sb.append("\n");
			if (i < bestIndividuals.size()) {
				sb.append("                                      ");
			}
		}
		System.out.println(sb);

		observationCount++;
	}
}