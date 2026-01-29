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

// ABOUTME: Front desk service managing a fixed thread pool of employees.
// ABOUTME: Demonstrates the Thread-Pool Executor pattern using Java's ExecutorService.
package com.iluwatar.threadpoolexecutor

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

/**
 * FrontDeskService represents the hotel's front desk with a fixed number of employees. This class
 * demonstrates the Thread-Pool Executor pattern using Java's ExecutorService.
 *
 * @property numberOfEmployees the number of employees (threads) at the front desk
 */
class FrontDeskService(val numberOfEmployees: Int) {

    private val executorService: ExecutorService = Executors.newFixedThreadPool(numberOfEmployees)

    init {
        logger.info { "Front desk initialized with $numberOfEmployees employees." }
    }

    /**
     * Submits a regular guest check-in task to an available employee.
     *
     * @param task the check-in task to submit
     * @return a Future representing pending completion of the task
     */
    fun submitGuestCheckIn(task: Runnable): Future<Void?> {
        logger.debug { "Submitting regular guest check-in task" }
        return executorService.submit(task, null)
    }

    /**
     * Submits a VIP guest check-in task to an available employee.
     *
     * @param task the VIP check-in task to submit
     * @return a Future representing pending completion of the task
     */
    fun <T> submitVipGuestCheckIn(task: Callable<T>): Future<T> {
        logger.debug { "Submitting VIP guest check-in task" }
        return executorService.submit(task)
    }

    /**
     * Closes the front desk after all currently checked-in guests are processed. No new check-ins
     * will be accepted.
     */
    fun shutdown() {
        logger.info { "Front desk is closing - no new guests will be accepted." }
        executorService.shutdown()
    }

    /**
     * Waits for all check-in processes to complete or until timeout.
     *
     * @param timeout the maximum time to wait
     * @param unit the time unit of the timeout argument
     * @return true if all tasks completed, false if timeout elapsed
     * @throws InterruptedException if interrupted while waiting
     */
    @Throws(InterruptedException::class)
    fun awaitTermination(timeout: Long, unit: TimeUnit): Boolean {
        logger.info { "Waiting for all check-ins to complete (max wait: $timeout $unit)" }
        return executorService.awaitTermination(timeout, unit)
    }
}
