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

// ABOUTME: Entry point demonstrating the Version Number pattern for optimistic concurrency control.
// ABOUTME: Shows how Alice and Bob encounter a version conflict when updating the same Book concurrently.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * The Version Number pattern helps to resolve concurrency conflicts in applications. Usually these
 * conflicts arise in database operations, when multiple clients are trying to update the same
 * record simultaneously. Resolving such conflicts requires determining whether an object has
 * changed. For this reason we need a version number that is incremented with each change to the
 * underlying data, e.g. database. The version number can be used by repositories to check for
 * external changes and to report concurrency issues to the users.
 *
 * In this example we show how Alice and Bob will try to update the [Book] and save it
 * simultaneously to [BookRepository], which represents a typical database.
 *
 * As in real databases, each client operates with copy of the data instead of original data
 * passed by reference, that's why we are using [Book.copy] here.
 */
fun main() {
    val bookId = 1L

    val bookRepository = BookRepository()
    val book = Book(id = bookId)
    bookRepository.add(book) // adding a book with empty title and author
    logger.info { "An empty book with version ${book.version} was added to repository" }

    // Alice and Bob took the book concurrently
    val aliceBook = bookRepository.get(bookId)
    val bobBook = bookRepository.get(bookId)

    aliceBook.title = "Kama Sutra" // Alice has updated book title
    bookRepository.update(aliceBook) // and successfully saved book in database
    logger.info { "Alice updates the book with new version ${aliceBook.version}" }

    // now Bob has the stale version of the book with empty title and version = 0
    // while actual book in database has filled title and version = 1
    bobBook.author = "Vatsyayana Mallanaga" // Bob updates the author
    try {
        logger.info { "Bob tries to update the book with his version ${bobBook.version}" }
        bookRepository.update(bobBook) // Bob tries to save his book to database
    } catch (e: VersionMismatchException) {
        // Bob update fails, and book in repository remained untouchable
        logger.info { "Exception: ${e.message}" }
        // Now Bob should reread actual book from repository, do his changes again and save again
    }
}
