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

package com.iluwatar.temporalproperty;

import lombok.extern.slf4j.Slf4j;

/**
 * The Temporal Property pattern is a property that changes over time.
 *
 * <p>Instead of recording a property of a class as it is now, it may be useful
 * to consider about some points in time where this property has changed.</p>
 *
 * <p>In this example, the {@link Customer} class has an address with temporal property.
 * In particular, the history of all addresses are stored within a {@link AddressHistory}
 * instance which is an implementation of Temporal Property described as a Temporal Collection</p>
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    Customer john = new Customer(1234, "John");

    // set today's calendar date to 1st January 2000
    SimpleDate.setToday(new SimpleDate(2000, 1, 1));

    // set john's address at different points in time

    // Set the address where john lived on the 4th of february 1996 to 124 ave
    john.putAddress("124 ave", new SimpleDate(1996, 2, 4));
    // Set the address john lives in today to 123 place
    john.putAddress("123 place");

    // set the calandar day to tomorrow, the 2nd of January 2000
    SimpleDate.setToday(new SimpleDate(2000, 1, 2));

    // get john's address today
    LOGGER.info("John lives at " + john.getAddress() + " today");

    // get the address that john lived at on the 3rd of June 1997
    LOGGER.info("John lived at " + john.getAddress(new SimpleDate(1997, 7, 3))
            + " on the 3rd of June 1997");

  }
}
