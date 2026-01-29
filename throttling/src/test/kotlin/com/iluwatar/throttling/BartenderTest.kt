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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

// ABOUTME: Tests for the Bartender service class.
// ABOUTME: Verifies that throttling limits are correctly enforced for customers.

/**
 * B2BServiceTest class to test the B2BService
 */
class BartenderTest {

    private val callsCount = CallsCount()

    @Test
    fun dummyCustomerApiTest() {
        val tenant = BarCustomer("pirate", 2, callsCount)
        // In order to assure that throttling limits will not be reset, we use an empty throttling
        // implementation
        val timer = Throttler { }
        val service = Bartender(timer, callsCount)

        repeat(5) { service.orderDrink(tenant) }
        val counter = callsCount.getCount(tenant.name)
        assertEquals(2, counter, "Counter limit must be reached")
    }
}
