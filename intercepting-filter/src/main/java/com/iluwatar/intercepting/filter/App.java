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

package com.iluwatar.intercepting.filter;

/**
 * When a request enters a Web application, it often must pass several entrance tests prior to the
 * main processing stage. For example, - Has the client been authenticated? - Does the client have a
 * valid session? - Is the client's IP address from a trusted network? - Does the request path
 * violate any constraints? - What encoding does the client use to send the data? - Do we support
 * the browser type of the client? Some of these checks are tests, resulting in a yes or no answer
 * that determines whether processing will continue. Other checks manipulate the incoming data
 * stream into a form suitable for processing.
 *
 * <p>The classic solution consists of a series of conditional checks, with any failed check
 * aborting the request. Nested if/else statements are a standard strategy, but this solution leads
 * to code fragility and a copy-and-paste style of programming, because the flow of the filtering
 * and the action of the filters is compiled into the application.
 *
 * <p>The key to solving this problem in a flexible and unobtrusive manner is to have a simple
 * mechanism for adding and removing processing components, in which each component completes a
 * specific filtering action. This is the Intercepting Filter pattern in action.
 *
 * <p>In this example we check whether the order request is valid through pre-processing done via
 * {@link Filter}. Each field has its own corresponding {@link Filter}.
 *
 * @author joshzambales
 */
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    var filterManager = new FilterManager();
    filterManager.addFilter(new NameFilter());
    filterManager.addFilter(new ContactFilter());
    filterManager.addFilter(new AddressFilter());
    filterManager.addFilter(new DepositFilter());
    filterManager.addFilter(new OrderFilter());

    var client = new Client();
    client.setFilterManager(filterManager);
  }
}
