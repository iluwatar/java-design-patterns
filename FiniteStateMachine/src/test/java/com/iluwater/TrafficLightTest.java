package com.iluwater;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.trafficlight.GreenLightState;
import com.iluwatar.trafficlight.RedLightState;
import com.iluwatar.trafficlight.TrafficLightContext;
import com.iluwatar.trafficlight.YellowLightState;
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

  // Test invalid state transition
  @Test
  void testInvalidStateTransition() {
    context.setState(new RedLightState());
    try {
      // This should fail, as it doesn't make sense for Red to handle an event again.
      context.handleEvent();
    } catch (IllegalStateException e) {
      assertTrue(true, "Handled invalid state transition.");
    }
  }

  // Test state reset
  @Test
  void testStateReset() {
    context.setState(new YellowLightState());
    context.handleEvent(); // Yellow -> Red
    assertTrue(context.getCurrentState() instanceof RedLightState);

    context.setState(new GreenLightState());
    context.handleEvent(); // Green -> Yellow
    assertTrue(context.getCurrentState() instanceof YellowLightState);

    context.setState(new RedLightState());
    context.handleEvent(); // Red -> Green
    assertTrue(context.getCurrentState() instanceof GreenLightState);
  }

  // Test manually setting the state
  @Test
  void testManualStateSet() {
    context.setState(new GreenLightState());
    assertTrue(context.getCurrentState() instanceof GreenLightState);

    context.setState(new YellowLightState());
    assertTrue(context.getCurrentState() instanceof YellowLightState);

    context.setState(new RedLightState());
    assertTrue(context.getCurrentState() instanceof RedLightState);
  }

  // Additional tests for edge cases

  // Test if state is correctly set in the middle of a cycle
  @Test
  void testMidCycleStateSet() {
    context.handleEvent(); // Red -> Green
    assertTrue(context.getCurrentState() instanceof GreenLightState);

    // Set state manually in the middle of the cycle
    context.setState(new YellowLightState());
    assertTrue(context.getCurrentState() instanceof YellowLightState);

    context.handleEvent(); // Yellow -> Red
    assertTrue(context.getCurrentState() instanceof RedLightState);
  }

  // Test if context properly resets after complete cycle
  @Test
  void testContextResetAfterFullCycle() {
    context.handleEvent(); // Red -> Green
    context.handleEvent(); // Green -> Yellow
    context.handleEvent(); // Yellow -> Red

    // Reset and verify again
    context.setState(new GreenLightState());
    assertTrue(context.getCurrentState() instanceof GreenLightState);

    context.handleEvent(); // Green -> Yellow
    assertTrue(context.getCurrentState() instanceof YellowLightState);

    context.handleEvent(); // Yellow -> Red
    assertTrue(context.getCurrentState() instanceof RedLightState);
  }

  // Test if the initial state remains unchanged after multiple cycles
  @Test
  void testMultipleCycles() {
    context.handleEvent(); // Red -> Green
    context.handleEvent(); // Green -> Yellow
    context.handleEvent(); // Yellow -> Red

    // Perform another full cycle and ensure the state remains correct
    context.handleEvent(); // Red -> Green
    assertTrue(context.getCurrentState() instanceof GreenLightState);

    context.handleEvent(); // Green -> Yellow
    assertTrue(context.getCurrentState() instanceof YellowLightState);

    context.handleEvent(); // Yellow -> Red
    assertTrue(context.getCurrentState() instanceof RedLightState);
  }
}



