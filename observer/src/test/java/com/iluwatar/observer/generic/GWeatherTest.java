/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.iluwatar.observer.WeatherObserver;
import com.iluwatar.observer.WeatherType;
import com.iluwatar.observer.utils.InMemoryAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** GWeatherTest */
class GWeatherTest {

  private InMemoryAppender appender;

  @BeforeEach
  void setUp() {
    appender = new InMemoryAppender(GenWeather.class);
  }

  @AfterEach
  void tearDown() {
    appender.stop();
  }

  /**
   * Add a {@link WeatherObserver}, verify if it gets notified of a weather change, remove the
   * observer again and verify that there are no more notifications.
   */
  @Test
  void testAddRemoveObserver() {
    final var observer = mock(Race.class);

    final var weather = new GenWeather();
    weather.addObserver(observer);
    verifyNoMoreInteractions(observer);

    weather.timePasses();
    assertEquals("The weather changed to rainy.", appender.getLastMessage());
    verify(observer).update(weather, WeatherType.RAINY);

    weather.removeObserver(observer);
    weather.timePasses();
    assertEquals("The weather changed to windy.", appender.getLastMessage());

    verifyNoMoreInteractions(observer);
    assertEquals(2, appender.getLogSize());
  }

  /** Verify if the weather passes in the order of the {@link WeatherType}s */
  @Test
  void testTimePasses() {
    final var observer = mock(Race.class);
    final var weather = new GenWeather();
    weather.addObserver(observer);

    final var inOrder = inOrder(observer);
    final var weatherTypes = WeatherType.values();
    for (var i = 1; i < 20; i++) {
      weather.timePasses();
      inOrder.verify(observer).update(weather, weatherTypes[i % weatherTypes.length]);
    }

    verifyNoMoreInteractions(observer);
  }
}
