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
package com.iluwatar.money

// ABOUTME: Unit tests for the Money class covering all arithmetic and currency operations.
// ABOUTME: Validates correct behavior for addition, subtraction, multiplication, conversion, and error cases.

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class MoneyTest {

    @Test
    fun testConstructor() {
        // Test the constructor
        val money = Money(100.00, "USD")
        assertEquals(100.00, money.amount)
        assertEquals("USD", money.currency)
    }

    @Test
    fun testAddMoney_SameCurrency() {
        // Test adding two Money objects with the same currency
        val money1 = Money(100.00, "USD")
        val money2 = Money(50.25, "USD")

        money1.addMoney(money2)

        assertEquals(150.25, money1.amount, "Amount after addition should be 150.25")
    }

    @Test
    fun testAddMoney_DifferentCurrency() {
        // Test adding two Money objects with different currencies
        val money1 = Money(100.00, "USD")
        val money2 = Money(50.25, "EUR")

        assertThrows(CannotAddTwoCurrenciesException::class.java) { money1.addMoney(money2) }
    }

    @Test
    fun testSubtractMoney_SameCurrency() {
        // Test subtracting two Money objects with the same currency
        val money1 = Money(100.00, "USD")
        val money2 = Money(50.25, "USD")

        money1.subtractMoney(money2)

        assertEquals(49.75, money1.amount, "Amount after subtraction should be 49.75")
    }

    @Test
    fun testSubtractMoney_DifferentCurrency() {
        // Test subtracting two Money objects with different currencies
        val money1 = Money(100.00, "USD")
        val money2 = Money(50.25, "EUR")

        assertThrows(CannotSubtractException::class.java) { money1.subtractMoney(money2) }
    }

    @Test
    fun testSubtractMoney_AmountTooLarge() {
        // Test subtracting an amount larger than the current amount
        val money1 = Money(50.00, "USD")
        val money2 = Money(60.00, "USD")

        assertThrows(CannotSubtractException::class.java) { money1.subtractMoney(money2) }
    }

    @Test
    fun testMultiply() {
        // Test multiplying the money amount by a factor
        val money = Money(100.00, "USD")

        money.multiply(3)

        assertEquals(300.00, money.amount, "Amount after multiplication should be 300.00")
    }

    @Test
    fun testMultiply_NegativeFactor() {
        // Test multiplying by a negative factor
        val money = Money(100.00, "USD")

        assertThrows(IllegalArgumentException::class.java) { money.multiply(-2) }
    }

    @Test
    fun testExchangeCurrency() {
        // Test converting currency using an exchange rate
        val money = Money(100.00, "USD")

        money.exchangeCurrency("EUR", 0.85)

        assertEquals("EUR", money.currency, "Currency after conversion should be EUR")
        assertEquals(85.00, money.amount, "Amount after conversion should be 85.00")
    }

    @Test
    fun testExchangeCurrency_NegativeExchangeRate() {
        // Test converting currency with a negative exchange rate
        val money = Money(100.00, "USD")

        assertThrows(IllegalArgumentException::class.java) { money.exchangeCurrency("EUR", -0.85) }
    }

    @Test
    fun testAppExecution() {
        assertDoesNotThrow { main() }
    }
}
