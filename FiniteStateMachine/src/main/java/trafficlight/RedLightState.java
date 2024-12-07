package trafficlight;
/**
 * Concrete state representing the Red Light.
 */
public class RedLightState implements TrafficLightState {
  @Override
  public void handleEvent(TrafficLightContext context) {
    System.out.println("Red Light: Stop!");
    context.setState(new GreenLightState());
  }
}

