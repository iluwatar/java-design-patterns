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

// ABOUTME: Represents a monetary value with an associated currency and arithmetic operations.
// ABOUTME: Provides addition, subtraction, multiplication, and currency conversion with rounding.

import kotlin.math.roundToLong

/**
 * Represents a monetary value with an associated currency. Provides operations for basic arithmetic
 * (addition, subtraction, multiplication), as well as currency conversion while ensuring proper
 * rounding.
 */
class Money(var amount: Double, var currency: String) {

    /**
     * Rounds the given value to two decimal places.
     *
     * @param value the value to round.
     * @return the rounded value, up to two decimal places.
     */
    private fun roundToTwoDecimals(value: Double): Double =
        (value * 100.0).roundToLong() / 100.0

    /**
     * Adds another Money object to the current instance.
     *
     * @param moneyToBeAdded the Money object to add.
     * @throws CannotAddTwoCurrenciesException if the currencies do not match.
     */
    @Throws(CannotAddTwoCurrenciesException::class)
    fun addMoney(moneyToBeAdded: Money) {
        if (moneyToBeAdded.currency != currency) {
            throw CannotAddTwoCurrenciesException("You are trying to add two different currencies")
        }
        amount = roundToTwoDecimals(amount + moneyToBeAdded.amount)
    }

    /**
     * Subtracts another Money object from the current instance.
     *
     * @param moneyToBeSubtracted the Money object to subtract.
     * @throws CannotSubtractException if the currencies do not match or if the amount to subtract is
     *     larger than the current amount.
     */
    @Throws(CannotSubtractException::class)
    fun subtractMoney(moneyToBeSubtracted: Money) {
        if (moneyToBeSubtracted.currency != currency) {
            throw CannotSubtractException("You are trying to subtract two different currencies")
        } else if (moneyToBeSubtracted.amount > amount) {
            throw CannotSubtractException(
                "The amount you are trying to subtract is larger than the amount you have"
            )
        }
        amount = roundToTwoDecimals(amount - moneyToBeSubtracted.amount)
    }

    /**
     * Multiplies the current amount of money by a factor.
     *
     * @param factor the factor to multiply by.
     * @throws IllegalArgumentException if the factor is negative.
     */
    fun multiply(factor: Int) {
        require(factor >= 0) { "Factor must be non-negative" }
        amount = roundToTwoDecimals(amount * factor)
    }

    /**
     * Converts the current amount of money to another currency using the provided exchange rate.
     *
     * @param currencyToChangeTo the new currency to convert to.
     * @param exchangeRate the exchange rate to convert from the current currency to the new currency.
     * @throws IllegalArgumentException if the exchange rate is negative.
     */
    fun exchangeCurrency(currencyToChangeTo: String, exchangeRate: Double) {
        require(exchangeRate >= 0) { "Exchange rate must be non-negative" }
        amount = roundToTwoDecimals(amount * exchangeRate)
        currency = currencyToChangeTo
    }
}
