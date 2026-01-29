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

// ABOUTME: Data class representing a book, with curried builder functions for step-by-step construction.
// ABOUTME: Demonstrates the currying pattern using Kotlin function types and a named-step builder.

import java.time.LocalDate

/** Book data class with curried builder functions. */
data class Book(
    val genre: Genre,
    val author: String,
    val title: String,
    val publicationDate: LocalDate,
) {
    /** Curried book builder/creator function using Kotlin function types. */
    companion object {
        val bookCreator: (Genre) -> (String) -> (String) -> (LocalDate) -> Book =
            { genre ->
                { author ->
                    { title ->
                        { publicationDate ->
                            Book(genre, author, title, publicationDate)
                        }
                    }
                }
            }

        /**
         * Implements the builder pattern using functional interfaces to create a more readable book
         * creator function. This function is equivalent to the [bookCreator] function.
         */
        fun builder(): AddGenre = { genre ->
            { author ->
                { title ->
                    { publicationDate ->
                        Book(genre, author, title, publicationDate)
                    }
                }
            }
        }
    }
}

/** Functional type alias which adds the genre to a book. */
typealias AddGenre = (Genre) -> AddAuthor

/** Functional type alias which adds the author to a book. */
typealias AddAuthor = (String) -> AddTitle

/** Functional type alias which adds the title to a book. */
typealias AddTitle = (String) -> AddPublicationDate

/** Functional type alias which adds the publication date to a book. */
typealias AddPublicationDate = (LocalDate) -> Book
