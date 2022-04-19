package com.iluwatar.fsm;

/**
 * Unlocked state.
 */
public class Unlocked extends Astate {
  public Unlocked(CoinMachine entity) {
    super(entity, "Unlocked");
  }

  @Override
  public void pass() {
    setEntityState(new Locked(entity));
  }

  @Override
  public void coin() {

  }

  @Override
  public void failed() {
    setEntityState(new Broken(entity));
  }

  @Override
  public void fixed() {

  }
}
