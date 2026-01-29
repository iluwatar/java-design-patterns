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

// ABOUTME: Main application demonstrating the Retry pattern for handling recoverable failures.
// ABOUTME: Shows examples of retry with fixed delay and exponential backoff strategies.
package com.iluwatar.retry

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

const val NOT_FOUND = "not found"

private var op: BusinessOperation<String>? = null

/**
 * The Retry pattern enables applications to handle potentially recoverable failures from
 * the environment if the business requirements and nature of the failures allow it. By retrying
 * failed operations on external dependencies, the application may maintain stability and minimize
 * negative impact on the user experience.
 *
 * In our example, we have the [BusinessOperation] interface as an abstraction over all
 * operations that our application performs involving remote systems. The calling code should remain
 * decoupled from implementations.
 *
 * [FindCustomer] is a business operation that looks up a customer's record and returns its
 * ID. Imagine its job is performed by looking up the customer in our local database and returning
 * its ID. We can pass [CustomerNotFoundException] as one of its constructor parameters in order
 * to simulate not finding the customer.
 *
 * Imagine that, lately, this operation has experienced intermittent failures due to some weird
 * corruption and/or locking in the data. After retrying a few times the customer is found. The
 * database is still, however, expected to always be available. While a definitive solution is found
 * to the problem, our engineers advise us to retry the operation a set number of times with a set
 * delay between retries, although not too many retries otherwise the end user will be left waiting
 * for a long time, while delays that are too short will not allow the database to recover from the
 * load.
 *
 * To keep the calling code as decoupled as possible from this workaround, we have implemented
 * the retry mechanism as a [BusinessOperation] named [Retry].
 *
 * @see [Retry pattern (Microsoft Azure Docs)](https://docs.microsoft.com/en-us/azure/architecture/patterns/retry)
 */
fun main() {
    noErrors()
    errorNoRetry()
    errorWithRetry()
    errorWithRetryExponentialBackoff()
}

private fun noErrors() {
    op = FindCustomer("123")
    op?.perform()
    logger.info { "Sometimes the operation executes with no errors." }
}

private fun errorNoRetry() {
    op = FindCustomer("123", CustomerNotFoundException(NOT_FOUND))
    try {
        op?.perform()
    } catch (e: CustomerNotFoundException) {
        logger.info { "Yet the operation will throw an error every once in a while." }
    }
}

private fun errorWithRetry() {
    val retry = Retry(
        FindCustomer("123", CustomerNotFoundException(NOT_FOUND)),
        3, // 3 attempts
        100, // 100 ms delay between attempts
        { e -> CustomerNotFoundException::class.java.isAssignableFrom(e.javaClass) }
    )
    op = retry
    val customerId = op?.perform()
    logger.info {
        "However, retrying the operation while ignoring a recoverable error will eventually yield " +
            "the result $customerId after a number of attempts ${retry.attempts()}"
    }
}

private fun errorWithRetryExponentialBackoff() {
    val retry = RetryExponentialBackoff(
        FindCustomer("123", CustomerNotFoundException(NOT_FOUND)),
        6, // 6 attempts
        30000, // 30 s max delay between attempts
        { e -> CustomerNotFoundException::class.java.isAssignableFrom(e.javaClass) }
    )
    op = retry
    val customerId = op?.perform()
    logger.info {
        "However, retrying the operation while ignoring a recoverable error will eventually yield " +
            "the result $customerId after a number of attempts ${retry.attempts()}"
    }
}
