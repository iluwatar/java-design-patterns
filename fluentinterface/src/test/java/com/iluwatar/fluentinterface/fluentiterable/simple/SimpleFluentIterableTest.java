package com.iluwatar.fluentinterface.fluentiterable.simple;

import com.iluwatar.fluentinterface.fluentiterable.FluentIterable;
import com.iluwatar.fluentinterface.fluentiterable.FluentIterableTest;

/**
 * Date: 12/12/15 - 7:56 PM
 *
 * @author Jeroen Meulemeester
 */
public class SimpleFluentIterableTest extends FluentIterableTest {

  @Override
  protected FluentIterable<Integer> createFluentIterable(Iterable<Integer> integers) {
    return SimpleFluentIterable.fromCopyOf(integers);
  }

}
