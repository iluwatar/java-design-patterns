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

// ABOUTME: Business operation that finds a customer and returns their ID.
// ABOUTME: Simulates flaky operations by throwing programmed exceptions before returning results.
package com.iluwatar.retry

import java.util.ArrayDeque
import java.util.Deque

/**
 * Finds a customer, returning its ID from our records.
 *
 * This is an imaginary operation that, for some imagined input, returns the ID for a customer.
 * However, this is a "flaky" operation that is supposed to fail intermittently, but for the
 * purposes of this example it fails in a programmed way depending on the constructor parameters.
 *
 * @param customerId the customer ID to return
 * @param errors the queue of errors to throw before returning the result
 */
data class FindCustomer(
    val customerId: String,
    val errors: Deque<BusinessException> = ArrayDeque()
) : BusinessOperation<String> {

    constructor(customerId: String, vararg errors: BusinessException) : this(
        customerId,
        ArrayDeque(errors.toList())
    )

    @Throws(BusinessException::class)
    override fun perform(): String {
        if (errors.isNotEmpty()) {
            throw errors.pop()
        }
        return customerId
    }
}
