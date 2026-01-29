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

// ABOUTME: Defines the Finder functional type and its combinator operations (or, and, not).
// ABOUTME: Provides the core building block for composing text-search functions via the combinator pattern.

/**
 * Functional type alias representing a text search function.
 * A Finder takes a text string and returns a list of matching lines.
 */
typealias Finder = (String) -> List<String>

/**
 * Creates a Finder that returns all lines containing the given word (case-insensitive).
 *
 * @param word the word to search for
 * @return a Finder that filters lines containing the word
 */
fun contains(word: String): Finder =
    { text ->
        text
            .split("\n")
            .filter { line -> line.lowercase().contains(word.lowercase()) }
    }

/**
 * Combinator that combines this Finder with another using logical OR.
 * The result includes lines found by either finder.
 *
 * @param other finder to combine with
 * @return a new Finder that returns results from both finders
 */
infix fun Finder.or(other: Finder): Finder =
    { text ->
        this(text) + other(text)
    }

/**
 * Combinator that excludes results found by the other Finder.
 *
 * @param other finder whose results are excluded
 * @return a new Finder that returns results from this finder minus results from the other
 */
infix fun Finder.not(other: Finder): Finder =
    { text ->
        val excluded = other(text)
        this(text).filterNot { it in excluded }
    }

/**
 * Combinator that combines this Finder with another using logical AND.
 * Each line found by this finder is further searched by the other finder.
 *
 * @param other finder to apply to each result
 * @return a new Finder that returns lines matching both finders
 */
infix fun Finder.and(other: Finder): Finder =
    { text ->
        this(text).flatMap { line -> other(line) }
    }