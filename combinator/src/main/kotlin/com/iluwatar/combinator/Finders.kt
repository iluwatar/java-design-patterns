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

// ABOUTME: Provides factory functions that compose complex Finders from simple ones.
// ABOUTME: Demonstrates the combinator pattern by building advanced, filtered, specialized, and expanded finders.

/**
 * Creates a finder for a complex query: finds lines matching [query] or [orQuery],
 * then excludes lines matching [notQuery].
 *
 * @param query primary search term
 * @param orQuery alternative search term
 * @param notQuery exclusion search term
 * @return a composed Finder
 */
fun advancedFinder(
    query: String,
    orQuery: String,
    notQuery: String,
): Finder = (contains(query) or contains(orQuery)) not contains(notQuery)

/**
 * Creates a finder that finds lines matching [query] and then excludes lines
 * matching any of the [excludeQueries].
 *
 * @param query primary search term
 * @param excludeQueries terms to exclude from results
 * @return a composed Finder
 */
fun filteredFinder(
    query: String,
    vararg excludeQueries: String,
): Finder =
    excludeQueries.fold(contains(query)) { finder, q ->
        finder not contains(q)
    }

/**
 * Creates a specialized finder where each successive query narrows the results
 * of the previous one (logical AND chain).
 *
 * @param queries array of search terms applied sequentially
 * @return a composed Finder
 */
fun specializedFinder(vararg queries: String): Finder =
    queries.fold(identMult()) { finder, query ->
        finder and contains(query)
    }

/**
 * Creates an expanded finder that returns results matching any of the queries
 * (logical OR chain).
 *
 * @param queries array of search terms combined with OR
 * @return a composed Finder
 */
fun expandedFinder(vararg queries: String): Finder =
    queries.fold(identSum()) { finder, query ->
        finder or contains(query)
    }

/** Identity element for AND composition: returns all lines from the text. */
private fun identMult(): Finder =
    { text ->
        text.split("\n")
    }

/** Identity element for OR composition: returns an empty list. */
private fun identSum(): Finder =
    { _ ->
        emptyList()
    }