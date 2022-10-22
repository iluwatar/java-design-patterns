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
 * Class that is used to wrap year, month, day values without correction.
 * Note that this class also does not consider different time zones being used in an implementation.
 */
public class SimpleDate implements Comparable<SimpleDate> {
  private final int year;
  private final int month;
  private final int day;

  private static SimpleDate today = new SimpleDate(0, 0, 0);

  SimpleDate(int year, int month, int day) {
    this.year = year;
    this.month = month;
    this.day = day;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SimpleDate that = (SimpleDate) o;
    return year == that.year && month == that.month && day == that.day;
  }

  @Override
  public int compareTo(SimpleDate otherDate) {
    if (equals(otherDate)) {
      return 0;
    }

    if (year > otherDate.year) {
      return 1;
    } else if (year < otherDate.year) {
      return -1;
    }

    if (month > otherDate.month) {
      return 1;
    } else if (month < otherDate.month) {
      return -1;
    }

    if (day > otherDate.day) {
      return 1;
    } else {
      return -1;
    }
  }

  /**
   * Set the value considered to be today by the simple date.
   *
   * @param newToday The date to be set as 'today'.
   */
  public static void setToday(SimpleDate newToday) {
    today = newToday;
  }

  /**
   * Gets the date set to be today.
   *
   * @return The date set to day
   */
  public static SimpleDate getToday() {
    return today;
  }

  @Override
  public String toString() {
    return "" + year + ", " + month + ", " + day;
  }
}