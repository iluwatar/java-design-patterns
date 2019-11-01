/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.observer;

import java.util.Collection;
import java.util.List;

/**
 * Date: 12/27/15 - 12:07 PM
 *
 * @author Jeroen Meulemeester
 */
public class OrcsTest extends WeatherObserverTest<Orcs> {

  @Override
  public Collection<Object[]> dataProvider() {
    return List.of(
            new Object[]{WeatherType.SUNNY, "The sun hurts the orcs' eyes."},
            new Object[]{WeatherType.RAINY, "The orcs are dripping wet."},
            new Object[]{WeatherType.WINDY, "The orc smell almost vanishes in the wind."},
            new Object[]{WeatherType.COLD, "The orcs are freezing cold."});
  }

  /**
   * Create a new test with the given weather and expected response
   */
  public OrcsTest() {
    super(Orcs::new);
  }

}
