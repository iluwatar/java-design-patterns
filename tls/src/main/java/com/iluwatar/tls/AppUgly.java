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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 
 * This App shows an example for problems with global thread variables. A global
 * thread variable is a variable defined as class variable of a
 * XyzRunnable-Class. In this example in the class
 * {@link DateFormatUglyRunnable}
 * <p>
 * Example use case: A well known problem with threads are non thread-safe Java
 * classes. One example is the class SimpleDateFormat.
 * <p>
 * In the example an instance of SimpleDateFormat is created in the constructor
 * of the Runnable. The constructor initializes a class variable with the
 * instance. The Runnable only does convert a string date to a date format using
 * the instance of SimpleDateFormat. It does this 5 times.
 * <p>
 * In the example 4 threads are started to do the same. If you are lucky
 * everything works well. But if you try more often you will happen to see
 * Exceptions. And these Exceptions arise on arbitrary points of the run.
 * <p>
 * The reason for this exceptions is: The threads try to use internal instance
 * variables of the SimpleDateFormat instance at the same time (the date is
 * stored internal as member variable during parsing and may be overwritten by
 * another thread during a parse is still running)
 * <p>
 * And even without Exceptions the run may not deliver the correct results. 
 * All date values should be the same after the run. Check it
 * 
 */
public class AppUgly {
  // A list to collect the date values created in the the threads
  static List<Date> dateList = Collections.synchronizedList(new ArrayList<Date>());
  // A list to collect Exceptions thrown in the threads
  static List<String> exceptionList = Collections.synchronizedList(new ArrayList<String>());

  /**
   * Program entry point
   * 
   * @param args
   *          command line args
   */
  public static void main(String[] args) {
    int counterDateValues = 0;
    int counterExceptions = 0;

    // Prepare the Runnable
    DateFormatUglyRunnable runnableDf = new DateFormatUglyRunnable("dd/MM/yyyy", "15/12/2015");

    // 4 threads using the same Runnable
    Thread t1 = new Thread(runnableDf);
    Thread t2 = new Thread(runnableDf);
    Thread t3 = new Thread(runnableDf);
    Thread t4 = new Thread(runnableDf);
    t1.start();
    t2.start();
    t3.start();
    t4.start();
    try {
      t1.join();
      t2.join();
      t3.join();
      t4.join();
    } catch (InterruptedException e) {
      // Action not coded here
    }
    for (Date dt : dateList) {
      // a correct run should deliver 20 times 15.12.2015
      counterDateValues++;
      Calendar cal = Calendar.getInstance();
      cal.setTime(dt);
      // Formatted output of the date value: DD.MM.YYYY
      System.out.println(cal.get(Calendar.DAY_OF_MONTH) + "." + cal.get(Calendar.MONTH) + "." + cal.get(Calendar.YEAR));
    }
    for (String ex : exceptionList) {
      // a correct run shouldn't deliver any exception
      counterExceptions++;
      System.out.println(ex);
    }
    System.out.println("The List dateList contains " + counterDateValues + " date values");
    System.out.println("The List exceptionList contains " + counterExceptions + " exceptions");
  }
}
