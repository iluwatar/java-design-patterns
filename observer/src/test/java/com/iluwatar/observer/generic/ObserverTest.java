/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.observer.generic;

import com.iluwatar.observer.WeatherType;
import com.iluwatar.observer.utils.InMemoryAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Date: 12/27/15 - 11:44 AM
 * Test for Observers
 * @param <O> Type of Observer
 * @author Jeroen Meulemeester
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ObserverTest<O extends Observer<?, ?, WeatherType>> {

  private InMemoryAppender appender;

  @BeforeEach
  public void setUp() {
    appender = new InMemoryAppender();
  }

  @AfterEach
  public void tearDown() {
    appender.stop();
  }

  /**
   * The observer instance factory
   */
  private final Supplier<O> factory;

  /**
   * Create a new test instance using the given parameters
   *
   * @param factory  The factory, used to create an instance of the tested observer
   */
  ObserverTest(final Supplier<O> factory) {
    this.factory = factory;
  }

  public abstract Collection<Object[]> dataProvider();

  /**
   * Verify if the weather has the expected influence on the observer
   */
  @ParameterizedTest
  @MethodSource("dataProvider")
  public void testObserver(WeatherType weather, String response) {
    final var observer = this.factory.get();
    assertEquals(0, appender.getLogSize());

    observer.update(null, weather);
    assertEquals(response, appender.getLastMessage());
    assertEquals(1, appender.getLogSize());
  }

}
