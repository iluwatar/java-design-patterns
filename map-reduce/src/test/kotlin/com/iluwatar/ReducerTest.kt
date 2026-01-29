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

// ABOUTME: Unit tests for the Reducer component of the MapReduce pattern.
// ABOUTME: Tests aggregation of word counts and descending order sorting.
package com.iluwatar

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ReducerTest {

    @Test
    fun testReduceWithMultipleWords() {
        val input = mapOf(
            "apple" to listOf(2, 3, 1),
            "banana" to listOf(1, 1),
            "cherry" to listOf(4)
        )

        val result = Reducer.reduce(input)

        assertEquals(3, result.size)
        assertEquals("apple", result[0].key)
        assertEquals(6, result[0].value)
        assertEquals("cherry", result[1].key)
        assertEquals(4, result[1].value)
        assertEquals("banana", result[2].key)
        assertEquals(2, result[2].value)
    }

    @Test
    fun testReduceWithEmptyInput() {
        val input = emptyMap<String, List<Int>>()

        val result = Reducer.reduce(input)

        assertTrue(result.isEmpty())
    }

    @Test
    fun testReduceWithTiedCounts() {
        val input = mapOf(
            "tie1" to listOf(2, 2),
            "tie2" to listOf(1, 3)
        )

        val result = Reducer.reduce(input)

        assertEquals(2, result.size)
        assertEquals(4, result[0].value)
        assertEquals(4, result[1].value)
        // Note: The order of tie1 and tie2 is not guaranteed in case of a tie
    }
}
