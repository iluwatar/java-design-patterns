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
// ABOUTME: Unit tests for the CakeBakingException class.
// ABOUTME: Tests both default and message-based constructor behavior.
package com.iluwatar.layers.exception

import exception.CakeBakingException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

/**
 * Tests for the [CakeBakingException] class. This class contains unit tests to verify the
 * correct functionality of the `CakeBakingException` class constructors, including the
 * default constructor and the constructor that accepts a message parameter.
 */
class CakeBakingExceptionTest {
    /**
     * Tests the default constructor of [CakeBakingException]. Ensures that an exception created
     * with the default constructor has `null` as its message and cause.
     */
    @Test
    fun testConstructor() {
        val exception = CakeBakingException()
        assertNull(exception.message, "The message should be null for the default constructor.")
        assertNull(exception.cause, "The cause should be null for the default constructor.")
    }

    /**
     * Tests the constructor of [CakeBakingException] that accepts a message. Ensures that an
     * exception created with this constructor correctly stores the provided message and has `null`
     * as its cause.
     */
    @Test
    fun testConstructorWithMessage() {
        val expectedMessage = "message"
        val exception = CakeBakingException(expectedMessage)
        assertEquals(
            expectedMessage,
            exception.message,
            "The stored message should match the expected message.",
        )
        assertNull(
            exception.cause,
            "The cause should be null when an exception is created with only a message.",
        )
    }
}
