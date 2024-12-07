package com.iluwatar.trafficlight;

/**
 * Concrete state representing the Yellow Light.
 */
public class YellowLightState implements TrafficLightState {
  @Override
  public void handleEvent(TrafficLightContext context) {
    System.out.println("Yellow Light: Caution!");
    context.setState(new RedLightState());
  }
}

