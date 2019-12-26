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

package com.iluwatar.tls;

import java.util.Calendar;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ThreadLocal pattern
 *
 * <p>This App shows how to create an isolated space per each thread. In this example the usage of
 * SimpleDateFormat is made to be thread-safe. This is an example of the ThreadLocal pattern.
 *
 * <p>By applying the ThreadLocal pattern you can keep track of application instances or locale
 * settings throughout the handling of a request. The ThreadLocal class works like a static
 * variable, with the exception that it is only bound to the current thread! This allows us to use
 * static variables in a thread-safe way.
 *
 * <p>In Java, thread-local variables are implemented by the ThreadLocal class object. ThreadLocal
 * holds a variable of type T, which is accessible via get/set methods.
 *
 * <p>SimpleDateFormat is one of the basic Java classes and is not thread-safe. If you do not
 * isolate the instance of SimpleDateFormat per each thread then problems arise.
 *
 * <p>App converts the String date value 15/12/2015 to the Date format using the Java class
 * SimpleDateFormat. It does this 20 times using 4 threads, each doing it 5 times. With the usage of
 * as ThreadLocal in DateFormatCallable everything runs well. But if you comment out the ThreadLocal
 * variant (marked with "//TLTL") and comment in the non ThreadLocal variant (marked with
 * "//NTLNTL") you can see what will happen without the ThreadLocal. Most likely you will get
 * incorrect date values and / or exceptions.
 *
 * <p>This example clearly show what will happen when using non thread-safe classes in a thread. In
 * real life this may happen one in of 1.000 or 10.000 conversions and those are really hard to find
 * errors.
 *
 * @author Thomas Bauer, 2017
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    var counterDateValues = 0;
    var counterExceptions = 0;

    // Create a callable
    var callableDf = new DateFormatCallable("dd/MM/yyyy", "15/12/2015");
    // start 4 threads, each using the same Callable instance
    var executor = Executors.newCachedThreadPool();

    var futureResult1 = executor.submit(callableDf);
    var futureResult2 = executor.submit(callableDf);
    var futureResult3 = executor.submit(callableDf);
    var futureResult4 = executor.submit(callableDf);
    try {
      var result = new Result[4];
      result[0] = futureResult1.get();
      result[1] = futureResult2.get();
      result[2] = futureResult3.get();
      result[3] = futureResult4.get();

      // Print results of thread executions (converted dates and raised exceptions)
      // and count them
      for (var value : result) {
        counterDateValues = counterDateValues + printAndCountDates(value);
        counterExceptions = counterExceptions + printAndCountExceptions(value);
      }

      // a correct run should deliver 20 times 15.12.2015
      // and a correct run shouldn't deliver any exception
      LOGGER.info("The List dateList contains " + counterDateValues + " date values");
      LOGGER.info("The List exceptionList contains " + counterExceptions + " exceptions");

    } catch (Exception e) {
      LOGGER.info("Abnormal end of program. Program throws exception: " + e);
    }
    executor.shutdown();
  }

  /**
   * Print result (date values) of a thread execution and count dates.
   *
   * @param res contains results of a thread execution
   */
  private static int printAndCountDates(Result res) {
    // a correct run should deliver 5 times 15.12.2015 per each thread
    var counter = 0;
    for (var dt : res.getDateList()) {
      counter++;
      var cal = Calendar.getInstance();
      cal.setTime(dt);
      // Formatted output of the date value: DD.MM.YYYY
      LOGGER.info(cal.get(Calendar.DAY_OF_MONTH) + "."
          + cal.get(Calendar.MONTH) + "."
          + cal.get(Calendar.YEAR)
      );
    }
    return counter;
  }

  /**
   * Print result (exceptions) of a thread execution and count exceptions.
   *
   * @param res contains results of a thread execution
   * @return number of dates
   */
  private static int printAndCountExceptions(Result res) {
    // a correct run shouldn't deliver any exception
    var counter = 0;
    for (var ex : res.getExceptionList()) {
      counter++;
      LOGGER.info(ex);
    }
    return counter;
  }
}
