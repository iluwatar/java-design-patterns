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

// ABOUTME: Test class for Retry pattern implementation.
// ABOUTME: Tests retry behavior with different exception types and conditions.
package com.iluwatar.commander

import com.iluwatar.commander.exceptions.DatabaseUnavailableException
import com.iluwatar.commander.exceptions.ItemUnavailableException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

private val logger = KotlinLogging.logger {}

class RetryTest {
    @Test
    fun performTest() {
        val op =
            Retry.Operation { l ->
                if (l.isNotEmpty()) {
                    throw l.removeAt(0)
                }
            }
        val handleError = Retry.HandleErrorIssue<Order> { _, _ -> }
        val r1 =
            Retry(
                op,
                handleError,
                3,
                30000,
                { e -> DatabaseUnavailableException::class.java.isAssignableFrom(e.javaClass) },
            )
        val r2 =
            Retry(
                op,
                handleError,
                3,
                30000,
                { e -> DatabaseUnavailableException::class.java.isAssignableFrom(e.javaClass) },
            )
        val user = User("Jim", "ABCD")
        val order = Order(user, "book", 10f)
        val arr1 =
            mutableListOf<Exception>(
                ItemUnavailableException(),
                DatabaseUnavailableException(),
            )
        try {
            r1.perform(arr1, order)
        } catch (e1: Exception) {
            logger.error(e1) { "An exception occurred" }
        }
        val arr2 =
            mutableListOf<Exception>(
                DatabaseUnavailableException(),
                ItemUnavailableException(),
            )
        try {
            r2.perform(arr2, order)
        } catch (e1: Exception) {
            logger.error(e1) { "An exception occurred" }
        }
        // r1 stops at ItemUnavailableException, r2 retries because it encounters
        // DatabaseUnavailableException
        assertTrue(arr1.size == 1 && arr2.isEmpty())
    }
}