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
package com.iluwatar.value.object;

import lombok.extern.slf4j.Slf4j;

/**
 * A Value Object are objects which follow value semantics rather than reference semantics. This
 * means value objects' equality are not based on identity. Two value objects are equal when they
 * have the same value, not necessarily being the same object.
 *
 * <p>Value Objects must override equals(), hashCode() to check the equality with values. Value
 * Objects should be immutable so declare members final. Obtain instances by static factory methods.
 * The elements of the state must be other values, including primitive types. Provide methods,
 * typically simple getters, to get the elements of the state. A Value Object must check equality
 * with equals() not ==
 *
 * <p>For more specific and strict rules to implement value objects check the rules from Stephen
 * Colebourne's term VALJO : http://blog.joda.org/2014/03/valjos-value-java-objects.html
 */
@Slf4j
public class App {

  /**
   * This example creates three HeroStats (value objects) and checks equality between those.
   */
  public static void main(String[] args) {
    var statA = HeroStat.valueOf(10, 5, 0);
    var statB = HeroStat.valueOf(10, 5, 0);
    var statC = HeroStat.valueOf(5, 1, 8);

    LOGGER.info("statA: {}", statA);
    LOGGER.info("statB: {}", statB);
    LOGGER.info("statC: {}", statC);

    LOGGER.info("Are statA and statB equal? {}", statA.equals(statB));
    LOGGER.info("Are statA and statC equal? {}", statA.equals(statC));
  }
}
