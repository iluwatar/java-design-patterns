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
package com.iluwatar.state

// ABOUTME: Entry point demonstrating the State design pattern.
// ABOUTME: Shows how a Mammoth's behavior changes as its internal state alternates over time.

/**
 * In the State pattern, the container object has an internal state object that defines the current
 * behavior. The state object can be changed to alter the behavior.
 *
 * This can be a cleaner way for an object to change its behavior at runtime without resorting to
 * large monolithic conditional statements and thus improves maintainability.
 *
 * In this example the [Mammoth] changes its behavior as time passes by.
 */
fun main() {
    val mammoth = Mammoth()
    mammoth.observe()
    mammoth.timePasses()
    mammoth.observe()
    mammoth.timePasses()
    mammoth.observe()
}
