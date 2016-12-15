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
 * ThreadLocal pattern
 * <p>
 * This App shows how to create an isolated space per each thread. In this
 * example the usage of SimpleDateFormat is made to be thread-safe. This is an
 * example of the ThreadLocal pattern.
 * <p>
 * By applying the ThreadLocal pattern you can keep track of application
 * instances or locale settings throughout the handling of a request. The
 * ThreadLocal class works like a static variable, with the exception that it is
 * only bound to the current thread! This allows us to use static variables in a
 * thread-safe way.
 * <p>
 * In Java, thread-local variables are implemented by the ThreadLocal class
 * object. ThreadLocal holds variable of type T, which is accessible via get/set
 * methods.
 * <p>
 * SimpleDateFormat is one of the basic Java classes and is not thread-safe. If
 * you do not isolate the instance of SimpleDateFormat per each thread then
 * problems arise. These problems are described with the example {@link AppUgly}
 * 
 */
public class App {
  // A list to collect the date values created in the the threads
  static List<Date> dateList = Collections.synchronizedList(new ArrayList<Date>());

  // A list to collect Exceptions thrown in the threads (should be none in
  // this example)
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

    // Create a runnable
    DateFormatRunnable runnableDf = new DateFormatRunnable("dd/MM/yyyy", "15/12/2015");
    // start 4 threads, each using the same Runnable instance
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
      System.out.println(
          cal.get(Calendar.DAY_OF_MONTH) + "." + cal.get(Calendar.MONTH) + "." + +cal.get(Calendar.YEAR));
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
