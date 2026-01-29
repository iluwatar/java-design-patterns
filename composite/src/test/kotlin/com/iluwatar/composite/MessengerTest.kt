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
package com.iluwatar.composite

// ABOUTME: Tests for the Messenger class verifying correct composite message construction.
// ABOUTME: Captures System.out to assert that printed composite messages match expected strings.

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

/** MessengerTest */
class MessengerTest {
    /** The buffer used to capture every write to [System.out] */
    private var stdOutBuffer = ByteArrayOutputStream()

    /** Keep the original std-out so it can be restored after the test */
    private val realStdOut: PrintStream = System.out

    /**
     * Inject the mocked std-out [PrintStream] into the [System] class before each test
     */
    @BeforeEach
    fun setUp() {
        stdOutBuffer = ByteArrayOutputStream()
        System.setOut(PrintStream(stdOutBuffer))
    }

    /** Removed the mocked std-out [PrintStream] again from the [System] class */
    @AfterEach
    fun tearDown() {
        System.setOut(realStdOut)
    }

    /** Test the message from the orcs */
    @Test
    fun testMessageFromOrcs() {
        val messenger = Messenger()
        testMessage(messenger.messageFromOrcs(), "Where there is a whip there is a way.")
    }

    /** Test the message from the elves */
    @Test
    fun testMessageFromElves() {
        val messenger = Messenger()
        testMessage(messenger.messageFromElves(), "Much wind pours from your mouth.")
    }

    /**
     * Test if the given composed message matches the expected message
     *
     * @param composedMessage The composed message, received from the messenger
     * @param message The expected message
     */
    private fun testMessage(
        composedMessage: LetterComposite,
        message: String,
    ) {
        // Test is the composed message has the correct number of words
        val words = message.split(" ")
        assertNotNull(composedMessage)
        assertEquals(words.size, composedMessage.count())

        // Print the message to the mocked stdOut ...
        composedMessage.print()

        // ... and verify if the message matches with the expected one
        assertEquals(message, stdOutBuffer.toByteArray().toString(Charsets.UTF_8).trim())
    }
}