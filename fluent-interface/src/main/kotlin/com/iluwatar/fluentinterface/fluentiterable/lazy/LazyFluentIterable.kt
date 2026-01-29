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
package com.iluwatar.fluentinterface.fluentiterable.lazy

// ABOUTME: Lazy implementation of FluentIterable that defers evaluation until a terminating operation.
// ABOUTME: Chained operations (filter, first, last, map) build up decorated iterators evaluated on demand.

import com.iluwatar.fluentinterface.fluentiterable.FluentIterable

/**
 * This is a lazy implementation of the FluentIterable interface. It evaluates all chained
 * operations when a terminating operation is applied.
 *
 * @param E the type of the objects the iteration is about
 */
open class LazyFluentIterable<E> protected constructor(
    private val iterable: Iterable<E>
) : FluentIterable<E> {

    /**
     * Filters the contents of Iterable using the given predicate, leaving only the ones which
     * satisfy the predicate.
     *
     * @param predicate the condition to test with for the filtering. If the test is negative, the
     *     tested object is removed by the iterator.
     * @return a new FluentIterable object that decorates the source iterable
     */
    override fun filter(predicate: (E) -> Boolean): FluentIterable<E> {
        val source = this
        return object : LazyFluentIterable<E>(source) {
            override fun iterator(): Iterator<E> {
                return object : DecoratingIterator<E>(source.iterator()) {
                    override fun computeNext(): E? {
                        while (fromIterator!!.hasNext()) {
                            val candidate = fromIterator.next()
                            if (predicate(candidate)) {
                                return candidate
                            }
                        }
                        return null
                    }
                }
            }
        }
    }

    /**
     * Can be used to collect objects from the iteration. Is a terminating operation.
     *
     * @return the first object of this Iterable, or null if empty
     */
    override fun first(): E? {
        val resultIterator = first(1).iterator()
        return if (resultIterator.hasNext()) resultIterator.next() else null
    }

    /**
     * Can be used to collect objects from the iteration.
     *
     * @param count defines the number of objects to return
     * @return the same FluentIterable with a collection decimated to a maximum of 'count' first
     *     objects.
     */
    override fun first(count: Int): FluentIterable<E> {
        val source = this
        return object : LazyFluentIterable<E>(source) {
            override fun iterator(): Iterator<E> {
                return object : DecoratingIterator<E>(source.iterator()) {
                    var currentIndex = 0

                    override fun computeNext(): E? {
                        if (currentIndex < count && fromIterator!!.hasNext()) {
                            val candidate = fromIterator.next()
                            currentIndex++
                            return candidate
                        }
                        return null
                    }
                }
            }
        }
    }

    /**
     * Can be used to collect objects from the iteration. Is a terminating operation.
     *
     * @return the last object of this Iterable, or null if empty
     */
    override fun last(): E? {
        val resultIterator = last(1).iterator()
        return if (resultIterator.hasNext()) resultIterator.next() else null
    }

    /**
     * Can be used to collect objects from the Iterable. Is a terminating operation. This operation
     * is memory intensive, because the contents of this Iterable are collected into a List, when
     * the next object is requested.
     *
     * @param count defines the number of objects to return
     * @return the same FluentIterable with a collection decimated to a maximum of 'count' last
     *     objects
     */
    override fun last(count: Int): FluentIterable<E> {
        val source = this
        return object : LazyFluentIterable<E>(source) {
            override fun iterator(): Iterator<E> {
                return object : DecoratingIterator<E>(source.iterator()) {
                    private var stopIndex = 0
                    private var totalElementsCount = 0
                    private var list: MutableList<E>? = null
                    private var currentIndex = 0

                    override fun computeNext(): E? {
                        initialize()

                        while (currentIndex < stopIndex && fromIterator!!.hasNext()) {
                            currentIndex++
                            fromIterator.next()
                        }
                        if (currentIndex >= stopIndex && fromIterator!!.hasNext()) {
                            return fromIterator.next()
                        }
                        return null
                    }

                    private fun initialize() {
                        if (list == null) {
                            list = mutableListOf()
                            source.forEach { list!!.add(it) }
                            totalElementsCount = list!!.size
                            stopIndex = totalElementsCount - count
                        }
                    }
                }
            }
        }
    }

    /**
     * Transforms this FluentIterable into a new one containing objects of the type T.
     *
     * @param function a function that transforms an instance of E into an instance of T
     * @param T the target type of the transformation
     * @return a new FluentIterable of the new type
     */
    override fun <T> map(function: (E) -> T): FluentIterable<T> {
        val source = this
        return object : LazyFluentIterable<T>(emptyList()) {
            override fun iterator(): Iterator<T> {
                return object : DecoratingIterator<T>(null) {
                    val oldTypeIterator = source.iterator()

                    override fun computeNext(): T? {
                        return if (oldTypeIterator.hasNext()) {
                            val candidate = oldTypeIterator.next()
                            function(candidate)
                        } else {
                            null
                        }
                    }
                }
            }
        }
    }

    /**
     * Collects all remaining objects of this iteration into a list.
     *
     * @return a list with all remaining objects of this iteration
     */
    override fun asList(): List<E> {
        return FluentIterable.copyToList(this)
    }

    override fun iterator(): Iterator<E> {
        return object : DecoratingIterator<E>(iterable.iterator()) {
            override fun computeNext(): E? {
                return if (fromIterator!!.hasNext()) fromIterator.next() else null
            }
        }
    }

    companion object {
        /**
         * Constructs FluentIterable from given iterable.
         *
         * @return a FluentIterable from a given iterable. Calls the LazyFluentIterable constructor.
         */
        fun <E> from(iterable: Iterable<E>): FluentIterable<E> {
            return LazyFluentIterable(iterable)
        }
    }
}
