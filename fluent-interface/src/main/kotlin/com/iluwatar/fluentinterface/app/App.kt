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
package com.iluwatar.fluentinterface.app

// ABOUTME: Entry point demonstrating the Fluent Interface design pattern with two FluentIterable implementations.
// ABOUTME: Shows eager (SimpleFluentIterable) and lazy (LazyFluentIterable) approaches to fluent collection operations.

import com.iluwatar.fluentinterface.fluentiterable.lazy.LazyFluentIterable
import com.iluwatar.fluentinterface.fluentiterable.simple.SimpleFluentIterable
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * The Fluent Interface pattern is useful when you want to provide an easy readable, flowing API.
 * Those interfaces tend to mimic domain specific languages, so they can nearly be read as human
 * languages.
 *
 * In this example two implementations of a FluentIterable interface are given. The
 * SimpleFluentIterable evaluates eagerly and would be too costly for real world applications.
 * The LazyFluentIterable is evaluated on termination. Their usage is demonstrated with a simple
 * number list that is filtered, transformed and collected. The result is printed afterward.
 */
fun main() {
    val integerList = listOf(1, -61, 14, -22, 18, -87, 6, 64, -82, 26, -98, 97, 45, 23, 2, -68)

    prettyPrint("The initial list contains: ", integerList)

    val firstFiveNegatives =
        SimpleFluentIterable.fromCopyOf(integerList).filter(negatives()).first(3).asList()
    prettyPrint("The first three negative values are: ", firstFiveNegatives)

    val lastTwoPositives =
        SimpleFluentIterable.fromCopyOf(integerList).filter(positives()).last(2).asList()
    prettyPrint("The last two positive values are: ", lastTwoPositives)

    SimpleFluentIterable.fromCopyOf(integerList)
        .filter { number -> number % 2 == 0 }
        .first()
        ?.let { evenNumber -> logger.info { "The first even number is: $evenNumber" } }

    val transformedList =
        SimpleFluentIterable.fromCopyOf(integerList)
            .filter(negatives())
            .map(transformToString())
            .asList()
    prettyPrint("A string-mapped list of negative numbers contains: ", transformedList)

    val lastTwoOfFirstFourStringMapped =
        LazyFluentIterable.from(integerList)
            .filter(positives())
            .first(4)
            .last(2)
            .map { number -> "String[$number]" }
            .asList()
    prettyPrint(
        "The lazy list contains the last two of the first four positive numbers " +
            "mapped to Strings: ",
        lastTwoOfFirstFourStringMapped
    )

    LazyFluentIterable.from(integerList)
        .filter(negatives())
        .first(2)
        .last()
        ?.let { number -> logger.info { "Last amongst first two negatives: $number" } }
}

private fun transformToString(): (Int) -> String {
    return { integer -> "String[$integer]" }
}

private fun negatives(): (Int) -> Boolean {
    return { integer -> integer < 0 }
}

private fun positives(): (Int) -> Boolean {
    return { integer -> integer > 0 }
}

private fun <E> prettyPrint(prefix: String, iterable: Iterable<E>) {
    prettyPrint(", ", prefix, iterable)
}

private fun <E> prettyPrint(delimiter: String, prefix: String, iterable: Iterable<E>) {
    val result = iterable.joinToString(separator = delimiter, prefix = prefix, postfix = ".")
    logger.info { result }
}
