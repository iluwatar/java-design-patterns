package com.iluwatar.monitor;

/**
 * A final class for Monitors.
 */

public final class Monitor extends AbstractMonitor {

  private Assertion invariant;

  public Monitor() {
    this(TrueAssertion.singleton);
  }

  public Monitor(Assertion invariant) {
    this.invariant = invariant;
  }

  public Monitor(String name) {
    this(name, TrueAssertion.singleton);
  }

  public Monitor(String name, Assertion invariant) {
    super(name);
    this.invariant = invariant;
  }

  @Override
  public boolean invariant() {
    return invariant.isTrue();
  }

  @Override
  public void enter() {
    super.enter();
  }

  @Override
  public void leave() {
    super.leave();
  }

  @Override
  public <T> T leave(T result) {
    return super.leave(result);
  }

  @Override
  public void doWithin(Runnable runnable) {
    super.doWithin(runnable);
  }

  @Override
  public <T> T doWithin(RunnableWithResult<T> runnable) {
    return super.doWithin(runnable);
  }

  @Override
  public Condition makeCondition() {
    return super.makeCondition();
  }

  @Override
  public Condition makeCondition(Assertion assertion) {
    return super.makeCondition(assertion);
  }

  @Override
  public Condition makeCondition(String name) {
    return super.makeCondition(name);
  }

  @Override
  public Condition makeCondition(String name, Assertion assertion) {
    return super.makeCondition(name, assertion);
  }
}