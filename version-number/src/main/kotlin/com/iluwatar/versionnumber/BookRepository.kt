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

// ABOUTME: Simplified database repository that stores copies of Book objects.
// ABOUTME: Implements optimistic concurrency control using version numbers on updates.

import java.util.concurrent.ConcurrentHashMap

/**
 * This repository represents simplified database. As a typical database do, repository operates
 * with copies of object. So client and repo has different copies of book, which can lead to
 * concurrency conflicts as much as in real databases.
 */
class BookRepository {
    private val collection = ConcurrentHashMap<Long, Book>()
    private val lock = Any()

    /**
     * Adds book to collection. Actually we are putting copy of book (saving a book by value, not by
     * reference);
     */
    fun add(book: Book) {
        if (collection.containsKey(book.id)) {
            throw BookDuplicateException("Duplicated book with id: ${book.id}")
        }

        // add copy of the book
        collection[book.id] = book.copy()
    }

    /** Updates book in collection only if client has modified the latest version of the book. */
    fun update(book: Book) {
        if (!collection.containsKey(book.id)) {
            throw BookNotFoundException("Not found book with id: ${book.id}")
        }

        // used synchronized block to ensure only one thread compares and update the version
        synchronized(lock) {
            val latestBook = collection[book.id]!!
            if (book.version != latestBook.version) {
                throw VersionMismatchException(
                    "Tried to update stale version ${book.version}" +
                        " while actual version is ${latestBook.version}",
                )
            }

            // update version, including client representation - modify by reference here
            book.version = book.version + 1

            // save book copy to repository
            collection[book.id] = book.copy()
        }
    }

    /**
     * Returns book representation to the client. Representation means we are returning copy of the
     * book.
     */
    fun get(bookId: Long): Book {
        if (!collection.containsKey(bookId)) {
            throw BookNotFoundException("Not found book with id: $bookId")
        }

        // return copy of the book
        return collection[bookId]!!.copy()
    }
}
