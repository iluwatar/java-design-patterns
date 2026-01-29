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

// ABOUTME: Test class for Commander pattern implementation.
// ABOUTME: Tests various scenarios for order placement with different service configurations.
package com.iluwatar.commander

import com.iluwatar.commander.employeehandle.EmployeeDatabase
import com.iluwatar.commander.employeehandle.EmployeeHandle
import com.iluwatar.commander.exceptions.DatabaseUnavailableException
import com.iluwatar.commander.exceptions.ItemUnavailableException
import com.iluwatar.commander.exceptions.PaymentDetailsErrorException
import com.iluwatar.commander.exceptions.ShippingNotPossibleException
import com.iluwatar.commander.messagingservice.MessagingDatabase
import com.iluwatar.commander.messagingservice.MessagingService
import com.iluwatar.commander.paymentservice.PaymentDatabase
import com.iluwatar.commander.paymentservice.PaymentService
import com.iluwatar.commander.queue.QueueDatabase
import com.iluwatar.commander.shippingservice.ShippingDatabase
import com.iluwatar.commander.shippingservice.ShippingService
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class CommanderTest {
    private val retryParams = RetryParams(1, 1_000L)
    private val timeLimits = TimeLimits(1L, 1000L, 6000L, 5000L, 2000L)

    companion object {
        private val exceptionList =
            listOf<Exception>(
                DatabaseUnavailableException(),
                ShippingNotPossibleException(),
                ItemUnavailableException(),
                PaymentDetailsErrorException(),
                IllegalStateException(),
            )
        private val appAllCases = AppAllCases()
    }

    private fun buildCommanderObject(nonPaymentException: Boolean = false): Commander {
        val paymentService =
            PaymentService(
                PaymentDatabase(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )

        val shippingService = ShippingService(ShippingDatabase(), DatabaseUnavailableException())
        val messagingService = MessagingService(MessagingDatabase(), DatabaseUnavailableException())

        val employeeHandle =
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
        return Commander(
            employeeHandle,
            paymentService,
            shippingService,
            messagingService,
            qdb,
            retryParams,
            timeLimits,
        )
    }

    private fun buildCommanderObjectVanilla(): Commander {
        val paymentService =
            PaymentService(
                PaymentDatabase(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
                DatabaseUnavailableException(),
            )
        val shippingService = ShippingService(ShippingDatabase())
        val messagingService = MessagingService(MessagingDatabase())
        val employeeHandle =
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
        return Commander(
            employeeHandle,
            paymentService,
            shippingService,
            messagingService,
            qdb,
            retryParams,
            timeLimits,
        )
    }

    private fun buildCommanderObjectUnknownException(): Commander {
        val paymentService = PaymentService(PaymentDatabase(), IllegalStateException())
        val shippingService = ShippingService(ShippingDatabase())
        val messagingService = MessagingService(MessagingDatabase())
        val employeeHandle = EmployeeHandle(EmployeeDatabase(), IllegalStateException())
        val qdb = QueueDatabase(DatabaseUnavailableException(), IllegalStateException())
        return Commander(
            employeeHandle,
            paymentService,
            shippingService,
            messagingService,
            qdb,
            retryParams,
            timeLimits,
        )
    }

    private fun buildCommanderObjectNoPaymentException1(): Commander {
        val paymentService = PaymentService(PaymentDatabase())
        val shippingService = ShippingService(ShippingDatabase())
        val messagingService = MessagingService(MessagingDatabase())
        val employeeHandle = EmployeeHandle(EmployeeDatabase(), IllegalStateException())
        val qdb = QueueDatabase(DatabaseUnavailableException(), IllegalStateException())
        return Commander(
            employeeHandle,
            paymentService,
            shippingService,
            messagingService,
            qdb,
            retryParams,
            timeLimits,
        )
    }

    private fun buildCommanderObjectNoPaymentException2(): Commander {
        val paymentService = PaymentService(PaymentDatabase())
        val shippingService = ShippingService(ShippingDatabase())
        val messagingService = MessagingService(MessagingDatabase(), IllegalStateException())
        val employeeHandle = EmployeeHandle(EmployeeDatabase(), IllegalStateException())
        val qdb = QueueDatabase(DatabaseUnavailableException(), IllegalStateException())
        return Commander(
            employeeHandle,
            paymentService,
            shippingService,
            messagingService,
            qdb,
            retryParams,
            timeLimits,
        )
    }

    private fun buildCommanderObjectNoPaymentException3(): Commander {
        val paymentService = PaymentService(PaymentDatabase())
        val shippingService = ShippingService(ShippingDatabase())
        val messagingService = MessagingService(MessagingDatabase(), DatabaseUnavailableException())
        val employeeHandle = EmployeeHandle(EmployeeDatabase(), IllegalStateException())
        val qdb = QueueDatabase(DatabaseUnavailableException(), IllegalStateException())
        return Commander(
            employeeHandle,
            paymentService,
            shippingService,
            messagingService,
            qdb,
            retryParams,
            timeLimits,
        )
    }

    private fun buildCommanderObjectWithDB(
        includeException: Boolean = false,
        includeDBException: Boolean = false,
        e: Exception = IllegalStateException(),
    ): Commander {
        val l: Exception = if (includeDBException) DatabaseUnavailableException() else e
        val paymentService: PaymentService
        val shippingService: ShippingService
        val messagingService: MessagingService
        val employeeHandle: EmployeeHandle
        if (includeException) {
            paymentService = PaymentService(PaymentDatabase(), l)
            shippingService = ShippingService(ShippingDatabase(), l)
            messagingService = MessagingService(MessagingDatabase(), l)
            employeeHandle = EmployeeHandle(EmployeeDatabase(), l)
        } else {
            paymentService = PaymentService(null)
            shippingService = ShippingService(null)
            messagingService = MessagingService(null)
            employeeHandle = EmployeeHandle(null)
        }

        return Commander(
            employeeHandle,
            paymentService,
            shippingService,
            messagingService,
            null,
            retryParams,
            timeLimits,
        )
    }

    private fun buildCommanderObjectWithoutDB(
        includeException: Boolean = false,
        includeDBException: Boolean = false,
        e: Exception = IllegalStateException(),
    ): Commander {
        val l: Exception = if (includeDBException) DatabaseUnavailableException() else e
        val paymentService: PaymentService
        val shippingService: ShippingService
        val messagingService: MessagingService
        val employeeHandle: EmployeeHandle
        if (includeException) {
            paymentService = PaymentService(null, l)
            shippingService = ShippingService(null, l)
            messagingService = MessagingService(null, l)
            employeeHandle = EmployeeHandle(null, l)
        } else {
            paymentService = PaymentService(null)
            shippingService = ShippingService(null)
            messagingService = MessagingService(null)
            employeeHandle = EmployeeHandle(null)
        }

        return Commander(
            employeeHandle,
            paymentService,
            shippingService,
            messagingService,
            null,
            retryParams,
            timeLimits,
        )
    }

    @Test
    fun testPlaceOrderVanilla() {
        var paymentTime = timeLimits.paymentTime
        var queueTaskTime = timeLimits.queueTaskTime
        var d = 0.1
        while (d < 2) {
            paymentTime = (paymentTime * d).toLong()
            queueTaskTime = (queueTaskTime * d).toLong()
            val c = buildCommanderObjectVanilla()
            val order = Order(User("K", "J"), "pen", 1f)
            for (ms in Order.MessageSent.entries) {
                c.placeOrder(order)
                assertFalse(order.id.isBlank())
            }
            d += 0.1
        }
    }

    @Test
    fun testPlaceOrder() {
        var paymentTime = timeLimits.paymentTime
        var queueTaskTime = timeLimits.queueTaskTime
        var d = 0.1
        while (d < 2) {
            paymentTime = (paymentTime * d).toLong()
            queueTaskTime = (queueTaskTime * d).toLong()
            val c = buildCommanderObject(true)
            val order = Order(User("K", "J"), "pen", 1f)
            for (ms in Order.MessageSent.entries) {
                c.placeOrder(order)
                assertFalse(order.id.isBlank())
            }
            d += 0.1
        }
    }

    @Test
    fun testPlaceOrder2() {
        var paymentTime = timeLimits.paymentTime
        var queueTaskTime = timeLimits.queueTaskTime
        var d = 0.1
        while (d < 2) {
            paymentTime = (paymentTime * d).toLong()
            queueTaskTime = (queueTaskTime * d).toLong()
            val c = buildCommanderObject(false)
            val order = Order(User("K", "J"), "pen", 1f)
            for (ms in Order.MessageSent.entries) {
                c.placeOrder(order)
                assertFalse(order.id.isBlank())
            }
            d += 0.1
        }
    }

    @Test
    fun testPlaceOrderNoException1() {
        var paymentTime = timeLimits.paymentTime
        var queueTaskTime = timeLimits.queueTaskTime
        var d = 0.1
        while (d < 2) {
            paymentTime = (paymentTime * d).toLong()
            queueTaskTime = (queueTaskTime * d).toLong()
            val c = buildCommanderObjectNoPaymentException1()
            val order = Order(User("K", "J"), "pen", 1f)
            for (ms in Order.MessageSent.entries) {
                c.placeOrder(order)
                assertFalse(order.id.isBlank())
            }
            d += 0.1
        }
    }

    @Test
    fun testPlaceOrderNoException2() {
        var paymentTime = timeLimits.paymentTime
        var queueTaskTime = timeLimits.queueTaskTime
        var d = 0.1
        while (d < 2) {
            paymentTime = (paymentTime * d).toLong()
            queueTaskTime = (queueTaskTime * d).toLong()
            val c = buildCommanderObjectNoPaymentException2()
            val order = Order(User("K", "J"), "pen", 1f)
            for (ms in Order.MessageSent.entries) {
                c.placeOrder(order)
                assertFalse(order.id.isBlank())
            }
            d += 0.1
        }
    }

    @Test
    fun testPlaceOrderNoException3() {
        var paymentTime = timeLimits.paymentTime
        var queueTaskTime = timeLimits.queueTaskTime
        var d = 0.1
        while (d < 2) {
            paymentTime = (paymentTime * d).toLong()
            queueTaskTime = (queueTaskTime * d).toLong()
            val c = buildCommanderObjectNoPaymentException3()
            val order = Order(User("K", "J"), "pen", 1f)
            for (ms in Order.MessageSent.entries) {
                c.placeOrder(order)
                assertFalse(order.id.isBlank())
            }
            d += 0.1
        }
    }

    @Test
    fun testPlaceOrderNoException4() {
        var paymentTime = timeLimits.paymentTime
        var queueTaskTime = timeLimits.queueTaskTime
        var d = 0.1
        while (d < 2) {
            paymentTime = (paymentTime * d).toLong()
            queueTaskTime = (queueTaskTime * d).toLong()
            val c = buildCommanderObjectNoPaymentException3()
            val order = Order(User("K", "J"), "pen", 1f)
            for (ms in Order.MessageSent.entries) {
                c.placeOrder(order)
                c.placeOrder(order)
                c.placeOrder(order)
                assertFalse(order.id.isBlank())
            }
            d += 0.1
        }
    }

    @Test
    fun testPlaceOrderUnknownException() {
        var paymentTime = timeLimits.paymentTime
        var queueTaskTime = timeLimits.queueTaskTime
        var messageTime = timeLimits.messageTime
        var employeeTime = timeLimits.employeeTime
        var queueTime = timeLimits.queueTime
        var d = 0.1
        while (d < 2) {
            paymentTime = (paymentTime * d).toLong()
            queueTaskTime = (queueTaskTime * d).toLong()
            messageTime = (messageTime * d).toLong()
            employeeTime = (employeeTime * d).toLong()
            queueTime = (queueTime * d).toLong()
            val c = buildCommanderObjectUnknownException()
            val order = Order(User("K", "J"), "pen", 1f)
            for (ms in Order.MessageSent.entries) {
                c.placeOrder(order)
                assertFalse(order.id.isBlank())
            }
            d += 0.1
        }
    }

    @Test
    fun testPlaceOrderShortDuration() {
        var paymentTime = timeLimits.paymentTime
        var queueTaskTime = timeLimits.queueTaskTime
        var messageTime = timeLimits.messageTime
        var employeeTime = timeLimits.employeeTime
        var queueTime = timeLimits.queueTime
        var d = 0.1
        while (d < 2) {
            paymentTime = (paymentTime * d).toLong()
            queueTaskTime = (queueTaskTime * d).toLong()
            messageTime = (messageTime * d).toLong()
            employeeTime = (employeeTime * d).toLong()
            queueTime = (queueTime * d).toLong()
            val c = buildCommanderObject(true)
            val order = Order(User("K", "J"), "pen", 1f)
            for (ms in Order.MessageSent.entries) {
                c.placeOrder(order)
                assertFalse(order.id.isBlank())
            }
            d += 0.1
        }
    }

    @Test
    fun testPlaceOrderShortDuration2() {
        var paymentTime = timeLimits.paymentTime
        var queueTaskTime = timeLimits.queueTaskTime
        var messageTime = timeLimits.messageTime
        var employeeTime = timeLimits.employeeTime
        var queueTime = timeLimits.queueTime
        var d = 0.1
        while (d < 2) {
            paymentTime = (paymentTime * d).toLong()
            queueTaskTime = (queueTaskTime * d).toLong()
            messageTime = (messageTime * d).toLong()
            employeeTime = (employeeTime * d).toLong()
            queueTime = (queueTime * d).toLong()
            val c = buildCommanderObject(false)
            val order = Order(User("K", "J"), "pen", 1f)
            for (ms in Order.MessageSent.entries) {
                c.placeOrder(order)
                assertFalse(order.id.isBlank())
            }
            d += 0.1
        }
    }

    @Test
    fun testPlaceOrderNoExceptionShortMsgDuration() {
        var paymentTime = timeLimits.paymentTime
        var queueTaskTime = timeLimits.queueTaskTime
        var messageTime = timeLimits.messageTime
        var employeeTime = timeLimits.employeeTime
        var queueTime = timeLimits.queueTime
        var d = 0.1
        while (d < 2) {
            paymentTime = (paymentTime * d).toLong()
            queueTaskTime = (queueTaskTime * d).toLong()
            messageTime = (messageTime * d).toLong()
            employeeTime = (employeeTime * d).toLong()
            queueTime = (queueTime * d).toLong()
            val c = buildCommanderObjectNoPaymentException1()
            val order = Order(User("K", "J"), "pen", 1f)
            for (ms in Order.MessageSent.entries) {
                c.placeOrder(order)
                assertFalse(order.id.isBlank())
            }
            d += 0.1
        }
    }

    @Test
    fun testPlaceOrderNoExceptionShortQueueDuration() {
        var paymentTime = timeLimits.paymentTime
        var queueTaskTime = timeLimits.queueTaskTime
        var messageTime = timeLimits.messageTime
        var employeeTime = timeLimits.employeeTime
        var queueTime = timeLimits.queueTime
        var d = 0.1
        while (d < 2) {
            paymentTime = (paymentTime * d).toLong()
            queueTaskTime = (queueTaskTime * d).toLong()
            messageTime = (messageTime * d).toLong()
            employeeTime = (employeeTime * d).toLong()
            queueTime = (queueTime * d).toLong()
            val c = buildCommanderObjectUnknownException()
            val order = Order(User("K", "J"), "pen", 1f)
            for (ms in Order.MessageSent.entries) {
                c.placeOrder(order)
                assertFalse(order.id.isBlank())
            }
            d += 0.1
        }
    }

    @Test
    fun testPlaceOrderWithDatabase() {
        var paymentTime = timeLimits.paymentTime
        var queueTaskTime = timeLimits.queueTaskTime
        var messageTime = timeLimits.messageTime
        var employeeTime = timeLimits.employeeTime
        var queueTime = timeLimits.queueTime
        var d = 0.1
        while (d < 2) {
            paymentTime = (paymentTime * d).toLong()
            queueTaskTime = (queueTaskTime * d).toLong()
            messageTime = (messageTime * d).toLong()
            employeeTime = (employeeTime * d).toLong()
            queueTime = (queueTime * d).toLong()
            val c = buildCommanderObjectWithDB()
            val order = Order(User("K", null), "pen", 1f)
            for (ms in Order.MessageSent.entries) {
                c.placeOrder(order)
                assertFalse(order.id.isBlank())
            }
            d += 0.1
        }
    }

    @Test
    fun testPlaceOrderWithDatabaseAndExceptions() {
        var paymentTime = timeLimits.paymentTime
        var queueTaskTime = timeLimits.queueTaskTime
        var messageTime = timeLimits.messageTime
        var employeeTime = timeLimits.employeeTime
        var queueTime = timeLimits.queueTime
        var d = 0.1
        while (d < 2) {
            paymentTime = (paymentTime * d).toLong()
            queueTaskTime = (queueTaskTime * d).toLong()
            messageTime = (messageTime * d).toLong()
            employeeTime = (employeeTime * d).toLong()
            queueTime = (queueTime * d).toLong()

            for (e in exceptionList) {
                var c = buildCommanderObjectWithDB(true, true, e)
                var order = Order(User("K", null), "pen", 1f)
                for (ms in Order.MessageSent.entries) {
                    c.placeOrder(order)
                    assertFalse(order.id.isBlank())
                }

                c = buildCommanderObjectWithDB(true, false, e)
                order = Order(User("K", null), "pen", 1f)
                for (ms in Order.MessageSent.entries) {
                    c.placeOrder(order)
                    assertFalse(order.id.isBlank())
                }

                c = buildCommanderObjectWithDB(false, false, e)
                order = Order(User("K", null), "pen", 1f)
                for (ms in Order.MessageSent.entries) {
                    c.placeOrder(order)
                    assertFalse(order.id.isBlank())
                }

                c = buildCommanderObjectWithDB(false, true, e)
                order = Order(User("K", null), "pen", 1f)
                for (ms in Order.MessageSent.entries) {
                    c.placeOrder(order)
                    assertFalse(order.id.isBlank())
                }
            }
            d += 0.1
        }
    }

    @Test
    fun testPlaceOrderWithoutDatabase() {
        var paymentTime = timeLimits.paymentTime
        var queueTaskTime = timeLimits.queueTaskTime
        var messageTime = timeLimits.messageTime
        var employeeTime = timeLimits.employeeTime
        var queueTime = timeLimits.queueTime
        var d = 0.1
        while (d < 2) {
            paymentTime = (paymentTime * d).toLong()
            queueTaskTime = (queueTaskTime * d).toLong()
            messageTime = (messageTime * d).toLong()
            employeeTime = (employeeTime * d).toLong()
            queueTime = (queueTime * d).toLong()
            val c = buildCommanderObjectWithoutDB()
            val order = Order(User("K", null), "pen", 1f)
            for (ms in Order.MessageSent.entries) {
                c.placeOrder(order)
                assertFalse(order.id.isBlank())
            }
            d += 0.1
        }
    }

    @Test
    fun testPlaceOrderWithoutDatabaseAndExceptions() {
        var paymentTime = timeLimits.paymentTime
        var queueTaskTime = timeLimits.queueTaskTime
        var messageTime = timeLimits.messageTime
        var employeeTime = timeLimits.employeeTime
        var queueTime = timeLimits.queueTime
        var d = 0.1
        while (d < 2) {
            paymentTime = (paymentTime * d).toLong()
            queueTaskTime = (queueTaskTime * d).toLong()
            messageTime = (messageTime * d).toLong()
            employeeTime = (employeeTime * d).toLong()
            queueTime = (queueTime * d).toLong()

            for (e in exceptionList) {
                var c = buildCommanderObjectWithoutDB(true, true, e)
                var order = Order(User("K", null), "pen", 1f)
                for (ms in Order.MessageSent.entries) {
                    c.placeOrder(order)
                    assertFalse(order.id.isBlank())
                }

                c = buildCommanderObjectWithoutDB(true, false, e)
                order = Order(User("K", null), "pen", 1f)
                for (ms in Order.MessageSent.entries) {
                    c.placeOrder(order)
                    assertFalse(order.id.isBlank())
                }

                c = buildCommanderObjectWithoutDB(false, false, e)
                order = Order(User("K", null), "pen", 1f)
                for (ms in Order.MessageSent.entries) {
                    c.placeOrder(order)
                    assertFalse(order.id.isBlank())
                }

                c = buildCommanderObjectWithoutDB(false, true, e)
                order = Order(User("K", null), "pen", 1f)
                for (ms in Order.MessageSent.entries) {
                    c.placeOrder(order)
                    assertFalse(order.id.isBlank())
                }
            }
            d += 0.1
        }
    }

    @Test
    fun testAllSuccessCases() {
        appAllCases.employeeDbSuccessCase()
        appAllCases.messagingSuccessCase()
        appAllCases.paymentSuccessCase()
        appAllCases.queueSuccessCase()
        appAllCases.shippingSuccessCase()
    }

    @Test
    fun testAllUnavailableCase() {
        appAllCases.employeeDatabaseUnavailableCase()
        appAllCases.messagingDatabaseUnavailableCasePaymentSuccess()
        appAllCases.messagingDatabaseUnavailableCasePaymentError()
        appAllCases.messagingDatabaseUnavailableCasePaymentFailure()
        appAllCases.paymentDatabaseUnavailableCase()
        appAllCases.queuePaymentTaskDatabaseUnavailableCase()
        appAllCases.queueMessageTaskDatabaseUnavailableCase()
        appAllCases.queueEmployeeDbTaskDatabaseUnavailableCase()
        appAllCases.itemUnavailableCase()
        appAllCases.shippingDatabaseUnavailableCase()
    }

    @Test
    fun testAllNotPossibleCase() {
        appAllCases.paymentNotPossibleCase()
        appAllCases.shippingItemNotPossibleCase()
    }
}