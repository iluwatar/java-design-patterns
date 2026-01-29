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
package com.iluwatar.fluentinterface.fluentiterable

// ABOUTME: Abstract base test class defining shared test cases for FluentIterable implementations.
// ABOUTME: Subclasses provide the specific FluentIterable factory; tests cover filter, first, last, map, and forEach.

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

/** FluentIterableTest */
abstract class FluentIterableTest {

    /**
     * Create a new [FluentIterable] from the given integers
     *
     * @param integers The integers
     * @return The new iterable, use for testing
     */
    protected abstract fun createFluentIterable(integers: Iterable<Int>): FluentIterable<Int>

    @Test
    fun testFirst() {
        val integers = listOf(1, 2, 3, 10, 9, 8)
        val first = createFluentIterable(integers).first()
        assertNotNull(first)
        assertEquals(integers[0], first)
    }

    @Test
    fun testFirstEmptyCollection() {
        val integers = emptyList<Int>()
        val first = createFluentIterable(integers).first()
        assertNull(first)
    }

    @Test
    fun testFirstCount() {
        val integers = listOf(1, 2, 3, 10, 9, 8)
        val first4 = createFluentIterable(integers).first(4).asList()

        assertNotNull(first4)
        assertEquals(4, first4.size)

        assertEquals(integers[0], first4[0])
        assertEquals(integers[1], first4[1])
        assertEquals(integers[2], first4[2])
        assertEquals(integers[3], first4[3])
    }

    @Test
    fun testFirstCountLessItems() {
        val integers = listOf(1, 2, 3)
        val first4 = createFluentIterable(integers).first(4).asList()

        assertNotNull(first4)
        assertEquals(3, first4.size)

        assertEquals(integers[0], first4[0])
        assertEquals(integers[1], first4[1])
        assertEquals(integers[2], first4[2])
    }

    @Test
    fun testLast() {
        val integers = listOf(1, 2, 3, 10, 9, 8)
        val last = createFluentIterable(integers).last()
        assertNotNull(last)
        assertEquals(integers[integers.size - 1], last)
    }

    @Test
    fun testLastEmptyCollection() {
        val integers = emptyList<Int>()
        val last = createFluentIterable(integers).last()
        assertNull(last)
    }

    @Test
    fun testLastCount() {
        val integers = listOf(1, 2, 3, 10, 9, 8)
        val last4 = createFluentIterable(integers).last(4).asList()

        assertNotNull(last4)
        assertEquals(4, last4.size)
        assertEquals(3, last4[0])
        assertEquals(10, last4[1])
        assertEquals(9, last4[2])
        assertEquals(8, last4[3])
    }

    @Test
    fun testLastCountLessItems() {
        val integers = listOf(1, 2, 3)
        val last4 = createFluentIterable(integers).last(4).asList()

        assertNotNull(last4)
        assertEquals(3, last4.size)

        assertEquals(1, last4[0])
        assertEquals(2, last4[1])
        assertEquals(3, last4[2])
    }

    @Test
    fun testFilter() {
        val integers = listOf(1, 2, 3, 10, 9, 8)
        val evenItems = createFluentIterable(integers).filter { i -> i % 2 == 0 }.asList()

        assertNotNull(evenItems)
        assertEquals(3, evenItems.size)
        assertEquals(2, evenItems[0])
        assertEquals(10, evenItems[1])
        assertEquals(8, evenItems[2])
    }

    @Test
    fun testMap() {
        val integers = listOf(1, 2, 3)
        val longs = createFluentIterable(integers).map { it.toLong() }.asList()

        assertNotNull(longs)
        assertEquals(integers.size, longs.size)
        assertEquals(1L, longs[0])
        assertEquals(2L, longs[1])
        assertEquals(3L, longs[2])
    }

    @Test
    fun testForEach() {
        val integers = listOf(1, 2, 3)

        val consumer = mockk<(Int) -> Unit>(relaxed = true)
        createFluentIterable(integers).forEach(consumer)

        verify(exactly = 1) { consumer(1) }
        verify(exactly = 1) { consumer(2) }
        verify(exactly = 1) { consumer(3) }
    }

    @Test
    fun testSpliterator() {
        val integers = listOf(1, 2, 3)
        val split = createFluentIterable(integers).spliterator()
        assertNotNull(split)
    }
}
