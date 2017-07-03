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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * 
 * Test of the Callable
 * 
 * In this test {@link DateFormatCallable} is tested with only one thread (i.e. without concurrency situation)
 * <p>
 * After a successful run 5 date values should be in the result object. All dates should have
 * the same value (15.11.2015). To avoid problems with time zone not the date instances themselves
 * are compared by the test. For the test the dates are converted into string format DD.MM.YYY
 * <p>
 * Additionally the number of list entries are tested for both the list with the date values
 * and the list with the exceptions 
 * 
 * @author Thomas Bauer, January 2017
 *
 */
public class DateFormatCallableTest {

  // Class variables used in setup() have to be static because setup() has to be static
  /**
   * Result object given back by DateFormatCallable
   *   -- Array with converted date values
   *   -- Array with thrown exceptions
   */
  static Result result;

  /**
   * The date values created by the run of of DateFormatRunnalbe. List will be filled in the setup() method
   */
  static List<String> createdDateValues = new ArrayList<String>();

  /**
   * Expected number of date values in the date value list created by the run of DateFormatRunnalbe
   */
  int expectedCounterDateValues = 5;

  /**
   * Expected number of exceptions in the exception list created by the run of DateFormatRunnalbe. 
   */
  int expectedCounterExceptions = 0;

  /**
   * Expected content of the list containing the date values created by the run of DateFormatRunnalbe
   */
  List<String> expectedDateValues = Arrays.asList("15.11.2015", "15.11.2015", "15.11.2015", "15.11.2015", "15.11.2015");

  /**
   * Run Callable and prepare results for usage in the test methods
   */
  @BeforeClass
  public static void setup() {
    // Create a callable
    DateFormatCallable callableDf = new DateFormatCallable("dd/MM/yyyy", "15/12/2015");
    // start thread using the Callable instance
    ExecutorService executor = Executors.newCachedThreadPool();
    Future<Result> futureResult = executor.submit(callableDf);
    try {
      result = futureResult.get();
      createdDateValues = convertDatesToString(result);
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
    List<String> returnList = new ArrayList<String>();

    for (Date dt : res.getDateList()) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(dt);
      returnList.add(cal.get(Calendar.DAY_OF_MONTH) + "." + cal.get(Calendar.MONTH) + "." + cal.get(Calendar.YEAR));
    }
    return returnList;
  }

  /**
   * Test date values after the run of DateFormatRunnalbe. A correct run should deliver 5 times 15.12.2015
   */
  @Test
  public void testDateValues() {
    assertEquals(expectedDateValues, createdDateValues);
  }

  /**
   * Test number of dates in the list after the run of DateFormatRunnalbe. A correct run should deliver 5 date values
   */
  @Test
  public void testCounterDateValues() {
    assertEquals(expectedCounterDateValues, result.getDateList().size());
  }

  /**
   * Test number of Exceptions in the list after the run of DateFormatRunnalbe. A correct run should deliver 
   * no exceptions
   */
  @Test
  public void testCounterExceptions() {
    assertEquals(expectedCounterExceptions, result.getExceptionList().size());
  }
}
