package com.iluwatar.fsm;

/**
 * Broken state.
 */
public class Broken extends Astate {
  public Broken(CoinMachine entity) {
    super(entity, "Broken");
  }

  @Override
  public void pass() {

  }

  @Override
  public void coin() {

  }

  @Override
  public void failed() {

  }

  @Override
  public void fixed() {
    setEntityState(new Locked(entity));
  }
}