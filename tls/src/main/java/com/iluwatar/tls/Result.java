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

/*
 * Fiducia IT AG, All rights reserved. Use is subject to license terms.
 */

package com.iluwatar.tls;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Result object that will be returned by the Callable {@link DateFormatCallable} used in {@link
 * App}.
 *
 * @author Thomas Bauer, 2017
 */
public class Result {
  // A list to collect the date values created in one thread
  private List<Date> dateList = new ArrayList<>();

  // A list to collect Exceptions thrown in one threads (should be none in
  // this example)
  private List<String> exceptionList = new ArrayList<>();

  /**
   * Get list of date values collected within a thread execution.
   *
   * @return List of date values collected within an thread execution
   */
  public List<Date> getDateList() {
    return dateList;
  }

  /**
   * Get list of exceptions thrown within a thread execution.
   *
   * @return List of exceptions thrown within an thread execution
   */
  public List<String> getExceptionList() {
    return exceptionList;
  }
}
