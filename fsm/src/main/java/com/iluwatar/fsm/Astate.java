package com.iluwatar.fsm;

/**
 * Different states.
 */
public abstract class Astate implements Translation {
  CoinMachine entity;
  private String name;

  public Astate(CoinMachine entity, String name) {
    this.entity = entity;
    this.name = name;
  }

  void setEntityState(Astate newState) {
    entity.machineState = newState;
    this.name = "";
    this.entity = null;
  }

  public String currentState() {
    return name;
  }
}
