package com.iluwatar.coarse.grained;

/**
 * Implements a coarse-grained lock for synchronizing tasks.
 * This class provides a mechanism to ensure thread safety by wrapping tasks
 * inside a synchronized block.
 */
public class Lock {

  /** The internal lock object used for synchronization. */
  private final Object newLock = new Object();

  /**
   * Executes a given task within a synchronized block, ensuring thread safety.
   *
   * @param task the {@code Runnable} task to be executed in a synchronized context
   */
  public void synchronizedMethod(Runnable task) {
    synchronized (newLock) {
      task.run();
    }
  }
}
