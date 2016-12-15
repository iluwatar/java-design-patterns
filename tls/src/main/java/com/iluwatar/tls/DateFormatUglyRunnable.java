/**
 * The MIT License
 * Copyright (c) 2016 Thomas Bauer
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

package com.iluwatar.tls;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * DateFormatUglyRunnable converts string dates to a date format using
 * SimpleDateFormat. The date value and the date format will be passed to the
 * Runnable by the constructor. The constructor creates an instance of
 * SimpleDateFormat and stores it in a class variable. For the complete
 * description of the example see {@link AppUgly}
 *
 */
public class DateFormatUglyRunnable implements Runnable {
  // class variables (members)
  private DateFormat df;
  private String dateValue;

  /**
   * The date format and the date value are passed to the constructor
   * 
   * @param inDateFormat
   *          string date format string, e.g. "dd/MM/yyyy"
   * @param inDateValue
   *          string date value, e.g. "21/06/2016"
   */
  public DateFormatUglyRunnable(String inDateFormat, String inDateValue) {
    this.df = new SimpleDateFormat(inDateFormat);
    this.dateValue = inDateValue;
  }

  /**
   * @see java.lang.Runnable#run()
   */
  @Override
  public void run() {
    System.out.println(Thread.currentThread() + " started executing...");

    // Convert date value to date 5 times
    for (int i = 1; i <= 5; i++) {
      try {
        // Create the date value and store it in dateList
        AppUgly.dateList.add(this.df.parse(this.dateValue));
      } catch (Exception e) {
        // write the Exception to a list and continue work
        AppUgly.exceptionList.add(e.getClass() + ": " + e.getMessage());
      }

    }

    System.out.println(Thread.currentThread() + " finished executing");
  }
}
