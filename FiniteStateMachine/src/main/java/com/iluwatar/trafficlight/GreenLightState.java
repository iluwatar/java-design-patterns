package com.iluwatar.trafficlight;

/**
 * Concrete state representing the Green Light.
 */
public class GreenLightState implements TrafficLightState {

  /**
   * Handles the event for the Green Light.
   * This method transitions the traffic light to the Yellow state after the Green state.
   *
   * @param context The traffic light context to manage the state transitions.
   */
  @Override
  public void handleEvent(TrafficLightContext context) {
    System.out.println("Green Light: Go!");
    // Transition to the Yellow light state
    context.setState(new YellowLightState());
  }
}



