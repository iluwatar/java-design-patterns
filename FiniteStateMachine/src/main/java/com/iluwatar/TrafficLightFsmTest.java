package com.iluwatar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class TrafficLightFsmTest {

  private TrafficLightContext context;
  private TrafficLightState redState;
  private TrafficLightState greenState;
  private TrafficLightState yellowState;

  @BeforeEach
  void setUp() {
    // Set up the initial states for testing
    redState = mock(RedLightState.class);
    greenState = mock(GreenLightState.class);
    yellowState = mock(YellowLightState.class);

    // Initialize the context with the Red state
    context = new TrafficLightContext(redState);
  }

  @Test
  void testRedToGreenStateTransition() {
    // Mock the behavior of handleEvent for red state
    context.handleEvent();  // Expected transition to green state
    verify(redState).handleEvent(context); // Verify that handleEvent was called
    assert context.getCurrentState() instanceof GreenLightState; // Assert the next state is Green
  }

  @Test
  void testGreenToYellowStateTransition() {
    // Change to green state manually
    context.setState(greenState);
    context.handleEvent();  // Expected transition to yellow state
    verify(greenState).handleEvent(context); // Verify the handleEvent for green state
    assert context.getCurrentState() instanceof YellowLightState; // Assert the next state is Yellow
  }

  @Test
  void testYellowToRedStateTransition() {
    // Change to yellow state manually
    context.setState(yellowState);
    context.handleEvent();  // Expected transition to red state
    verify(yellowState).handleEvent(context); // Verify the handleEvent for yellow state
    assert context.getCurrentState() instanceof RedLightState; // Assert the next state is Red
  }

  @Test
  void testStateTransitionOrder() {
    // Test the full cycle: Red -> Green -> Yellow -> Red
    context = new TrafficLightContext(new RedLightState());

    // Red to Green
    context.handleEvent();
    assert context.getCurrentState() instanceof GreenLightState;

    // Green to Yellow
    context.handleEvent();
    assert context.getCurrentState() instanceof YellowLightState;

    // Yellow to Red
    context.handleEvent();
    assert context.getCurrentState() instanceof RedLightState;
  }
}

