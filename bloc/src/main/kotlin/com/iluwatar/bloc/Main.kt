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

// ABOUTME: Entry point for the BLoC pattern demonstration application.
// ABOUTME: Initializes the GUI to showcase Business Logic Component pattern with reactive state.
package com.iluwatar.bloc

/**
 * The BLoC (Business Logic Component) pattern is a software design pattern primarily used in
 * Flutter applications. It facilitates the separation of business logic from UI code, making the
 * application more modular, testable, and scalable. The BLoC pattern uses streams to manage the
 * flow of data and state changes, allowing widgets to react to new states as they arrive. In the
 * BLoC pattern, the application is divided into three key components:
 * - Input streams: Represent user interactions or external events fed into the BLoC.
 * - Business logic: Processes the input and determines the resulting state or actions.
 * - Output streams: Emit the updated state for the UI to consume.
 *
 * The BLoC pattern is especially useful in reactive programming scenarios and aligns well
 * with the declarative nature of Flutter. By using this pattern, developers can ensure a clear
 * separation of concerns, enhance reusability, and maintain consistent state management throughout
 * the application.
 */
fun main() {
    val blocUi = BlocUi()
    blocUi.createAndShowUi()
}