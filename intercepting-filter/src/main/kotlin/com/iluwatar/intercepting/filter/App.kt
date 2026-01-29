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

// ABOUTME: Main entry point demonstrating the Intercepting Filter design pattern.
// ABOUTME: Sets up filter chain for order validation before processing.
package com.iluwatar.intercepting.filter

/**
 * When a request enters a Web application, it often must pass several entrance tests prior to the
 * main processing stage. For example, - Has the client been authenticated? - Does the client have a
 * valid session? - Is the client's IP address from a trusted network? - Does the request path
 * violate any constraints? - What encoding does the client use to send the data? - Do we support
 * the browser type of the client? Some of these checks are tests, resulting in a yes or no answer
 * that determines whether processing will continue. Other checks manipulate the incoming data
 * stream into a form suitable for processing.
 *
 * The classic solution consists of a series of conditional checks, with any failed check
 * aborting the request. Nested if/else statements are a standard strategy, but this solution leads
 * to code fragility and a copy-and-paste style of programming, because the flow of the filtering
 * and the action of the filters is compiled into the application.
 *
 * The key to solving this problem in a flexible and unobtrusive manner is to have a simple
 * mechanism for adding and removing processing components, in which each component completes a
 * specific filtering action. This is the Intercepting Filter pattern in action.
 *
 * In this example we check whether the order request is valid through pre-processing done via
 * [Filter]. Each field has its own corresponding [Filter].
 */
fun main() {
    val filterManager = FilterManager()
    filterManager.addFilter(NameFilter())
    filterManager.addFilter(ContactFilter())
    filterManager.addFilter(AddressFilter())
    filterManager.addFilter(DepositFilter())
    filterManager.addFilter(OrderFilter())

    val client = Client()
    client.setFilterManager(filterManager)
}
