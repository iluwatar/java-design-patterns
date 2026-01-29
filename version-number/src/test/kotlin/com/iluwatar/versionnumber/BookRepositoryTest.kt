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
package com.iluwatar.versionnumber

// ABOUTME: Tests for BookRepository verifying optimistic concurrency control behavior.
// ABOUTME: Covers version tracking, concurrent update conflicts, and stale version detection.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** Tests for [BookRepository] */
class BookRepositoryTest {
    private val bookId = 1L
    private val bookRepository = BookRepository()

    @BeforeEach
    fun setUp() {
        val book = Book(id = bookId)
        bookRepository.add(book)
    }

    @Test
    fun testDefaultVersionRemainsZeroAfterAdd() {
        val book = bookRepository.get(bookId)
        assertEquals(0, book.version)
    }

    @Test
    fun testAliceAndBobHaveDifferentVersionsAfterAliceUpdate() {
        val aliceBook = bookRepository.get(bookId)
        val bobBook = bookRepository.get(bookId)

        aliceBook.title = "Kama Sutra"
        bookRepository.update(aliceBook)

        assertEquals(1, aliceBook.version)
        assertEquals(0, bobBook.version)
        val actualBook = bookRepository.get(bookId)
        assertEquals(aliceBook.version, actualBook.version)
        assertEquals(aliceBook.title, actualBook.title)
        assertNotEquals(aliceBook.title, bobBook.title)
    }

    @Test
    fun testShouldThrowVersionMismatchExceptionOnStaleUpdate() {
        val aliceBook = bookRepository.get(bookId)
        val bobBook = bookRepository.get(bookId)

        aliceBook.title = "Kama Sutra"
        bookRepository.update(aliceBook)

        bobBook.author = "Vatsyayana Mallanaga"
        try {
            bookRepository.update(bobBook)
        } catch (e: VersionMismatchException) {
            assertEquals(0, bobBook.version)
            val actualBook = bookRepository.get(bookId)
            assertEquals(1, actualBook.version)
            assertEquals(aliceBook.version, actualBook.version)
            assertEquals("", bobBook.title)
            assertNotEquals(aliceBook.author, bobBook.author)
        }
    }
}
