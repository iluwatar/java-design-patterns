package com.iluwatar.state;

/**
 * 
 * Mammoth has internal state that defines its behavior.
 * 
 */
public class Mammoth {

  private State state;

  public Mammoth() {
    state = new PeacefulState(this);
  }

  /**
   * Makes time pass for the mammoth
   */
  public void timePasses() {
    if (state.getClass().equals(PeacefulState.class)) {
      changeStateTo(new AngryState(this));
    } else {
      changeStateTo(new PeacefulState(this));
    }
  }

  private void changeStateTo(State newState) {
    this.state = newState;
    this.state.onEnterState();
  }

  @Override
  public String toString() {
    return "The mammoth";
  }

  public void observe() {
    this.state.observe();
  }
}
