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
package com.iluwatar.twin

// ABOUTME: Entry point demonstrating the Twin design pattern.
// ABOUTME: Shows how BallItem and BallThread coordinate as twin objects simulating multiple inheritance.

/**
 * Twin pattern is a design pattern which provides a standard solution to simulate multiple
 * inheritance in Java.
 *
 * In this example, the essence of the Twin pattern is the [BallItem] class and [BallThread]
 * class represent the twin objects to coordinate with each other (via the twin reference)
 * like a single class inheriting from [GameItem] and [Thread].
 */
fun main() {
    val ballItem = BallItem()
    val ballThread = BallThread()

    ballItem.twin = ballThread
    ballThread.twin = ballItem

    ballThread.start()

    Thread.sleep(750)

    ballItem.click()

    Thread.sleep(750)

    ballItem.click()

    Thread.sleep(750)

    // exit
    ballThread.stopMe()
}
