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
package com.iluwatar.mute

// ABOUTME: Tests for the Mute utility object covering both mute() and loggedMute() behaviors.
// ABOUTME: Verifies silent execution, AssertionError wrapping, and stderr logging of exceptions.

import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

private val logger = KotlinLogging.logger {}

/** Test for the mute-idiom pattern */
class MuteTest {

    companion object {
        private const val MESSAGE = "should not occur"
    }

    @Test
    fun muteShouldRunTheCheckedRunnableAndNotThrowAnyExceptionIfCheckedRunnableDoesNotThrowAnyException() {
        assertDoesNotThrow { Mute.mute { methodNotThrowingAnyException() } }
    }

    @Test
    fun muteShouldRethrowUnexpectedExceptionAsAssertionError() {
        assertThrows(AssertionError::class.java) { Mute.mute { methodThrowingException() } }
    }

    @Test
    fun loggedMuteShouldRunTheCheckedRunnableAndNotThrowAnyExceptionIfCheckedRunnableDoesNotThrowAnyException() {
        assertDoesNotThrow { Mute.loggedMute { methodNotThrowingAnyException() } }
    }

    @Test
    fun loggedMuteShouldLogExceptionTraceBeforeSwallowingIt() {
        val stream = ByteArrayOutputStream()
        System.setErr(PrintStream(stream))

        Mute.loggedMute { methodThrowingException() }

        assertTrue(stream.toString().contains(MESSAGE))
    }

    private fun methodNotThrowingAnyException() {
        logger.info { "Executed successfully" }
    }

    @Throws(Exception::class)
    private fun methodThrowingException() {
        throw Exception(MESSAGE)
    }
}
