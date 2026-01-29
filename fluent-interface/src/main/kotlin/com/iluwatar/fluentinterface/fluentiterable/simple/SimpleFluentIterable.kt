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
package com.iluwatar.fluentinterface.fluentiterable.simple

// ABOUTME: Eager implementation of FluentIterable that evaluates operations immediately on a mutable copy.
// ABOUTME: Each chained operation mutates the internal list and returns this for fluent chaining.

import com.iluwatar.fluentinterface.fluentiterable.FluentIterable

/**
 * This is a simple implementation of the FluentIterable interface. It evaluates all chained
 * operations eagerly. This implementation would be costly to be utilized in real applications.
 *
 * @param E the type of the objects the iteration is about
 */
class SimpleFluentIterable<E>(
    private val iterable: Iterable<E>
) : FluentIterable<E> {

    /**
     * Filters the contents of Iterable using the given predicate, leaving only the ones which
     * satisfy the predicate.
     *
     * @param predicate the condition to test with for the filtering. If the test is negative, the
     *     tested object is removed by the iterator.
     * @return the same FluentIterable with a filtered collection
     */
    override fun filter(predicate: (E) -> Boolean): FluentIterable<E> {
        val iterator = iterator()
        while (iterator.hasNext()) {
            val nextElement = iterator.next()
            if (!predicate(nextElement)) {
                (iterator as MutableIterator).remove()
            }
        }
        return this
    }

    /**
     * Can be used to collect objects from the Iterable. Is a terminating operation.
     *
     * @return the first object of the Iterable, or null if empty
     */
    override fun first(): E? {
        val resultIterator = first(1).iterator()
        return if (resultIterator.hasNext()) resultIterator.next() else null
    }

    /**
     * Can be used to collect objects from the Iterable. Is a terminating operation.
     *
     * @param count defines the number of objects to return
     * @return the same FluentIterable with a collection decimated to a maximum of 'count' first
     *     objects.
     */
    override fun first(count: Int): FluentIterable<E> {
        val iterator = iterator()
        var currentCount = 0
        while (iterator.hasNext()) {
            iterator.next()
            if (currentCount >= count) {
                (iterator as MutableIterator).remove()
            }
            currentCount++
        }
        return this
    }

    /**
     * Can be used to collect objects from the Iterable. Is a terminating operation.
     *
     * @return the last object of the Iterable, or null if empty
     */
    override fun last(): E? {
        val list = last(1).asList()
        return if (list.isEmpty()) null else list[0]
    }

    /**
     * Can be used to collect objects from the Iterable. Is a terminating operation.
     *
     * @param count defines the number of objects to return
     * @return the same FluentIterable with a collection decimated to a maximum of 'count' last
     *     objects
     */
    override fun last(count: Int): FluentIterable<E> {
        val remainingElementsCount = getRemainingElementsCount()
        val iterator = iterator()
        var currentIndex = 0
        while (iterator.hasNext()) {
            iterator.next()
            if (currentIndex < remainingElementsCount - count) {
                (iterator as MutableIterator).remove()
            }
            currentIndex++
        }
        return this
    }

    /**
     * Transforms this FluentIterable into a new one containing objects of the type T.
     *
     * @param function a function that transforms an instance of E into an instance of T
     * @param T the target type of the transformation
     * @return a new FluentIterable of the new type
     */
    override fun <T> map(function: (E) -> T): FluentIterable<T> {
        val temporaryList = mutableListOf<T>()
        this.forEach { temporaryList.add(function(it)) }
        return from(temporaryList)
    }

    /**
     * Collects all remaining objects of this Iterable into a list.
     *
     * @return a list with all remaining objects of this Iterable
     */
    override fun asList(): List<E> {
        return toList(iterable.iterator())
    }

    override fun iterator(): Iterator<E> {
        return iterable.iterator()
    }

    /**
     * Find the count of remaining objects of current iterable.
     *
     * @return the count of remaining objects of the current Iterable
     */
    fun getRemainingElementsCount(): Int {
        var counter = 0
        for (ignored in this) {
            counter++
        }
        return counter
    }

    companion object {
        /**
         * Constructs FluentIterable from iterable.
         *
         * @return a FluentIterable from a given iterable. Calls the SimpleFluentIterable constructor.
         */
        fun <E> from(iterable: Iterable<E>): FluentIterable<E> {
            return SimpleFluentIterable(iterable)
        }

        fun <E> fromCopyOf(iterable: Iterable<E>): FluentIterable<E> {
            val copy = FluentIterable.copyToList(iterable)
            return SimpleFluentIterable(copy.toMutableList())
        }

        /**
         * Collects the remaining objects of the given iterator into a List.
         *
         * @return a new List with the remaining objects.
         */
        fun <E> toList(iterator: Iterator<E>): List<E> {
            val copy = mutableListOf<E>()
            iterator.forEachRemaining { copy.add(it) }
            return copy
        }
    }
}
