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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Test of the Callable
 * <p>
 * In this test {@link DateFormatCallable} is used by 4 threads in parallel
 * <p>
 * After a successful run 5 date values should be in the result object of each thread. All dates
 * should have the same value (15.11.2015). To avoid problems with time zone not the date instances
 * themselves are compared by the test. For the test the dates are converted into string format
 * DD.MM.YYY
 * <p>
 * Additionally the number of list entries are tested for both the list with the date values and the
 * list with the exceptions
 *
 * @author Thomas Bauer, January 2017
 */
public class DateFormatCallableTestMultiThread {

  // Class variables used in setup() have to be static because setup() has to be static
  /**
   * Result object given back by DateFormatCallable, one for each thread -- Array with converted
   * date values -- Array with thrown exceptions
   */
  private static final Result[] result = new Result[4];

  /**
   * The date values created by the run of of DateFormatRunnalbe. List will be filled in the setup()
   * method
   */
  @SuppressWarnings("serial")
  private static class StringArrayList extends ArrayList<String> {
    /* nothing needed here */
  }

  private static final List<String>[] createdDateValues = new StringArrayList[4];

  /**
   * Expected number of date values in the date value list created by each thread
   */
  private final int expectedCounterDateValues = 5;

  /**
   * Expected number of exceptions in the exception list created by each thread
   */
  private final int expectedCounterExceptions = 0;

  /**
   * Expected content of the list containing the date values created by each thread
   */
  private final List<String> expectedDateValues =
      List.of("15.11.2015", "15.11.2015", "15.11.2015", "15.11.2015", "15.11.2015");

  /**
   * Run Callable and prepare results for usage in the test methods
   */
  @BeforeAll
  public static void setup() {
    // Create a callable
    var callableDf = new DateFormatCallable("dd/MM/yyyy", "15/12/2015");
    // start thread using the Callable instance
    var executor = Executors.newCachedThreadPool();
    var futureResult1 = executor.submit(callableDf);
    var futureResult2 = executor.submit(callableDf);
    var futureResult3 = executor.submit(callableDf);
    var futureResult4 = executor.submit(callableDf);
    try {
      result[0] = futureResult1.get();
      result[1] = futureResult2.get();
      result[2] = futureResult3.get();
      result[3] = futureResult4.get();
      for (var i = 0; i < result.length; i++) {
        createdDateValues[i] = convertDatesToString(result[i]);
      }
    } catch (Exception e) {
      fail("Setup failed: " + e);
    }
    executor.shutdown();
  }

  private static List<String> convertDatesToString(Result res) {
    // Format date value as DD.MM.YYYY
    if (res == null || res.getDateList() == null || res.getDateList().size() == 0) {
      return null;
    }
    var returnList = new StringArrayList();

    for (var dt : res.getDateList()) {
      var cal = Calendar.getInstance();
      cal.setTime(dt);
      returnList.add(cal.get(Calendar.DAY_OF_MONTH) + "."
          + cal.get(Calendar.MONTH) + "."
          + cal.get(Calendar.YEAR)
      );
    }
    return returnList;
  }

  /**
   * Test date values after the run of DateFormatRunnalbe. A correct run should deliver 5 times
   * 15.12.2015 by each thread
   */
  @Test
  void testDateValues() {
    for (var createdDateValue : createdDateValues) {
      assertEquals(expectedDateValues, createdDateValue);
    }
  }

  /**
   * Test number of dates in the list after the run of DateFormatRunnalbe. A correct run should
   * deliver 5 date values by each thread
   */
  @Test
  void testCounterDateValues() {
    for (var value : result) {
      assertEquals(expectedCounterDateValues, value.getDateList().size());
    }
  }

  /**
   * Test number of Exceptions in the list after the run of DateFormatRunnalbe. A correct run should
   * deliver no exceptions
   */
  @Test
  void testCounterExceptions() {
    for (var value : result) {
      assertEquals(expectedCounterExceptions, value.getExceptionList().size());
    }
  }
}
