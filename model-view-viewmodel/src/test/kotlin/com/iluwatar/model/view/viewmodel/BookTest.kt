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
// ABOUTME: Unit tests for the Book model and BookViewModel classes.
// ABOUTME: Verifies book data operations including load, select, and delete.
package com.iluwatar.model.view.viewmodel

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BookTest {

    private lateinit var bvm: BookViewModel
    private lateinit var testBook: Book
    private lateinit var testBookList: MutableList<Book>
    private lateinit var testBookTwo: Book
    private lateinit var testBookThree: Book

    @BeforeEach
    fun setUp() {
        bvm = BookViewModel()
        testBook = Book(
            "Head First Design Patterns: A Brain-Friendly Guide",
            "Eric Freeman, Bert Bates, Kathy Sierra, Elisabeth Robson",
            "Head First Design Patterns Description"
        )
        testBookList = bvm.getBookList()
        testBookTwo = Book(
            "Head First Design Patterns: A Brain-Friendly Guide",
            "Eric Freeman, Bert Bates, Kathy Sierra, Elisabeth Robson",
            "Head First Design Patterns Description"
        )
        testBookThree = Book(
            "Design Patterns: Elements of Reusable Object-Oriented Software",
            "Erich Gamma, Richard Helm, Ralph Johnson, and John Vlissides",
            "Design Patterns Description"
        )
    }

    @Test
    fun testBookModel() {
        assertNotNull(testBook)
    }

    @Test
    fun testEquals() {
        assertEquals(testBook, testBookTwo)
    }

    @Test
    fun testToString() {
        assertEquals(testBook.toString(), testBookTwo.toString())
        assertNotEquals(testBook.toString(), testBookThree.toString())
    }

    @Test
    fun testHashCode() {
        assertTrue(testBook == testBookTwo && testBookTwo == testBook)
        assertEquals(testBook.hashCode(), testBookTwo.hashCode())
    }

    @Test
    fun testLoadData() {
        assertNotNull(testBookList)
        assertTrue(testBookList[0].toString().contains("Head First Design Patterns"))
    }

    @Test
    fun testSelectedData() {
        bvm.selectedBook = testBook
        assertNotNull(bvm.selectedBook)
        assertEquals(testBook.toString(), bvm.selectedBook.toString())
        assertTrue(true, bvm.selectedBook.toString())
    }

    @Test
    fun testDeleteData() {
        bvm.selectedBook = testBook
        assertNotNull(bvm.selectedBook)
        assertTrue(testBookList[0].toString().contains("Head First Design Patterns"))
        bvm.deleteBook()
        assertNull(bvm.selectedBook)
        assertFalse(testBookList[0].toString().contains("Head First Design Patterns"))
    }
}
