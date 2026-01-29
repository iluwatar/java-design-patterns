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
package com.iluwatar.currying

// ABOUTME: Entry point demonstrating the currying pattern for building Book instances.
// ABOUTME: Shows how curried functions create partial applications for genre and author specialization.

import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalDate

private val logger = KotlinLogging.logger {}

/**
 * Currying decomposes a function with multiple arguments into multiple functions that take a single
 * argument. A curried function which has only been passed some of its arguments is called a partial
 * application. Partial application is useful since it can be used to create specialised functions
 * in a concise way.
 *
 * In this example, a librarian uses a curried book builder function to create books belonging to
 * desired genres and written by specific authors.
 */
fun main() {
    logger.info { "Librarian begins their work." }

    // Defining genre book functions
    val fantasyBookFunc = Book.builder()(Genre.FANTASY)
    val horrorBookFunc = Book.builder()(Genre.HORROR)
    val scifiBookFunc = Book.builder()(Genre.SCIFI)

    // Defining author book functions
    val kingFantasyBooksFunc = fantasyBookFunc("Stephen King")
    val kingHorrorBooksFunc = horrorBookFunc("Stephen King")
    val rowlingFantasyBooksFunc = fantasyBookFunc("J.K. Rowling")

    // Creates books by Stephen King (horror and fantasy genres)
    val shining =
        kingHorrorBooksFunc("The Shining")(LocalDate.of(1977, 1, 28))
    val darkTower =
        kingFantasyBooksFunc("The Dark Tower: Gunslinger")(LocalDate.of(1982, 6, 10))

    // Creates fantasy books by J.K. Rowling
    val chamberOfSecrets =
        rowlingFantasyBooksFunc("Harry Potter and the Chamber of Secrets")(LocalDate.of(1998, 7, 2))

    // Create sci-fi books
    val dune =
        scifiBookFunc("Frank Herbert")("Dune")(LocalDate.of(1965, 8, 1))
    val foundation =
        scifiBookFunc("Isaac Asimov")("Foundation")(LocalDate.of(1942, 5, 1))

    logger.info { "Stephen King Books:" }
    logger.info { shining.toString() }
    logger.info { darkTower.toString() }

    logger.info { "J.K. Rowling Books:" }
    logger.info { chamberOfSecrets.toString() }

    logger.info { "Sci-fi Books:" }
    logger.info { dune.toString() }
    logger.info { foundation.toString() }
}
