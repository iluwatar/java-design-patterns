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

// ABOUTME: Main application demonstrating the async method invocation pattern with space rockets.
// ABOUTME: Shows how to launch async tasks, use callbacks, and collect results from multiple threads.
package com.iluwatar.async.method.invocation

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.Callable

private val logger = KotlinLogging.logger {}

private const val ROCKET_LAUNCH_LOG_PATTERN = "Space rocket <%s> launched successfully"

/**
 * In this example, we are launching space rockets and deploying lunar rovers.
 *
 * The application demonstrates the async method invocation pattern. The key parts of the pattern
 * are [AsyncResult] which is an intermediate container for an asynchronously evaluated
 * value, [AsyncCallback] which can be provided to be executed on task completion and
 * [AsyncExecutor] that manages the execution of the async tasks.
 *
 * The main method shows example flow of async invocations. The main thread starts multiple tasks
 * with variable durations and then continues its own work. When the main thread has done its job
 * it collects the results of the async tasks. Two of the tasks are handled with callbacks, meaning
 * the callbacks are executed immediately when the tasks complete.
 *
 * Noteworthy difference of thread usage between the async results and callbacks is that the
 * async results are collected in the main thread but the callbacks are executed within the worker
 * threads. This should be noted when working with thread pools.
 *
 * Java provides its own implementations of async method invocation pattern. FutureTask,
 * CompletableFuture and ExecutorService are the real world implementations of this pattern. But due
 * to the nature of parallel programming, the implementations are not trivial. This example does not
 * take all possible scenarios into account but rather provides a simple version that helps to
 * understand the pattern.
 *
 * @see AsyncResult
 * @see AsyncCallback
 * @see AsyncExecutor
 * @see java.util.concurrent.FutureTask
 * @see java.util.concurrent.CompletableFuture
 * @see java.util.concurrent.ExecutorService
 */
@Throws(Exception::class)
fun main() {
    // construct a new executor that will run async tasks
    val executor = ThreadAsyncExecutor()

    // start few async tasks with varying processing times, two last with callback handlers
    val asyncResult1 = executor.startProcess(lazyval(10, 500))
    val asyncResult2 = executor.startProcess(lazyval("test", 300))
    val asyncResult3 = executor.startProcess(lazyval(50L, 700))
    val asyncResult4 = executor.startProcess(lazyval(20, 400), callback("Deploying lunar rover"))
    val asyncResult5 = executor.startProcess(lazyval("callback", 600), callback("Deploying lunar rover"))

    // emulate processing in the current thread while async tasks are running in their own threads
    Thread.sleep(350) // Oh boy, we are working hard here
    log("Mission command is sipping coffee")

    // wait for completion of the tasks
    val result1 = executor.endProcess(asyncResult1)
    val result2 = executor.endProcess(asyncResult2)
    val result3 = executor.endProcess(asyncResult3)
    asyncResult4.await()
    asyncResult5.await()

    // log the results of the tasks, callbacks log immediately when complete
    log(String.format(ROCKET_LAUNCH_LOG_PATTERN, result1))
    log(String.format(ROCKET_LAUNCH_LOG_PATTERN, result2))
    log(String.format(ROCKET_LAUNCH_LOG_PATTERN, result3))
}

/**
 * Creates a callable that lazily evaluates to given value with artificial delay.
 *
 * @param value value to evaluate
 * @param delayMillis artificial delay in milliseconds
 * @return new callable for lazy evaluation
 */
private fun <T> lazyval(
    value: T,
    delayMillis: Long,
): Callable<T> =
    Callable {
        Thread.sleep(delayMillis)
        log(String.format(ROCKET_LAUNCH_LOG_PATTERN, value))
        value
    }

/**
 * Creates a simple callback that logs the complete status of the async result.
 *
 * @param name callback name
 * @return new async callback
 */
private fun <T> callback(name: String): AsyncCallback<T> =
    object : AsyncCallback<T> {
        override fun onComplete(value: T) {
            log("$name <$value>")
        }

        override fun onError(ex: Exception) {
            log("$name failed: ${ex.message}")
        }
    }

private fun log(msg: String) {
    logger.info { msg }
}