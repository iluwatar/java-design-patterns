package com.iluwatar;

import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;

/**
 * Example of runnable with use of {@link ThreadLocal}.
 */
@AllArgsConstructor
public class WithThreadLocal extends AbstractThreadLocalExample {

  private final ThreadLocal<Integer> value;

  /**
   * Removes the current thread's value for this thread-local variable.
   */
  public void remove() {
    this.value.remove();
  }

  @Override
  protected Consumer<Integer> setter() {
    return value::set;
  }

  @Override
  protected Supplier<Integer> getter() {
    return value::get;
  }
}
