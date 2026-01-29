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
package com.iluwatar.nullobject

// ABOUTME: Tests for the binary tree structure using NodeImpl and NullNode objects.
// ABOUTME: Verifies tree size, walk traversal logging, and left/right child navigation.

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

/** TreeTest */
class TreeTest {

    private lateinit var appender: InMemoryAppender

    @BeforeEach
    fun setUp() {
        appender = InMemoryAppender()
    }

    @AfterEach
    fun tearDown() {
        appender.stop()
    }

    /**
     * During the tests, the same tree structure will be used, shown below. End points will be
     * terminated with the [NullNode] instance.
     *
     * ```
     * root
     * +-- level1_a
     * |   +-- level2_a
     * |   |   +-- level3_a
     * |   |   +-- level3_b
     * |   +-- level2_b
     * +-- level1_b
     * ```
     */
    companion object {
        private val TREE_ROOT: Node

        init {
            val level1B = NodeImpl("level1_b", NullNode, NullNode)
            val level2B = NodeImpl("level2_b", NullNode, NullNode)
            val level3A = NodeImpl("level3_a", NullNode, NullNode)
            val level3B = NodeImpl("level3_b", NullNode, NullNode)
            val level2A = NodeImpl("level2_a", level3A, level3B)
            val level1A = NodeImpl("level1_a", level2A, level2B)
            TREE_ROOT = NodeImpl("root", level1A, level1B)
        }
    }

    /**
     * Verify the number of items in the tree. The root has 6 children so we expect a
     * [Node.treeSize] of 7 [Node]s in total.
     */
    @Test
    fun testTreeSize() {
        assertEquals(7, TREE_ROOT.treeSize)
    }

    /** Walk through the tree and verify if every item is handled. */
    @Test
    fun testWalk() {
        TREE_ROOT.walk()

        assertTrue(appender.logContains("root"))
        assertTrue(appender.logContains("level1_a"))
        assertTrue(appender.logContains("level2_a"))
        assertTrue(appender.logContains("level3_a"))
        assertTrue(appender.logContains("level3_b"))
        assertTrue(appender.logContains("level2_b"))
        assertTrue(appender.logContains("level1_b"))
        assertEquals(7, appender.logSize)
    }

    @Test
    fun testGetLeft() {
        val level1 = TREE_ROOT.left
        assertNotNull(level1)
        assertEquals("level1_a", level1!!.name)
        assertEquals(5, level1.treeSize)

        val level2 = level1.left
        assertNotNull(level2)
        assertEquals("level2_a", level2!!.name)
        assertEquals(3, level2.treeSize)

        val level3 = level2.left
        assertNotNull(level3)
        assertEquals("level3_a", level3!!.name)
        assertEquals(1, level3.treeSize)
        assertSame(NullNode, level3.right)
        assertSame(NullNode, level3.left)
    }

    @Test
    fun testGetRight() {
        val level1 = TREE_ROOT.right
        assertNotNull(level1)
        assertEquals("level1_b", level1!!.name)
        assertEquals(1, level1.treeSize)
        assertSame(NullNode, level1.right)
        assertSame(NullNode, level1.left)
    }

    private class InMemoryAppender : AppenderBase<ILoggingEvent>() {
        private val log = mutableListOf<ILoggingEvent>()

        init {
            (LoggerFactory.getLogger("root") as Logger).addAppender(this)
            start()
        }

        override fun append(eventObject: ILoggingEvent) {
            log.add(eventObject)
        }

        fun logContains(message: String): Boolean =
            log.any { it.message == message }

        val logSize: Int
            get() = log.size
    }
}
