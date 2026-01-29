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
package com.iluwatar.throttling

import com.iluwatar.throttling.timer.ThrottleTimerImpl
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

// ABOUTME: Demonstrates the Throttling pattern with bartender serving customers.
// ABOUTME: Shows time-based throttling limiting calls per second for different tenants.

private val logger = KotlinLogging.logger {}

/**
 * Throttling pattern is a design pattern to throttle or limit the use of resources or even a
 * complete service by users or a particular tenant. This can allow systems to continue to function
 * and meet service level agreements, even when an increase in demand places load on resources.
 *
 * In this example there is a [Bartender] serving beer to [BarCustomer]s. This is a
 * time based throttling, i.e. only a certain number of calls are allowed per second. [BarCustomer]
 * is the service tenant class having a name and the number of calls allowed. [Bartender]
 * is the service which is consumed by the tenants and is throttled.
 */
fun main() {
    val callsCount = CallsCount()
    val human = BarCustomer("young human", 2, callsCount)
    val dwarf = BarCustomer("dwarf soldier", 4, callsCount)

    val executorService = Executors.newFixedThreadPool(2)

    executorService.execute { makeServiceCalls(human, callsCount) }
    executorService.execute { makeServiceCalls(dwarf, callsCount) }

    executorService.shutdown()
    try {
        if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            executorService.shutdownNow()
        }
    } catch (e: InterruptedException) {
        executorService.shutdownNow()
    }
}

/**
 * Make calls to the bartender.
 */
private fun makeServiceCalls(barCustomer: BarCustomer, callsCount: CallsCount) {
    val timer = ThrottleTimerImpl(1000, callsCount)
    val service = Bartender(timer, callsCount)
    // Sleep is introduced to keep the output in check and easy to view and analyze the results.
    repeat(50) {
        service.orderDrink(barCustomer)
        try {
            Thread.sleep(100)
        } catch (e: InterruptedException) {
            logger.error { "Thread interrupted: ${e.message}" }
        }
    }
}
