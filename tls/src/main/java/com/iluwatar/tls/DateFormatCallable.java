/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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
import java.util.concurrent.Callable;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;

/**
 * DateFormatCallable converts string dates to a date format using SimpleDateFormat. The date format
 * and the date value will be passed to the Callable by the constructor. The constructor creates a
 * instance of SimpleDateFormat and stores it in a ThreadLocal class variable. For the complete
 * description of the example see {@link App}.
 *
 * <p>You can comment out the code marked with //TLTL and comment in the code marked //NTLNTL. Then
 * you can see what will happen if you do not use the ThreadLocal. For details see the description
 * of {@link App}
 *
 * @author Thomas Bauer, 2017
 */
@Slf4j
public class DateFormatCallable implements Callable<Result> {

  // class variables (members)
  private final ThreadLocal<DateFormat> df;    //TLTL
  // private DateFormat df;                 //NTLNTL

  private final String dateValue; // for dateValue Thread Local not needed


  /**
   * The date format and the date value are passed to the constructor.
   *
   * @param inDateFormat string date format string, e.g. "dd/MM/yyyy"
   * @param inDateValue  string date value, e.g. "21/06/2016"
   */
  public DateFormatCallable(String inDateFormat, String inDateValue) {
    final var idf = inDateFormat;                 //TLTL
    this.df = ThreadLocal.withInitial(() -> {          //TLTL
      return new SimpleDateFormat(idf);            //TLTL
    });                                               //TLTL
    // this.df = new SimpleDateFormat(inDateFormat);    //NTLNTL
    this.dateValue = inDateValue;
  }

  @Override
  public Result call() {
    LOGGER.info(Thread.currentThread() + " started executing...");
    var result = new Result();

    // Convert date value to date 5 times
    IntStream.rangeClosed(1, 5).forEach(i -> {
      try {
        // this is the statement where it is important to have the
        // instance of SimpleDateFormat locally
        // Create the date value and store it in dateList
        result.getDateList().add(this.df.get().parse(this.dateValue));   //TLTL
        // result.getDateList().add(this.df.parse(this.dateValue));           //NTLNTL
      } catch (Exception e) {
        // write the Exception to a list and continue work
        result.getExceptionList().add(e.getClass() + ": " + e.getMessage());
      }
    });

    LOGGER.info("{} finished processing part of the thread", Thread.currentThread());

    return result;
  }
}
