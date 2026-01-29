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

// ABOUTME: Demonstrates the Half-Sync/Half-Async pattern with arithmetic sum tasks.
// ABOUTME: Shows how to execute long-running tasks asynchronously without blocking the main thread.
package com.iluwatar.halfsynchalfasync

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.LinkedBlockingQueue

private val logger = KotlinLogging.logger {}

/**
 * This application demonstrates Half-Sync/Half-Async pattern. Key parts of the pattern are [AsyncTask]
 * and [AsynchronousService].
 *
 * **PROBLEM**
 *
 * A concurrent system have a mixture of short duration, mid-duration and long duration tasks. Mid
 * or long duration tasks should be performed asynchronously to meet quality of service
 * requirements.
 *
 * **INTENT**
 *
 * The intent of this pattern is to separate the synchronous and asynchronous processing in the
 * concurrent application by introducing two intercommunicating layers - one for sync and one for
 * async. This simplifies the programming without unduly affecting the performance.
 *
 * **APPLICABILITY**
 *
 * UNIX network subsystems - In operating systems network operations are carried out asynchronously
 * with help of hardware level interrupts.
 *
 * CORBA - At the asynchronous layer one thread is associated with each socket that is connected to
 * the client. Thread blocks waiting for CORBA requests from the client. On receiving request it is
 * inserted in the queuing layer which is then picked up by synchronous layer which processes the
 * request and sends response back to the client.
 *
 * Android AsyncTask framework - Framework provides a way to execute long-running blocking calls,
 * such as downloading a file, in background threads so that the UI thread remains free to respond
 * to user inputs.
 *
 * **IMPLEMENTATION**
 *
 * The main method creates an asynchronous service which does not block the main thread while the
 * task is being performed. The main thread continues its work which is similar to Async Method
 * Invocation pattern. The difference between them is that there is a queuing layer between
 * Asynchronous layer and synchronous layer, which allows for different communication patterns
 * between both layers. Such as Priority Queue can be used as queuing layer to prioritize the way
 * tasks are executed. Our implementation is just one simple way of implementing this pattern, there
 * are many variants possible as described in its applications.
 */
fun main() {
    val service = AsynchronousService(LinkedBlockingQueue())
    /*
     * A new task to calculate sum is received but as this is main thread, it should not block. So
     * it passes it to the asynchronous task layer to compute and proceeds with handling other
     * incoming requests. This is particularly useful when main thread is waiting on Socket to
     * receive new incoming requests and does not wait for particular request to be completed before
     * responding to new request.
     */
    service.execute(ArithmeticSumTask(1000))

    /*
     * New task received, lets pass that to async layer for computation. So both requests will be
     * executed in parallel.
     */
    service.execute(ArithmeticSumTask(500))
    service.execute(ArithmeticSumTask(2000))
    service.execute(ArithmeticSumTask(1))

    service.close()
}

/** ArithmeticSumTask. */
internal class ArithmeticSumTask(private val numberOfElements: Long) : AsyncTask<Long> {

    /*
     * This will be called in context of the main thread where some validations can be done
     * regarding the inputs. Such as it must be greater than 0. It's a small computation which can
     * be performed in main thread. If we did validate the input in background thread then we pay
     * the cost of context switching which is much more than validating it in main thread.
     */
    override fun onPreCall() {
        if (numberOfElements < 0) {
            throw IllegalArgumentException("n is less than 0")
        }
    }

    /*
     * This is the long-running task that is performed in background. In our example the long-running
     * task is calculating arithmetic sum with artificial delay.
     */
    override fun call(): Long {
        return ap(numberOfElements)
    }

    override fun onPostCall(result: Long) {
        // Handle the result of computation
        logger.info { result.toString() }
    }

    override fun onError(throwable: Throwable) {
        throw IllegalStateException("Should not occur")
    }
}

private fun ap(i: Long): Long {
    try {
        Thread.sleep(i)
    } catch (e: InterruptedException) {
        logger.error(e) { "Exception caught." }
    }
    return i * (i + 1) / 2
}
