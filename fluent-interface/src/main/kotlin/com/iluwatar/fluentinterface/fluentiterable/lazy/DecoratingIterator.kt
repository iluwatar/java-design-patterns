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

// ABOUTME: Abstract iterator decorator used by LazyFluentIterable for lazy evaluation.
// ABOUTME: Subclasses implement computeNext() to define custom iteration behavior; null signals end of data.

/**
 * This class is used to realize LazyFluentIterables. It decorates a given iterator. Does not
 * support consecutive hasNext() calls.
 *
 * @param E Iterable Collection of Elements of Type E
 */
abstract class DecoratingIterator<E>(
    protected val fromIterator: Iterator<E>?
) : Iterator<E> {

    private var next: E? = null

    /**
     * Precomputes and saves the next element of the Iterable. null is considered as end of data.
     *
     * @return true if a next element is available
     */
    override fun hasNext(): Boolean {
        next = computeNext()
        return next != null
    }

    /**
     * Returns the next element of the Iterable.
     *
     * @return the next element of the Iterable, or delegates to fromIterator if not precomputed.
     */
    override fun next(): E {
        val current = next
        return if (current == null) {
            fromIterator!!.next()
        } else {
            next = null
            current
        }
    }

    /**
     * Computes the next object of the Iterable. Can be implemented to realize custom behaviour for
     * an iteration process. null is considered as end of data.
     *
     * @return the next element of the Iterable.
     */
    abstract fun computeNext(): E?
}
