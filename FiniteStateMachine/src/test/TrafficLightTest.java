package trafficlight;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Traffic Light FSM.
 */
public class TrafficLightTest {

  private TrafficLightContext context;

  @BeforeEach
  void setUp() {
    // Start with the Red Light state
    context = new TrafficLightContext(new RedLightState());
  }

  @Test
  void testInitialState() {
    assertTrue(context.getCurrentState() instanceof RedLightState, "Initial state should be RedLightState.");
  }

  @Test
  void testRedToGreenTransition() {
    context.handleEvent();
    assertTrue(context.getCurrentState() instanceof GreenLightState, "Red Light should transition to Green Light.");
  }

  @Test
  void testGreenToYellowTransition() {
    context.setState(new GreenLightState());
    context.handleEvent();
    assertTrue(context.getCurrentState() instanceof YellowLightState, "Green Light should transition to Yellow Light.");
  }

  @Test
  void testYellowToRedTransition() {
    context.setState(new YellowLightState());
    context.handleEvent();
    assertTrue(context.getCurrentState() instanceof RedLightState, "Yellow Light should transition to Red Light.");
  }

  @Test
  void testFullCycle() {
    context.handleEvent(); // Red -> Green
    assertTrue(context.getCurrentState() instanceof GreenLightState);

    context.handleEvent(); // Green -> Yellow
    assertTrue(context.getCurrentState() instanceof YellowLightState);

    context.handleEvent(); // Yellow -> Red
    assertTrue(context.getCurrentState() instanceof RedLightState);
  }
}
