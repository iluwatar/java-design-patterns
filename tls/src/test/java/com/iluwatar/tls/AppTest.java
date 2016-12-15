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

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * 
 * Application test
 * 
 * In this test {@link App} is executed. After the run of App the converted Data is available in
 * the static lists created by the run of the app.
 * <p>
 * After a successful run 20 date values should be in the date value list. All dates should have
 * the same value (15.11.2015). To avoid problems with time zone not the date instances themselve
 * are compared in the test. For the test the dates are converted in a string format DD.MM.YYY
 * <p>
 * Additionally the number of list entries are tested for both the list with the date values
 * and the list with the exceptions 
 *
 */
public class AppTest {

  // Class variables used in setup() have to be static because the Compiler wants the 
  // setup() method to be static
  /**
   * Number of date values in the list created by the run of App. Will be set in setup()
   */
  static int actualCounterDateValues = 0;

  /**
   * Number of exceptions in the list created by the run of App. Will be set in setup()
   */
  static int actualCounterExceptions = 0;

  /**
   * The date values created by the run of App. List will be filled in the setup() method
   */
  static List<String> actualDateValues = new ArrayList<String>();

  /**
   * Expected number of date values in the date value list created by the run of App
   */
  int expectedCounterDateValues = 20;

  /**
   * Expected number of exceptions in the exception list created by the run of App. 
   */
  int expectedCounterExceptions = 0;

  /**
   * Expected content of the list containing the date values created by the run of App
   */
  List<String> expectedDateValues = Arrays.asList("15.11.2015", "15.11.2015", "15.11.2015", "15.11.2015", "15.11.2015",
      "15.11.2015", "15.11.2015", "15.11.2015", "15.11.2015", "15.11.2015", "15.11.2015", "15.11.2015", "15.11.2015",
      "15.11.2015", "15.11.2015", "15.11.2015", "15.11.2015", "15.11.2015", "15.11.2015", "15.11.2015");

  /**
   * Run App. After this run the result is available in the static lists
   */
  @BeforeClass
  public static void setup() {
    String[] args = {};
    App.main(args);

    // Prepare data created by the run of App for the tests
    for (Date dt : App.dateList) {
      actualCounterDateValues++;
      // a correct run should deliver 20 times 15.12.2015
      Calendar cal = Calendar.getInstance();
      cal.setTime(dt);
      // Convert date value to string format DD.MM.YYYY
      actualDateValues.add(
          cal.get(Calendar.DAY_OF_MONTH) + "." + cal.get(Calendar.MONTH) + "." + +cal.get(Calendar.YEAR));
    }
    for (@SuppressWarnings("unused") String exc : App.exceptionList) {
      actualCounterExceptions++;
      // a correct run should no exceptions
    }
  }

  /**
   * Test date values after run of App. A correct run should deliver 20 times 15.12.2015
   */
  @Test
  public void testDateValues() {
    assertEquals(expectedDateValues, actualDateValues);
  }

  /**
   * Test number of dates in list after und of App. A correct run should deliver 20 date values
   */
  @Test
  public void testCounterDateValues() {
    assertEquals(expectedCounterDateValues, actualCounterDateValues);
  }

  /**
   * Test number of Exceptions in list after und of App. A correct run should deliver no exceptions
   */
  @Test
  public void testCounterExceptions() {
    assertEquals(expectedCounterExceptions, actualCounterExceptions);
  }
}
