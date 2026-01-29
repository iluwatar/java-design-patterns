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

// ABOUTME: Defines the FluentIterable interface for chaining collection operations in a fluent style.
// ABOUTME: Provides filter, first, last, map, and asList operations with a companion copyToList utility.

/**
 * The FluentIterable is a more convenient implementation of the common iterable interface based on
 * the fluent interface design pattern. This interface defines common operations, but doesn't aim to
 * be complete. It was inspired by Guava's com.google.common.collect.FluentIterable.
 *
 * @param E is the class of objects the iterable contains
 */
interface FluentIterable<E> : Iterable<E> {

    /**
     * Filters the contents of Iterable using the given predicate, leaving only the ones which
     * satisfy the predicate.
     *
     * @param predicate the condition to test with for the filtering. If the test is negative, the
     *     tested object is removed by the iterator.
     * @return a filtered FluentIterable
     */
    fun filter(predicate: (E) -> Boolean): FluentIterable<E>

    /**
     * Returns the first element of this iterable if present, else returns null.
     *
     * @return the first element after the iteration is evaluated, or null
     */
    fun first(): E?

    /**
     * Evaluates the iteration and leaves only the count first elements.
     *
     * @return the first count elements as a FluentIterable
     */
    fun first(count: Int): FluentIterable<E>

    /**
     * Evaluates the iteration and returns the last element. This is a terminating operation.
     *
     * @return the last element after the iteration is evaluated, or null
     */
    fun last(): E?

    /**
     * Evaluates the iteration and leaves only the count last elements.
     *
     * @return the last count elements as a FluentIterable
     */
    fun last(count: Int): FluentIterable<E>

    /**
     * Transforms this FluentIterable into a new one containing objects of the type T.
     *
     * @param function a function that transforms an instance of E into an instance of T
     * @param T the target type of the transformation
     * @return a new FluentIterable of the new type
     */
    fun <T> map(function: (E) -> T): FluentIterable<T>

    /**
     * Returns the contents of this Iterable as a List.
     *
     * @return a List representation of this Iterable
     */
    fun asList(): List<E>

    companion object {
        /**
         * Utility method that iterates over iterable and adds the contents to a list.
         *
         * @param iterable the iterable to collect
         * @param E the type of the objects to iterate
         * @return a list with all objects of the given iterator
         */
        fun <E> copyToList(iterable: Iterable<E>): List<E> {
            val copy = mutableListOf<E>()
            iterable.forEach { copy.add(it) }
            return copy
        }
    }
}
