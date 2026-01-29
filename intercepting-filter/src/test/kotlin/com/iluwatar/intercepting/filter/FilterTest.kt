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

// ABOUTME: Parameterized tests for all concrete Filter implementations.
// ABOUTME: Tests filter execution and chain linking for various order configurations.
package com.iluwatar.intercepting.filter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

/** FilterTest */
class FilterTest {

    companion object {
        private val PERFECT_ORDER = Order("name", "12345678901", "addr", "dep", "order")
        private val WRONG_ORDER = Order("name", "12345678901", "addr", "dep", "")
        private val WRONG_DEPOSIT = Order("name", "12345678901", "addr", "", "order")
        private val WRONG_ADDRESS = Order("name", "12345678901", "", "dep", "order")
        private val WRONG_CONTACT = Order("name", "", "addr", "dep", "order")
        private val WRONG_NAME = Order("", "12345678901", "addr", "dep", "order")

        @JvmStatic
        fun getTestData(): List<Array<Any>> = listOf(
            arrayOf(NameFilter(), PERFECT_ORDER, ""),
            arrayOf(NameFilter(), WRONG_NAME, "Invalid name!"),
            arrayOf(NameFilter(), WRONG_CONTACT, ""),
            arrayOf(NameFilter(), WRONG_ADDRESS, ""),
            arrayOf(NameFilter(), WRONG_DEPOSIT, ""),
            arrayOf(NameFilter(), WRONG_ORDER, ""),
            arrayOf(ContactFilter(), PERFECT_ORDER, ""),
            arrayOf(ContactFilter(), WRONG_NAME, ""),
            arrayOf(ContactFilter(), WRONG_CONTACT, "Invalid contact number!"),
            arrayOf(ContactFilter(), WRONG_ADDRESS, ""),
            arrayOf(ContactFilter(), WRONG_DEPOSIT, ""),
            arrayOf(ContactFilter(), WRONG_ORDER, ""),
            arrayOf(AddressFilter(), PERFECT_ORDER, ""),
            arrayOf(AddressFilter(), WRONG_NAME, ""),
            arrayOf(AddressFilter(), WRONG_CONTACT, ""),
            arrayOf(AddressFilter(), WRONG_ADDRESS, "Invalid address!"),
            arrayOf(AddressFilter(), WRONG_DEPOSIT, ""),
            arrayOf(AddressFilter(), WRONG_ORDER, ""),
            arrayOf(DepositFilter(), PERFECT_ORDER, ""),
            arrayOf(DepositFilter(), WRONG_NAME, ""),
            arrayOf(DepositFilter(), WRONG_CONTACT, ""),
            arrayOf(DepositFilter(), WRONG_ADDRESS, ""),
            arrayOf(DepositFilter(), WRONG_DEPOSIT, "Invalid deposit number!"),
            arrayOf(DepositFilter(), WRONG_ORDER, ""),
            arrayOf(OrderFilter(), PERFECT_ORDER, ""),
            arrayOf(OrderFilter(), WRONG_NAME, ""),
            arrayOf(OrderFilter(), WRONG_CONTACT, ""),
            arrayOf(OrderFilter(), WRONG_ADDRESS, ""),
            arrayOf(OrderFilter(), WRONG_DEPOSIT, ""),
            arrayOf(OrderFilter(), WRONG_ORDER, "Invalid order!")
        )
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    fun testExecute(filter: Filter, order: Order, expectedResult: String) {
        val result = filter.execute(order)
        assertNotNull(result)
        assertEquals(expectedResult, result.trim())
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    fun testNext(filter: Filter, @Suppress("UNUSED_PARAMETER") order: Order, @Suppress("UNUSED_PARAMETER") expectedResult: String) {
        assertNull(filter.getNext())
        assertSame(filter, filter.getLast())
    }
}
