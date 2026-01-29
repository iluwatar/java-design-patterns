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

// ABOUTME: Unit tests for the Order data class.
// ABOUTME: Tests getter and setter functionality for all order fields.
package com.iluwatar.intercepting.filter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/** OrderTest */
class OrderTest {

    companion object {
        private const val EXPECTED_VALUE = "test"
    }

    @Test
    fun testSetName() {
        val order = Order()
        order.name = EXPECTED_VALUE
        assertEquals(EXPECTED_VALUE, order.name)
    }

    @Test
    fun testSetContactNumber() {
        val order = Order()
        order.contactNumber = EXPECTED_VALUE
        assertEquals(EXPECTED_VALUE, order.contactNumber)
    }

    @Test
    fun testSetAddress() {
        val order = Order()
        order.address = EXPECTED_VALUE
        assertEquals(EXPECTED_VALUE, order.address)
    }

    @Test
    fun testSetDepositNumber() {
        val order = Order()
        order.depositNumber = EXPECTED_VALUE
        assertEquals(EXPECTED_VALUE, order.depositNumber)
    }

    @Test
    fun testSetOrder() {
        val order = Order()
        order.orderItem = EXPECTED_VALUE
        assertEquals(EXPECTED_VALUE, order.orderItem)
    }
}
