package trafficlight;

/**
 * State interface for traffic light states.
 */
public interface TrafficLightState {

  /**
   * Handles the transition to the next state based on the current state.
   *
   * @param context the context object that manages the traffic light's state
   */
  void handleEvent(TrafficLightContext context);
}




