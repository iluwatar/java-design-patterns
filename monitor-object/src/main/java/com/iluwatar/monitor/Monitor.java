package com.iluwatar.monitor;

/**
 * A final class for Monitors.
 */

public final class Monitor extends AbstractMonitor {

  private Assertion invariant;

  public Monitor() {
    this(TrueAssertion.SINGLETON);
  }

  public Monitor(Assertion invariant) {
    this.invariant = invariant;
  }

  public Monitor(String name) {
    this(name, TrueAssertion.SINGLETON);
  }

  public Monitor(String name, Assertion invariant) {
    super(name);
    this.invariant = invariant;
  }

  @Override
  public boolean invariant() {
    return invariant.isTrue();
  }
}