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

// ABOUTME: Integration tests for all special case scenarios in the pattern.
// ABOUTME: Tests maintenance mode, invalid user, out of stock, insufficient funds, and successful receipt.
package com.iluwatar.specialcase

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

/**
 * Special cases unit tests. (including the successful scenario [ReceiptDto])
 */
class SpecialCasesTest {

    companion object {
        private lateinit var applicationServices: ApplicationServices
        private lateinit var receipt: ReceiptViewModel

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            Db.getInstance().seedUser("ignite1771", 1000.0)
            Db.getInstance().seedItem("computer", 800.0)
            Db.getInstance().seedItem("car", 20000.0)

            applicationServices = ApplicationServicesImpl()
        }
    }

    @BeforeEach
    fun beforeEach() {
        MaintenanceLock.getInstance().setLock(false)
    }

    @Test
    fun testDownForMaintenance() {
        val loggerInstance = LoggerFactory.getLogger(DownForMaintenance::class.java) as Logger

        val listAppender = ListAppender<ILoggingEvent>()
        listAppender.start()
        loggerInstance.addAppender(listAppender)

        MaintenanceLock.getInstance().setLock(true)
        val receipt = applicationServices.loggedInUserPurchase(null, null)
        receipt.show()

        val loggingEventList = listAppender.list
        assertEquals("Down for maintenance", loggingEventList[0].message)
        assertEquals(Level.INFO, loggingEventList[0].level)
    }

    @Test
    fun testInvalidUser() {
        val loggerInstance = LoggerFactory.getLogger(InvalidUser::class.java) as Logger

        val listAppender = ListAppender<ILoggingEvent>()
        listAppender.start()
        loggerInstance.addAppender(listAppender)

        val receipt = applicationServices.loggedInUserPurchase("a", null)
        receipt.show()

        val loggingEventList = listAppender.list
        assertEquals("Invalid user: a", loggingEventList[0].message)
        assertEquals(Level.INFO, loggingEventList[0].level)
    }

    @Test
    fun testOutOfStock() {
        val loggerInstance = LoggerFactory.getLogger(OutOfStock::class.java) as Logger

        val listAppender = ListAppender<ILoggingEvent>()
        listAppender.start()
        loggerInstance.addAppender(listAppender)

        val receipt = applicationServices.loggedInUserPurchase("ignite1771", "tv")
        receipt.show()

        val loggingEventList = listAppender.list
        assertEquals("Out of stock: tv for user = ignite1771 to buy", loggingEventList[0].message)
        assertEquals(Level.INFO, loggingEventList[0].level)
    }

    @Test
    fun testInsufficientFunds() {
        val loggerInstance = LoggerFactory.getLogger(InsufficientFunds::class.java) as Logger

        val listAppender = ListAppender<ILoggingEvent>()
        listAppender.start()
        loggerInstance.addAppender(listAppender)

        val receipt = applicationServices.loggedInUserPurchase("ignite1771", "car")
        receipt.show()

        val loggingEventList = listAppender.list
        assertEquals(
            "Insufficient funds: 1000.0 of user: ignite1771 for buying item: car",
            loggingEventList[0].message
        )
        assertEquals(Level.INFO, loggingEventList[0].level)
    }

    @Test
    fun testReceiptDto() {
        val loggerInstance = LoggerFactory.getLogger(ReceiptDto::class.java) as Logger

        val listAppender = ListAppender<ILoggingEvent>()
        listAppender.start()
        loggerInstance.addAppender(listAppender)

        val receipt = applicationServices.loggedInUserPurchase("ignite1771", "computer")
        receipt.show()

        val loggingEventList = listAppender.list
        assertEquals("Receipt: 800.0 paid", loggingEventList[0].message)
        assertEquals(Level.INFO, loggingEventList[0].level)
    }
}
