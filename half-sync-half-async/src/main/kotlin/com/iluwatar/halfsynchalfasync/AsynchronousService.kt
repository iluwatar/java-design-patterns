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

// ABOUTME: Implements the asynchronous service layer of the Half-Sync/Half-Async pattern.
// ABOUTME: Provides non-blocking task execution using a thread pool with a configurable work queue.
package com.iluwatar.halfsynchalfasync

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.BlockingQueue
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.FutureTask
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

/**
 * This is the asynchronous layer which does not block when a new request arrives. It just passes
 * the request to the synchronous layer which consists of a queue i.e. a [BlockingQueue] and a
 * pool of threads i.e. [ThreadPoolExecutor]. Out of this pool of worker threads one of the
 * thread picks up the task and executes it synchronously in background and the result is posted
 * back to the caller via callback.
 */
class AsynchronousService(workQueue: BlockingQueue<Runnable>) {
    /*
     * This represents the queuing layer as well as synchronous layer of the pattern. The thread pool
     * contains worker threads which execute the tasks in blocking/synchronous manner. Long-running
     * tasks should be performed in the background which does not affect the performance of main
     * thread.
     */
    private val service: ExecutorService = ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS, workQueue)

    /**
     * A non-blocking method which performs the task provided in background and returns immediately.
     *
     * On successful completion of task the result is posted back using callback method
     * [AsyncTask.onPostCall], if task execution is unable to complete normally due to some
     * exception then the reason for error is posted back using callback method
     * [AsyncTask.onError].
     *
     * NOTE: The results are posted back in the context of background thread in this
     * implementation.
     */
    fun <T> execute(task: AsyncTask<T>) {
        try {
            // some small tasks such as validation can be performed here.
            task.onPreCall()
        } catch (e: Exception) {
            task.onError(e)
            return
        }

        service.submit(
            object : FutureTask<T>(task) {
                override fun done() {
                    super.done()
                    try {
                        /*
                         * called in context of background thread. There is other variant possible where result is
                         * posted back and sits in the queue of caller thread which then picks it up for
                         * processing. An example of such a system is Android OS, where the UI elements can only
                         * be updated using UI thread. So result must be posted back in UI thread.
                         */
                        task.onPostCall(get())
                    } catch (e: InterruptedException) {
                        // should not occur
                    } catch (e: ExecutionException) {
                        task.onError(e.cause!!)
                    }
                }
            }
        )
    }

    /** Stops the pool of workers. This is a blocking call to wait for all tasks to be completed. */
    fun close() {
        service.shutdown()
        try {
            service.awaitTermination(10, TimeUnit.SECONDS)
        } catch (ie: InterruptedException) {
            logger.error { "Error waiting for executor service shutdown!" }
        }
    }
}
