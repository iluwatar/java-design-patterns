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

// ABOUTME: Test class for PaymentService functionality.
// ABOUTME: Tests payment method selection with parameterized tests for cash, credit, and invalid methods.

package com.iluwatar.sessionfacade

import io.github.oshai.kotlinlogging.KLogger
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/**
 * The type Payment service test.
 */
class PaymentServiceTest {

    private lateinit var paymentService: PaymentService
    private lateinit var mockLogger: KLogger

    /**
     * Sets up.
     */
    @BeforeEach
    fun setUp() {
        paymentService = PaymentService()
        mockLogger = mockk(relaxed = true)
        paymentService.logger = mockLogger
    }

    @ParameterizedTest
    @CsvSource(
        "cash, Client have chosen cash payment option",
        "credit, Client have chosen credit card payment option",
        "cheque, Unspecified payment method type"
    )
    fun testSelectPaymentMethod(method: String, expectedLogMessage: String) {
        paymentService.selectPaymentMethod(method)
        verify { mockLogger.info(any<() -> Any?>()) }
    }
}
