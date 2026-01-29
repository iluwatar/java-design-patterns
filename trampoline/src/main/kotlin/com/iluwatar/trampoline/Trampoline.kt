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
package com.iluwatar.trampoline

// ABOUTME: Sealed interface implementing the Trampoline pattern for stack-safe recursion.
// ABOUTME: Converts recursive algorithms into iterative loops via Done and More variants.

/**
 * Trampoline pattern allows defining recursive algorithms by iterative loop.
 *
 * When [result] is called on a returned Trampoline, internally it will iterate calling [jump] on
 * the returned Trampoline as long as the concrete instance returned is [More],
 * stopping once the returned instance is [Done].
 *
 * Essentially we convert looping via recursion into iteration, the key enabling mechanism is the
 * fact that [More] is a lazy operation.
 *
 * @param T is type for returning result.
 */
sealed interface Trampoline<T> {

    /** Jump to next stage. */
    fun jump(): Trampoline<T> = this

    /** Get the final computed result by iterating through the trampoline chain. */
    fun result(): T

    /** Checks if complete. */
    fun complete(): Boolean

    /** A completed Trampoline holding the final [value]. */
    data class Done<T>(val value: T) : Trampoline<T> {
        override fun result(): T = value
        override fun complete(): Boolean = true
    }

    /**
     * A Trampoline that has more work to do.
     * The [next] function lazily produces the next step in the computation.
     */
    class More<T>(private val next: () -> Trampoline<T>) : Trampoline<T> {
        override fun jump(): Trampoline<T> = next()
        override fun complete(): Boolean = false

        override fun result(): T {
            var current: Trampoline<T> = this
            while (!current.complete()) {
                current = current.jump()
            }
            return current.result()
        }
    }
}

/** Creates a completed [Trampoline] holding the given [result]. */
fun <T> done(result: T): Trampoline<T> = Trampoline.Done(result)

/** Creates a [Trampoline] that has more work to do, with the next step provided lazily by [next]. */
fun <T> more(next: () -> Trampoline<T>): Trampoline<T> = Trampoline.More(next)
