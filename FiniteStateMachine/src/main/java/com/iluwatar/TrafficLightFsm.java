package com.iluwatar;

/**
 * The TrafficLightFsm class demonstrates a Finite State Machine (FSM)
 * implementation using a traffic light system.
 */
public class TrafficLightFsm {

  /**
   * State interface for traffic light states.
   */
  interface TrafficLightState {
    void handleEvent(TrafficLightContext context);
  }

  /**
   * Concrete state representing the Red Light.
   */
  static class RedLightState implements TrafficLightState {
    @Override
    public void handleEvent(TrafficLightContext context) {
      System.out.println("Red Light: Stop!");
      context.setState(new GreenLightState());
    }
  }

  /**
   * Concrete state representing the Green Light.
   */
  static class GreenLightState implements TrafficLightState {
    @Override
    public void handleEvent(TrafficLightContext context) {
      System.out.println("Green Light: Go!");
      context.setState(new YellowLightState());
    }
  }

  /**
   * Concrete state representing the Yellow Light.
   */
  static class YellowLightState implements TrafficLightState {
    @Override
    public void handleEvent(TrafficLightContext context) {
      System.out.println("Yellow Light: Caution!");
      context.setState(new RedLightState());
    }
  }

  /**
   * Context class for managing the current state and transitions.
   */
  static class TrafficLightContext {
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

  /**
   * Main method to simulate the traffic light FSM.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    // Initialize the traffic light with the Red state
    TrafficLightContext trafficLight = new TrafficLightContext(new RedLightState());

    // Simulate events
    for (int i = 0; i < 6; i++) {
      trafficLight.handleEvent();
    }
  }
}



