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

// ABOUTME: Test for orchestration saga with realistic booking services.
// ABOUTME: Verifies rollback and crash handling for bad and crashed orders.
package com.iluwatar.saga.orchestration

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Test to check general logic
 */
class SagaOrchestratorTest {

    @Test
    fun execute() {
        val sagaOrchestrator = SagaOrchestrator(newSaga(), serviceDiscovery())
        val badOrder = sagaOrchestrator.execute("bad_order")
        val crashedOrder = sagaOrchestrator.execute("crashed_order")

        assertEquals(Saga.Result.ROLLBACK, badOrder)
        assertEquals(Saga.Result.CRASHED, crashedOrder)
    }

    private fun newSaga(): Saga {
        return Saga.create()
            .chapter("init an order")
            .chapter("booking a Fly")
            .chapter("booking a Hotel")
            .chapter("withdrawing Money")
    }

    private fun serviceDiscovery(): ServiceDiscoveryService {
        return ServiceDiscoveryService()
            .discover(OrderService())
            .discover(FlyBookingService())
            .discover(HotelBookingService())
            .discover(WithdrawMoneyService())
    }
}
