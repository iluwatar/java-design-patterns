public class TrafficLightFSM {
  // State Interface
  interface TrafficLightState {
    void handleEvent(TrafficLightContext context);
  }

  // Concrete States
  static class RedLightState implements TrafficLightState {
    @Override
    public void handleEvent(TrafficLightContext context) {
      System.out.println("Red Light: Stop!");
      context.setState(new GreenLightState());
    }
  }

  static class GreenLightState implements TrafficLightState {
    @Override
    public void handleEvent(TrafficLightContext context) {
      System.out.println("Green Light: Go!");
      context.setState(new YellowLightState());
    }
  }

  static class YellowLightState implements TrafficLightState {
    @Override
    public void handleEvent(TrafficLightContext context) {
      System.out.println("Yellow Light: Caution!");
      context.setState(new RedLightState());
    }
  }

  // Context Class
  static class TrafficLightContext {
    private TrafficLightState currentState;

    public TrafficLightContext(TrafficLightState initialState) {
      this.currentState = initialState;
    }

    public void setState(TrafficLightState newState) {
      this.currentState = newState;
    }

    public void handleEvent() {
      currentState.handleEvent(this);
    }
  }

  // Main Method
  public static void main(String[] args) {
    // Initialize the traffic light with the Red state
    TrafficLightContext trafficLight = new TrafficLightContext(new RedLightState());

    // Simulate events
    for (int i = 0; i < 6; i++) {
      trafficLight.handleEvent();
    }
  }
}
