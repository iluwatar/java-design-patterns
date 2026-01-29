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
package com.iluwatar.combinator

// ABOUTME: Tests for the composed finder factory functions (advanced, filtered, specialized, expanded).
// ABOUTME: Verifies that combinator composition produces correct search results.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FindersTest {
    @Test
    fun advancedFinderTest() {
        val res = advancedFinder("it was", "kingdom", "sea")(text())
        assertEquals(1, res.size)
        assertEquals("It was many and many a year ago,", res[0])
    }

    @Test
    fun filteredFinderTest() {
        val res = filteredFinder(" was ", "many", "child")(text())
        assertEquals(1, res.size)
        assertEquals("But we loved with a love that was more than love-", res[0])
    }

    @Test
    fun specializedFinderTest() {
        val res = specializedFinder("love", "heaven")(text())
        assertEquals(1, res.size)
        assertEquals("With a love that the winged seraphs of heaven", res[0])
    }

    @Test
    fun expandedFinderTest() {
        val res = expandedFinder("It was", "kingdom")(text())
        assertEquals(3, res.size)
        assertEquals("It was many and many a year ago,", res[0])
        assertEquals("In a kingdom by the sea,", res[1])
        assertEquals("In this kingdom by the sea;", res[2])
    }

    private fun text(): String =
        """
        It was many and many a year ago,
        In a kingdom by the sea,
        That a maiden there lived whom you may know
        By the name of ANNABEL LEE;
        And this maiden she lived with no other thought
        Than to love and be loved by me.
        I was a child and she was a child,
        In this kingdom by the sea;
        But we loved with a love that was more than love-
        I and my Annabel Lee;
        With a love that the winged seraphs of heaven
        Coveted her and me.
        """.trimIndent()
}