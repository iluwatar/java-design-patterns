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

package com.iluwatar.effectivity;

import lombok.extern.slf4j.Slf4j;

/**
 * The Effectivity pattern adds a time period to an object to show when it is effective.
 *
 * <p>Many facts are only true for a certain period of time, and so a way to
 * describe these facts is to mark them with a period of time. </p>
 *
 * <p>In this example, each {@link Person} has {@link Employment} at a {@link Company}
 * over certain periods of time. Each {@link Employment} contains a {@link DateRange}
 * that describes the range over which the employment is valid.</p>
 */
@Slf4j
public class App {
  /**
   * Main function.
   *
   * @param args Unused arguments.
   */
  public static void main(String[] args) {
    Person bob = new Person("Bob");
    Company aaInc = new Company("AA inc");
    Company bbCo = new Company("BB Company");

    // Bob was employed at A Inc from the 2nd of June 2003, until 4th of February 2008
    DateRange aaIncPeriod = new DateRange(new SimpleDate(2003, 6, 2),
            new SimpleDate(2008, 2, 4));
    Employment aaIncEmployment = new Employment(aaInc, aaIncPeriod);
    bob.addEmployment(aaIncEmployment);

    // Then, Bob started working at B Company from the 19th of March 2008,
    // and has continued working there since.
    bob.addEmployment(bbCo, new SimpleDate(2008, 3, 19));

    LOGGER.info("Bob's employments:");
    for (Employment emp : bob.getEmployments()) {
      LOGGER.info(emp.toString());
    }
  }

}
