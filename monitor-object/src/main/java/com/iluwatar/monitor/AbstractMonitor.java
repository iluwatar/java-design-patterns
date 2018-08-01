package com.iluwatar.monitor;

import java.util.ArrayList;

/**
 * A class for Monitors. Monitors provide coordination of concurrent threads.
 * Each Monitor protects some resource, usually data. At each point in time a
 * monitor object is occupied by at most one thread.
 */
public abstract class AbstractMonitor {
  final Semaphore entrance = new Semaphore(1);
  volatile Thread occupant = null;
  private final ArrayList<MonitorListener> listOfListeners = new ArrayList<>();
  private final String name;

  public String getName() {
    return name;
  }

  protected AbstractMonitor() {
    this(null);
  }

  protected AbstractMonitor(String name) {
    this.name = name;
  }

  /**
   * The invariant. The default implementation always returns true. This method
   * should be overridden if at all possible with the strongest economically
   * evaluable invariant.
   */
  protected boolean invariant() {
    return true;
  }

  /**
   * Enter the monitor. Any thread calling this method is delayed until the
   * monitor is unoccupied. Upon returning from this method, the monitor is
   * considered occupied. A thread must not attempt to enter a Monitor it is
   * already in.
   */
  protected void enter() {
    notifyCallEnter();
    entrance.acquire();
    // The following assertion should never trip!
    Assertion.check(occupant == null, "2 threads in one monitor");
    occupant = Thread.currentThread();
    notifyReturnFromEnter();
    Assertion.check(invariant(), "Invariant of monitor " + getName());
  }

  /**
   * Leave the monitor. After returning from this method, the thread no longer
   * occupies the monitor. Only a thread that is in the monitor may leave it.
   * 
   * @throws AssertionError
   *             if the thread that leaves is not the occupant.
   */
  protected void leave() {
    notifyLeaveMonitor();
    leaveWithoutATrace();
  }

  /**
   * Leave the monitor. After returning from this method, the thread no longer
   * occupies the monitor. Only a thread that is in the monitor may leave it.
   * 
   * @throws AssertionError
   *             if the thread that leaves is not the occupant.
   */
  protected <T> T leave(T result) {
    leave();
    return result;
  }

  void leaveWithoutATrace() {
    Assertion.check(invariant(), "Invariant of monitor " + getName());
    Assertion.check(occupant == Thread.currentThread(), "Thread is not occupant");
    occupant = null;
    entrance.release();
  }

  /**
   * Run the runnable inside the monitor. Any thread calling this method will be
   * delayed until the monitor is empty. The "run" method of its argument is then
   * executed within the protection of the monitor. When the run method returns,
   * if the thread still occupies the monitor, it leaves the monitor.
   * 
   * @param runnable
   *            A Runnable object.
   */
  protected void doWithin(Runnable runnable) {
    enter();
    try {
      runnable.run();
    } finally {
      if (occupant == Thread.currentThread()) {
        leave();
      }
    }
  }

  /**
   * Run the runnable inside the monitor. Any thread calling this method will be
   * delayed until the monitor is empty. The "run" method of its argument is then
   * executed within the protection of the monitor. When the run method returns,
   * if the thread still occupies the monitor, it leaves the monitor. Thus the
   * signalAndLeave method may be called within the run method.
   * 
   * @param runnable
   *            A RunnableWithResult object.
   * @return The value computed by the run method of the runnable.
   */
  protected <T> T doWithin(RunnableWithResult<T> runnable) {
    enter();
    try {
      return runnable.run();
    } finally {
      if (occupant == Thread.currentThread()) {
        leave();
      }
    }
  }

  /**
   * Create a condition queue associated with a checked Assertion. The Assertion
   * will be checked prior to an signal of the condition.
   */
  protected Condition makeCondition(Assertion prop) {
    return makeCondition(null, prop);
  }

  /**
   * Create a condition queue with no associated checked Assertion.
   */
  protected Condition makeCondition() {
    return makeCondition(null, TrueAssertion.SINGLETON);
  }

  /**
   * Create a condition queue associated with a checked Assertion. The Assertion
   * will be checked prior to an signal of the condition.
   */
  protected Condition makeCondition(String name, Assertion prop) {
    return new Condition(name, this, prop);
  }

  /**
   * Create a condition queue with no associated checked Assertion.
   */
  protected Condition makeCondition(String name) {
    return makeCondition(name, TrueAssertion.SINGLETON);
  }

  /** Register a listener. */
  public void addListener(MonitorListener newListener) {
    listOfListeners.add(newListener);
  }

  private void notifyCallEnter() {
    for (MonitorListener listener : listOfListeners) {
      listener.callEnterMonitor(this);
    }
  }

  private void notifyReturnFromEnter() {
    for (MonitorListener listener : listOfListeners) {
      listener.returnFromEnterMonitor(this);
    }
  }

  private void notifyLeaveMonitor() {
    for (MonitorListener listener : listOfListeners) {
      listener.leaveMonitor(this);
    }
  }

  void notifyCallAwait(Condition condition) {
    for (MonitorListener listener : listOfListeners) {
      listener.callAwait(condition, this);
    }
  }

  void notifyReturnFromAwait(Condition condition) {
    for (MonitorListener listener : listOfListeners) {
      listener.returnFromAwait(condition, this);
    }
  }

  void notifySignallerAwakesAwaitingThread(Condition condition) {
    for (MonitorListener listener : listOfListeners) {
      listener.signallerAwakesAwaitingThread(condition, this);
    }
  }

  void notifySignallerLeavesTemporarily(Condition condition) {
    for (MonitorListener listener : listOfListeners) {
      listener.signallerLeavesTemporarily(condition, this);
    }
  }

  void notifySignallerReenters(Condition condition) {
    for (MonitorListener listener : listOfListeners) {
      listener.signallerReenters(condition, this);
    }
  }

  void notifySignallerLeavesMonitor(Condition condition) {
    for (MonitorListener listener : listOfListeners) {
      listener.signallerLeavesMonitor(condition, this);
    }
  }
}
