package patrol_allocation;

/**
 * An interface for observers that are notified of simulation observations.
 */
public interface SimulationObserver {

    /**
     * Called when an observation is made in the simulation.
     *
     * @param observation The observation that was made.
     */
    void onObservation(SimulationObservation observation);
}
