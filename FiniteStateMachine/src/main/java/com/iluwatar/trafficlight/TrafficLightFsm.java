package com.iluwatar.trafficlight;

/**
 * Simulates a traffic light system using a Finite State Machine (FSM).
 */
public class TrafficLightFsm {

  /**
   * Runs the traffic light simulation.
   *
   * @param args command-line arguments (not used here)
   */
  public static void main(String[] args) {
    // Start with the Red light
    TrafficLightContext trafficLight = new TrafficLightContext(new RedLightState());

    // Cycle through the traffic light states
    for (int i = 0; i < 6; i++) {
      trafficLight.handleEvent();
    }
  }
}





