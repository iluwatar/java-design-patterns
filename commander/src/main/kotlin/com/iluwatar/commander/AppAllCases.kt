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

// ABOUTME: Tests various scenarios for the microservices involved in the order placement process.
// ABOUTME: Consolidates success and failure cases for each service: employee, payment, messaging, queue, shipping.
package com.iluwatar.commander

import com.iluwatar.commander.employeehandle.EmployeeDatabase
import com.iluwatar.commander.employeehandle.EmployeeHandle
import com.iluwatar.commander.exceptions.DatabaseUnavailableException
import com.iluwatar.commander.exceptions.ItemUnavailableException
import com.iluwatar.commander.exceptions.PaymentDetailsErrorException
import com.iluwatar.commander.messagingservice.MessagingDatabase
import com.iluwatar.commander.messagingservice.MessagingService
import com.iluwatar.commander.paymentservice.PaymentDatabase
import com.iluwatar.commander.paymentservice.PaymentService
import com.iluwatar.commander.queue.QueueDatabase
import com.iluwatar.commander.shippingservice.ShippingDatabase
import com.iluwatar.commander.shippingservice.ShippingService

class AppAllCases {
    companion object {
        private val retryParams = RetryParams.DEFAULT
        private val timeLimits = TimeLimits.DEFAULT
    }

    internal fun employeeDatabaseUnavailableCase() {
        val ps =
            PaymentService(
                PaymentDatabase(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val ss = ShippingService(ShippingDatabase())
        val ms = MessagingService(MessagingDatabase())
        val eh =
            EmployeeHandle(
                EmployeeDatabase(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val qdb =
            QueueDatabase(
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val c = Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits)
        val user = User("Jim", "ABCD")
        val order = Order(user, "book", 10f)
        c.placeOrder(order)
    }

    internal fun employeeDbSuccessCase() {
        val ps = PaymentService(PaymentDatabase())
        val ss = ShippingService(ShippingDatabase(), ItemUnavailableException())
        val ms = MessagingService(MessagingDatabase())
        val eh =
            EmployeeHandle(
                EmployeeDatabase(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val qdb = QueueDatabase()
        val c = Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits)
        val user = User("Jim", "ABCD")
        val order = Order(user, "book", 10f)
        c.placeOrder(order)
    }

    internal fun messagingDatabaseUnavailableCasePaymentSuccess() {
        val ps = PaymentService(PaymentDatabase())
        val ss = ShippingService(ShippingDatabase())
        val ms =
            MessagingService(
                MessagingDatabase(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val eh = EmployeeHandle(EmployeeDatabase())
        val qdb = QueueDatabase()
        val c = Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits)
        val user = User("Jim", "ABCD")
        val order = Order(user, "book", 10f)
        c.placeOrder(order)
    }

    internal fun messagingDatabaseUnavailableCasePaymentError() {
        val ps =
            PaymentService(
                PaymentDatabase(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val ss = ShippingService(ShippingDatabase())
        val ms =
            MessagingService(
                MessagingDatabase(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val eh = EmployeeHandle(EmployeeDatabase())
        val qdb = QueueDatabase()
        val c = Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits)
        val user = User("Jim", "ABCD")
        val order = Order(user, "book", 10f)
        c.placeOrder(order)
    }

    internal fun messagingDatabaseUnavailableCasePaymentFailure() {
        val ps =
            PaymentService(
                PaymentDatabase(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val ss = ShippingService(ShippingDatabase())
        val ms =
            MessagingService(
                MessagingDatabase(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val eh = EmployeeHandle(EmployeeDatabase())
        val qdb =
            QueueDatabase(
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val c = Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits)
        val user = User("Jim", "ABCD")
        val order = Order(user, "book", 10f)
        c.placeOrder(order)
    }

    internal fun messagingSuccessCase() {
        val ps =
            PaymentService(
                PaymentDatabase(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val ss = ShippingService(ShippingDatabase())
        val ms = MessagingService(MessagingDatabase(), DatabaseUnavailableException())
        val eh = EmployeeHandle(EmployeeDatabase())
        val qdb = QueueDatabase()
        val c = Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits)
        val user = User("Jim", "ABCD")
        val order = Order(user, "book", 10f)
        c.placeOrder(order)
    }

    internal fun paymentNotPossibleCase() {
        val ps =
            PaymentService(
                PaymentDatabase(),
                DatabaseUnavailableException(),
                PaymentDetailsErrorException(),
            )
        val ss = ShippingService(ShippingDatabase())
        val ms = MessagingService(MessagingDatabase(), DatabaseUnavailableException())
        val eh = EmployeeHandle(EmployeeDatabase())
        val qdb = QueueDatabase(DatabaseUnavailableException())
        val c = Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits)
        val user = User("Jim", "ABCD")
        val order = Order(user, "book", 10f)
        c.placeOrder(order)
    }

    internal fun paymentDatabaseUnavailableCase() {
        val ps =
            PaymentService(
                PaymentDatabase(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val ss = ShippingService(ShippingDatabase())
        val ms = MessagingService(MessagingDatabase())
        val eh = EmployeeHandle(EmployeeDatabase())
        val qdb = QueueDatabase()
        val c = Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits)
        val user = User("Jim", "ABCD")
        val order = Order(user, "book", 10f)
        c.placeOrder(order)
    }

    internal fun paymentSuccessCase() {
        val ps =
            PaymentService(
                PaymentDatabase(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val ss = ShippingService(ShippingDatabase())
        val ms = MessagingService(MessagingDatabase(), DatabaseUnavailableException())
        val eh = EmployeeHandle(EmployeeDatabase())
        val qdb = QueueDatabase(DatabaseUnavailableException())
        val c = Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits)
        val user = User("Jim", "ABCD")
        val order = Order(user, "book", 10f)
        c.placeOrder(order)
    }

    internal fun queuePaymentTaskDatabaseUnavailableCase() {
        val ps =
            PaymentService(
                PaymentDatabase(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val ss = ShippingService(ShippingDatabase())
        val ms = MessagingService(MessagingDatabase())
        val eh = EmployeeHandle(EmployeeDatabase())
        val qdb =
            QueueDatabase(
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val c = Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits)
        val user = User("Jim", "ABCD")
        val order = Order(user, "book", 10f)
        c.placeOrder(order)
    }

    internal fun queueMessageTaskDatabaseUnavailableCase() {
        val ps = PaymentService(PaymentDatabase())
        val ss = ShippingService(ShippingDatabase())
        val ms =
            MessagingService(
                MessagingDatabase(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val eh = EmployeeHandle(EmployeeDatabase())
        val qdb =
            QueueDatabase(
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val c = Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits)
        val user = User("Jim", "ABCD")
        val order = Order(user, "book", 10f)
        c.placeOrder(order)
    }

    internal fun queueEmployeeDbTaskDatabaseUnavailableCase() {
        val ps = PaymentService(PaymentDatabase())
        val ss = ShippingService(ShippingDatabase(), ItemUnavailableException())
        val ms = MessagingService(MessagingDatabase())
        val eh =
            EmployeeHandle(
                EmployeeDatabase(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val qdb =
            QueueDatabase(
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val c = Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits)
        val user = User("Jim", "ABCD")
        val order = Order(user, "book", 10f)
        c.placeOrder(order)
    }

    internal fun queueSuccessCase() {
        val ps = PaymentService(PaymentDatabase())
        val ss = ShippingService(ShippingDatabase(), ItemUnavailableException())
        val ms = MessagingService(MessagingDatabase())
        val eh =
            EmployeeHandle(
                EmployeeDatabase(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val qdb = QueueDatabase()
        val c = Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits)
        val user = User("Jim", "ABCD")
        val order = Order(user, "book", 10f)
        c.placeOrder(order)
    }

    internal fun itemUnavailableCase() {
        val ps = PaymentService(PaymentDatabase())
        val ss = ShippingService(ShippingDatabase(), ItemUnavailableException())
        val ms = MessagingService(MessagingDatabase())
        val eh = EmployeeHandle(EmployeeDatabase())
        val qdb = QueueDatabase()
        val c = Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits)
        val user = User("Jim", "ABCD")
        val order = Order(user, "book", 10f)
        c.placeOrder(order)
    }

    internal fun shippingDatabaseUnavailableCase() {
        val ps = PaymentService(PaymentDatabase())
        val ss =
            ShippingService(
                ShippingDatabase(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val ms = MessagingService(MessagingDatabase())
        val eh = EmployeeHandle(EmployeeDatabase())
        val qdb = QueueDatabase()
        val c = Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits)
        val user = User("Jim", "ABCD")
        val order = Order(user, "book", 10f)
        c.placeOrder(order)
    }

    internal fun shippingItemNotPossibleCase() {
        val ps = PaymentService(PaymentDatabase())
        val ss = ShippingService(ShippingDatabase(), ItemUnavailableException())
        val ms = MessagingService(MessagingDatabase())
        val eh = EmployeeHandle(EmployeeDatabase())
        val qdb = QueueDatabase()
        val c = Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits)
        val user = User("Jim", "ABCD")
        val order = Order(user, "book", 10f)
        c.placeOrder(order)
    }

    internal fun shippingSuccessCase() {
        val ps = PaymentService(PaymentDatabase())
        val ss = ShippingService(ShippingDatabase(), ItemUnavailableException())
        val ms = MessagingService(MessagingDatabase(), DatabaseUnavailableException())
        val eh =
            EmployeeHandle(
                EmployeeDatabase(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val qdb = QueueDatabase()
        val c = Commander(eh, ps, ss, ms, qdb, retryParams, timeLimits)
        val user = User("Jim", "ABCD")
        val order = Order(user, "book", 10f)
        c.placeOrder(order)
    }
}

fun main() {
    val app = AppAllCases()

    // Employee Database cases
    app.employeeDatabaseUnavailableCase()
    app.employeeDbSuccessCase()

    // Messaging Database cases
    app.messagingDatabaseUnavailableCasePaymentSuccess()
    app.messagingDatabaseUnavailableCasePaymentError()
    app.messagingDatabaseUnavailableCasePaymentFailure()
    app.messagingSuccessCase()

    // Payment Database cases
    app.paymentNotPossibleCase()
    app.paymentDatabaseUnavailableCase()
    app.paymentSuccessCase()

    // Queue Database cases
    app.queuePaymentTaskDatabaseUnavailableCase()
    app.queueMessageTaskDatabaseUnavailableCase()
    app.queueEmployeeDbTaskDatabaseUnavailableCase()
    app.queueSuccessCase()

    // Shipping Database cases
    app.itemUnavailableCase()
    app.shippingDatabaseUnavailableCase()
    app.shippingItemNotPossibleCase()
    app.shippingSuccessCase()
}