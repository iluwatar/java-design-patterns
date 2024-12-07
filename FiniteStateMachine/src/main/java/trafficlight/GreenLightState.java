package trafficlight;


/**
 * Concrete state representing the Green Light.
 */
public class GreenLightState implements TrafficLightState {
  @Override
  public void handleEvent(TrafficLightContext context) {
    System.out.println("Green Light: Go!");
    context.setState(new YellowLightState());
  }
}

