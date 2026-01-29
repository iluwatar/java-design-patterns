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

// ABOUTME: Main application demonstrating the choreography saga pattern.
// ABOUTME: Shows distributed transactions using service-to-service coordination.
package com.iluwatar.saga.choreography

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * This pattern is used in distributed services to perform a group of operations atomically. This is
 * an analog of transaction in a database but in terms of microservices architecture this is
 * executed in a distributed environment
 *
 * A saga is a sequence of local transactions in a certain context. If one transaction fails for
 * some reason, the saga executes compensating transactions(rollbacks) to undo the impact of the
 * preceding transactions.
 *
 * In this approach, there are no mediators or orchestrators services. All chapters are handled
 * and moved by services manually.
 *
 * The major difference with choreography saga is an ability to handle crashed services
 * (otherwise in choreography services very hard to prevent a saga if one of them has been crashed)
 *
 * @see Saga
 * @see Service
 */
object SagaApplication {

    /** Main method. */
    @JvmStatic
    fun main(args: Array<String>) {
        val sd = serviceDiscovery()
        val service = sd.findAny()
        val goodOrderSaga = service.execute(newSaga("good_order"))
        val badOrderSaga = service.execute(newSaga("bad_order"))
        logger.info {
            "orders: goodOrder is ${goodOrderSaga.result}, badOrder is ${badOrderSaga.result}"
        }
    }

    private fun newSaga(value: Any): Saga {
        return Saga.create()
            .chapter("init an order")
            .setInValue(value)
            .chapter("booking a Fly")
            .chapter("booking a Hotel")
            .chapter("withdrawing Money")
    }

    private fun serviceDiscovery(): ServiceDiscoveryService {
        val sd = ServiceDiscoveryService()
        return sd.discover(OrderService(sd))
            .discover(FlyBookingService(sd))
            .discover(HotelBookingService(sd))
            .discover(WithdrawMoneyService(sd))
    }
}
