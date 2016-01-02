package com.iluwatar.fluentinterface.fluentiterable.lazy;

import com.iluwatar.fluentinterface.fluentiterable.FluentIterable;
import com.iluwatar.fluentinterface.fluentiterable.FluentIterableTest;

/**
 * Date: 12/12/15 - 7:56 PM
 *
 * @author Jeroen Meulemeester
 */
public class LazyFluentIterableTest extends FluentIterableTest {

  @Override
  protected FluentIterable<Integer> createFluentIterable(Iterable<Integer> integers) {
    return LazyFluentIterable.from(integers);
  }

}
