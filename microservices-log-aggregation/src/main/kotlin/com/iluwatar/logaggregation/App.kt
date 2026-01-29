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
// ABOUTME: Main application demonstrating the log aggregation pattern.
// ABOUTME: Creates services, generates logs, aggregates them, and displays the results.
package com.iluwatar.logaggregation

/**
 * The main application class responsible for demonstrating the log aggregation mechanism. Creates
 * services, generates logs, aggregates, and finally displays the logs.
 */
object App {
    /**
     * The entry point of the application.
     *
     * @param args Command line arguments.
     * @throws InterruptedException If any thread has interrupted the current thread.
     */
    @JvmStatic
    @Throws(InterruptedException::class)
    fun main(args: Array<String>) {
        val centralLogStore = CentralLogStore()
        val aggregator = LogAggregator(centralLogStore, LogLevel.INFO)

        val serviceA = LogProducer("ServiceA", aggregator)
        val serviceB = LogProducer("ServiceB", aggregator)

        serviceA.generateLog(LogLevel.INFO, "This is an INFO log from ServiceA")
        serviceB.generateLog(LogLevel.ERROR, "This is an ERROR log from ServiceB")
        serviceA.generateLog(LogLevel.DEBUG, "This is a DEBUG log from ServiceA")

        aggregator.stop()
        centralLogStore.displayLogs()
    }
}
