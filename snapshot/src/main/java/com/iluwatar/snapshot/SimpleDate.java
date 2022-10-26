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

package com.iluwatar.snapshot;

import java.time.Clock;
import java.time.LocalDate;

/**
 * Class that is used to have global now variables for LocalDate.
 */
public class SimpleDate implements Comparable<SimpleDate> {
  private static Clock clock = Clock.systemUTC();
  private static SimpleDate setDate = null;

  private LocalDate date;

  SimpleDate(int year, int month, int day) {
    date = LocalDate.of(year, month, day);
  }

  SimpleDate(LocalDate date) {
    this.date = date;
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
    return date.equals(that.date);
  }

  @Override
  public int compareTo(SimpleDate otherDate) {
    return date.compareTo(otherDate.getDate());
  }

  public LocalDate getDate() {
    return date;
  }

  /**
   * Set the value considered to be today by the simple date.
   *
   * @param newToday The date to be used for today.
   */
  public static void setToday(SimpleDate newToday) {
    setDate = newToday;
  }

  /**
   * Gets the date set to be today.
   *
   * @return The date set to day
   */
  public static SimpleDate getToday() {
    if (setDate != null) {
      return setDate;
    }
    return new SimpleDate(LocalDate.now(clock));
  }

  @Override
  public String toString() {
    return date.toString();
  }
}