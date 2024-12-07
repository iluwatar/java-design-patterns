package trafficlight;

/**
 * Context class for managing the current state and transitions.
 */
public class TrafficLightContext {
  private TrafficLightState currentState;

  /**
   * Initializes the context with the given initial state.
   *
   * @param initialState the initial state of the traffic light
   */
  public TrafficLightContext(TrafficLightState initialState) {
    this.currentState = initialState;
  }

  /**
   * Updates the current state of the traffic light.
   *
   * @param newState the new state to transition to
   */
  public void setState(TrafficLightState newState) {
    this.currentState = newState;
  }

  /**
   * Handles the current state's event and transitions to the next state.
   */
  public void handleEvent() {
    currentState.handleEvent(this);
  }

  /**
   * Gets the current state of the traffic light.
   * This can be useful for testing purposes.
   */
  public TrafficLightState getCurrentState() {
    return currentState;
  }
}

