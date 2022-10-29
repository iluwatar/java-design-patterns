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

import java.util.HashMap;
import java.util.Map;

/**
 * AddressHistory is a temporal property object, implemented as a temporal collection.
 *
 * @see <a href="https://martinfowler.com/eaaDev/TemporalProperty.html">
 *   https://martinfowler.com/eaaDev/TemporalProperty.html
 *   </a>
 */
public class AddressHistory {
  private HashMap<SimpleDate, String> addressMap;

  /**
   * Create new address history.
   */
  AddressHistory() {
    addressMap = new HashMap<>();
  }

  /**
   * Finds the address associated to the latest date before the given date.
   *
   * @param date The date to check address at
   * @return The address that was being used at the given date
   * @throws IllegalStateException If the date given is earlier than any recorded
   */
  public String get(SimpleDate date) throws IllegalStateException {
    // The most recent date recorded before the given date
    SimpleDate mostRecent = null;

    for (Map.Entry<SimpleDate, String> entry : addressMap.entrySet()) {
      SimpleDate checkDate = entry.getKey();
      // 0 if date = check, 1 if check > date, -1 if check < date
      int afterDate = checkDate.compareTo(date);

      // given date is in map, return the address.
      if (afterDate == 0) {
        return entry.getValue();
      }
      // date being checked is after the requested.
      if (afterDate > 0) {
        continue;
      }

      // if first date found before the given date
      if (mostRecent == null) {
        mostRecent = checkDate;
      }

      // new date is after the current most recent and is before the given
      if (checkDate.compareTo(mostRecent) > 0) {
        mostRecent = checkDate;
      }
    }

    if (mostRecent != null) {
      return addressMap.get(mostRecent);
    }

    throw new IllegalStateException("Customer has no recorded address that early.");
  }

  /**
   * Puts the given date and time into the history.
   *
   * @param date Date of entry
   * @param address Address of entry
   */
  public void put(SimpleDate date, String address) {
    addressMap.put(date, address);
  }
}
