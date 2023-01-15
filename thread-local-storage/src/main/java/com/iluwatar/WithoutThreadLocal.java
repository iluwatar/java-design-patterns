package com.iluwatar;

import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;

/**
 * Example of runnable without usage of {@link ThreadLocal}.
 */
@AllArgsConstructor
public class WithoutThreadLocal extends AbstractThreadLocalExample {

  private Integer value;

  @Override
  protected Consumer<Integer> setter() {
    return integer -> value = integer;
  }

  @Override
  protected Supplier<Integer> getter() {
    return () -> value;
  }
}
