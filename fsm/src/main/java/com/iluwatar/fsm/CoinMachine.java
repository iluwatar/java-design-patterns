package com.iluwatar.fsm;

/**
 * The coinmachine which is visible to users.
 */
public class CoinMachine implements Translation {
  public Astate machineState = new Locked(this);

  public void updateState(Astate newState) {
    machineState = newState;
  }

  @Override
  public void pass() {
    machineState.pass();
  }

  @Override
  public void coin() {
    machineState.coin();
  }

  @Override
  public void failed() {
    machineState.failed();
  }

  @Override
  public void fixed() {
    machineState.fixed();
  }

  public String currentState() {
    return machineState.currentState();
  }
}
