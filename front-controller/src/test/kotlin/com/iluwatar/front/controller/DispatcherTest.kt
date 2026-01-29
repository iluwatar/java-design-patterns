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

// ABOUTME: Unit tests for Dispatcher class functionality.
// ABOUTME: Tests command dispatching, retrieval, and class resolution logic.
package com.iluwatar.front.controller

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DispatcherTest {

    private lateinit var dispatcher: Dispatcher

    @BeforeEach
    fun setUp() {
        dispatcher = Dispatcher()
    }

    @Test
    fun testDispatchKnownCommand() {
        val mockCommand = mockk<ArcherCommand>(relaxed = true)
        val spyDispatcher = spyk(dispatcher)
        every { spyDispatcher.getCommand("Archer") } returns mockCommand

        spyDispatcher.dispatch("Archer")

        verify(exactly = 1) { mockCommand.process() }
    }

    @Test
    fun testDispatchUnknownCommand() {
        val mockCommand = mockk<UnknownCommand>(relaxed = true)
        val spyDispatcher = spyk(dispatcher)
        every { spyDispatcher.getCommand("Unknown") } returns mockCommand

        spyDispatcher.dispatch("Unknown")

        verify(exactly = 1) { mockCommand.process() }
    }

    @Test
    fun testGetCommandKnown() {
        val command = dispatcher.getCommand("Archer")
        assertNotNull(command)
        assertTrue(command is ArcherCommand)
    }

    @Test
    fun testGetCommandUnknown() {
        val command = dispatcher.getCommand("Unknown")
        assertNotNull(command)
        assertTrue(command is UnknownCommand)
    }

    @Test
    fun testGetCommandClassKnown() {
        val commandClass = Dispatcher.getCommandClass("Archer")
        assertNotNull(commandClass)
        assertEquals(ArcherCommand::class.java, commandClass)
    }

    @Test
    fun testGetCommandClassUnknown() {
        val commandClass = Dispatcher.getCommandClass("Unknown")
        assertNotNull(commandClass)
        assertEquals(UnknownCommand::class.java, commandClass)
    }
}
