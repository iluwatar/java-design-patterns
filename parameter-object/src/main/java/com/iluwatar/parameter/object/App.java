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
package com.iluwatar.parameter.object;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The syntax of Java language doesn’t allow you to declare a method with a predefined value
 * for a parameter. Probably the best option to achieve default method parameters in Java is
 * by using the method overloading. Method overloading allows you to declare several methods
 * with the same name but with a different number of parameters. But the main problem with
 * method overloading as a solution for default parameter values reveals itself when a method
 * accepts multiple parameters. Creating an overloaded method for each possible combination of
 * parameters might be cumbersome. To deal with this issue, the Parameter Object pattern is used.
 * The Parameter Object is simply a wrapper object for all parameters of a method.
 * It is nothing more than just a regular POJO. The advantage of the Parameter Object over a
 * regular method parameter list is the fact that class fields can have default values.
 * Once the wrapper class is created for the method parameter list, a corresponding builder class
 * is also created. Usually it's an inner static class. The final step is to use the builder
 * to construct a new parameter object. For those parameters that are skipped,
 * their default values are going to be used.
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    ParameterObject params = ParameterObject.newBuilder()
        .withType("sneakers")
        .sortBy("brand")
        .build();
    LOGGER.info(params.toString());
    LOGGER.info(new SearchService().search(params));
  }
}
