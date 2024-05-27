package patrol_allocation;

public class ObservationEvent extends SimulationEvent {

	ObservationEvent(PatrolSimulation simulation) {
		super(simulation);
	}

	public void execute() {
		simulation.performObservation(false);
	}

}