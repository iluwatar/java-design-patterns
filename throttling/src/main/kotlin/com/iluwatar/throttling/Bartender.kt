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

import com.iluwatar.throttling.timer.Throttler
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.random.Random

// ABOUTME: Bartender service that throttles drink orders based on tenant limits.
// ABOUTME: Accepts orders from BarCustomers and enforces per-second call limits.

private val logger = KotlinLogging.logger {}

/**
 * Bartender is a service which accepts a BarCustomer (tenant) and throttles the resource based on
 * the time given to the tenant.
 */
internal class Bartender(
    timer: Throttler,
    private val callsCount: CallsCount
) {
    init {
        timer.start()
    }

    /**
     * Orders a drink from the bartender.
     *
     * @return customer id which is randomly generated
     */
    fun orderDrink(barCustomer: BarCustomer): Int {
        val tenantName = barCustomer.name
        val count = callsCount.getCount(tenantName)
        if (count >= barCustomer.allowedCallsPerSecond) {
            logger.error { "I'm sorry $tenantName, you've had enough for today!" }
            return -1
        }
        callsCount.incrementCount(tenantName)
        logger.debug { "Serving beer to ${barCustomer.name} : [${count + 1} consumed] " }
        return getRandomCustomerId()
    }

    private fun getRandomCustomerId(): Int {
        return Random.nextInt(1, 10000)
    }
}
