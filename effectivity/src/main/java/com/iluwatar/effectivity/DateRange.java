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

/**
 * DateRange consists of a start and an end date, and a method includes to test
 * if a {@link SimpleDate} is within those start and end dates.
 */
public class DateRange {
  private final SimpleDate startDate;
  private final SimpleDate endDate;

  public DateRange(SimpleDate startDate, SimpleDate endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  /**
   * Returns a date range that starts at the given date and never ends.
   *
   * @param date The starting date.
   * @return A date range starting at date, and ending at integer max year, month, day.
   */
  public static DateRange startingOn(SimpleDate date) {
    return new DateRange(date,
            new SimpleDate(99999, 12, 31));
  }

  /**
   * Returns if the given date is within the start and end bounds of this range.
   *
   * @param arg Date to check is within bounds.
   * @return True if the date is within bounds (inclusive)
   */
  public boolean includes(SimpleDate arg) {
    return (getStartDate().compareTo(arg) <= 0 & getEndDate().compareTo(arg) >= 0);
  }

  public SimpleDate getStartDate() {
    return startDate;
  }

  public SimpleDate getEndDate() {
    return endDate;
  }

  @Override
  public String toString() {
    return "(" + startDate.toString() + " : " + endDate.toString() + ")";
  }
}
