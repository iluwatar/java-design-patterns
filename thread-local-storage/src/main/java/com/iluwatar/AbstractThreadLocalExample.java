package com.iluwatar;

import java.security.SecureRandom;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Class with main logic.
 */
public abstract class AbstractThreadLocalExample implements Runnable {

  private static final SecureRandom RND = new SecureRandom();

  private static final Integer RANDOM_THREAD_PARK_START = 1_000_000_000;
  private static final Integer RANDOM_THREAD_PARK_END = 2_000_000_000;

  @Override
  public void run() {
    long nanosToPark = RND.nextInt(RANDOM_THREAD_PARK_START, RANDOM_THREAD_PARK_END);
    LockSupport.parkNanos(nanosToPark);

    System.out.println(getThreadName() + ", before value changing: " + getter().get());
    setter().accept(RND.nextInt());
  }

  /**
   * Setter for our value.
   *
   * @return consumer
   */
  protected abstract Consumer<Integer> setter();

  /**
   * Getter for our value.
   *
   * @return supplier
   */
  protected abstract Supplier<Integer> getter();

  private String getThreadName() {
    return Thread.currentThread().getName();
  }
}
