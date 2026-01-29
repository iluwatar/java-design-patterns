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

// ABOUTME: Entry point demonstrating the Money class operations including arithmetic and conversion.
// ABOUTME: Showcases addition, subtraction, multiplication, and currency exchange with error handling.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * The `App` demonstrates the functionality of the [Money] class, which encapsulates
 * monetary values and their associated currencies. It showcases operations like addition,
 * subtraction, multiplication, and currency conversion, while ensuring validation and immutability.
 *
 * Through this example, the handling of invalid operations (e.g., mismatched currencies or
 * invalid inputs) is demonstrated using custom exceptions. Logging is used for transparency.
 *
 * This highlights the practical application of object-oriented principles such as encapsulation
 * and validation in a financial context.
 */
fun main() {
    // Create instances of Money
    val usdAmount1 = Money(50.00, "USD")
    val usdAmount2 = Money(20.00, "USD")

    // Demonstrate addition
    try {
        usdAmount1.addMoney(usdAmount2)
        logger.info { "Sum in USD: ${usdAmount1.amount}" }
    } catch (e: CannotAddTwoCurrenciesException) {
        logger.error { "Error adding money: ${e.message}" }
    }

    // Demonstrate subtraction
    try {
        usdAmount1.subtractMoney(usdAmount2)
        logger.info { "Difference in USD: ${usdAmount1.amount}" }
    } catch (e: CannotSubtractException) {
        logger.error { "Error subtracting money: ${e.message}" }
    }

    // Demonstrate multiplication
    try {
        usdAmount1.multiply(2)
        logger.info { "Multiplied Amount in USD: ${usdAmount1.amount}" }
    } catch (e: IllegalArgumentException) {
        logger.error { "Error multiplying money: ${e.message}" }
    }

    // Demonstrate currency conversion
    try {
        val exchangeRateUsdToEur = 0.85 // Example exchange rate
        usdAmount1.exchangeCurrency("EUR", exchangeRateUsdToEur)
        logger.info { "USD converted to EUR: ${usdAmount1.amount} ${usdAmount1.currency}" }
    } catch (e: IllegalArgumentException) {
        logger.error { "Error converting currency: ${e.message}" }
    }
}
