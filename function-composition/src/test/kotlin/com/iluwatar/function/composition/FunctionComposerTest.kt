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
package com.iluwatar.function.composition

// ABOUTME: Tests for FunctionComposer verifying correct function composition behavior.
// ABOUTME: Covers identity, zero-producing, negation, and inverse function compositions.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/** Test class for [FunctionComposer]. */
class FunctionComposerTest {

    /** Tests the composition of two functions. */
    @Test
    fun testComposeFunctions() {
        val timesTwo: (Int) -> Int = { x -> x * 2 }
        val square: (Int) -> Int = { x -> x * x }

        val composed = FunctionComposer.composeFunctions(timesTwo, square)

        assertEquals(36, composed(3), "Expected output of composed functions is 36")
    }

    /** Tests function composition with identity function. */
    @Test
    fun testComposeWithIdentity() {
        val identity: (Int) -> Int = { it }
        val timesThree: (Int) -> Int = { x -> x * 3 }

        val composedLeft = FunctionComposer.composeFunctions(identity, timesThree)
        val composedRight = FunctionComposer.composeFunctions(timesThree, identity)

        assertEquals(9, composedLeft(3), "Composition with identity on the left should be the same")
        assertEquals(9, composedRight(3), "Composition with identity on the right should be the same")
    }

    /** Tests function composition resulting in zero. */
    @Test
    fun testComposeToZero() {
        val multiply: (Int) -> Int = { x -> x * 10 }
        val toZero: (Int) -> Int = { 0 }

        val composed = FunctionComposer.composeFunctions(multiply, toZero)

        assertEquals(0, composed(5), "Expected output of function composition leading to zero is 0")
    }

    /** Tests the composition with a negative function. */
    @Test
    fun testComposeNegative() {
        val negate: (Int) -> Int = { x -> -x }
        val square: (Int) -> Int = { x -> x * x }

        val composed = FunctionComposer.composeFunctions(negate, square)

        assertEquals(9, composed(3), "Expected square of negative number to be positive")
    }

    /** Tests the composition of functions that cancel each other out. */
    @Test
    fun testComposeInverseFunctions() {
        val timesTwo: (Int) -> Int = { x -> x * 2 }
        val half: (Int) -> Int = { x -> x / 2 }

        val composed = FunctionComposer.composeFunctions(timesTwo, half)

        assertEquals(5, composed(5), "Expect the functions to cancel each other out")
    }
}
