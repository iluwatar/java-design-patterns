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

// ABOUTME: Value object representing a set of lottery numbers.
// ABOUTME: Supports both random generation and creation from a given set of numbers.
package com.iluwatar.hexagonal.domain

import java.security.SecureRandom
import java.util.Collections

/**
 * Value object representing lottery numbers. This lottery uses sets of 4 numbers. The numbers must
 * be unique and between 1 and 20.
 */
class LotteryNumbers private constructor(givenNumbers: Set<Int>? = null) {

    private val numbers: MutableSet<Int> = HashSet()

    init {
        if (givenNumbers != null) {
            numbers.addAll(givenNumbers)
        } else {
            generateRandomNumbers()
        }
    }

    /**
     * Get numbers.
     *
     * @return lottery numbers as unmodifiable set
     */
    fun getNumbers(): Set<Int> = Collections.unmodifiableSet(numbers)

    /**
     * Get numbers as string.
     *
     * @return numbers as comma separated string
     */
    fun getNumbersAsString(): String = numbers.joinToString(",")

    /**
     * Generates 4 unique random numbers between 1-20 into numbers set.
     */
    private fun generateRandomNumbers() {
        numbers.clear()
        val generator = RandomNumberGenerator(MIN_NUMBER, MAX_NUMBER)
        while (numbers.size < NUM_NUMBERS) {
            val num = generator.nextInt()
            numbers.add(num)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        other as LotteryNumbers
        return numbers == other.numbers
    }

    override fun hashCode(): Int = numbers.hashCode()

    override fun toString(): String = "LotteryNumbers(numbers=$numbers)"

    /**
     * Helper class for generating random numbers.
     */
    private class RandomNumberGenerator(min: Int, max: Int) {
        private val randomIterator = SecureRandom().ints(min, max + 1).iterator()

        /**
         * Gets next random integer in [min, max] range.
         *
         * @return a random number in the range (min, max)
         */
        fun nextInt(): Int = randomIterator.nextInt()
    }

    companion object {
        const val MIN_NUMBER = 1
        const val MAX_NUMBER = 20
        const val NUM_NUMBERS = 4

        /**
         * Creates a random lottery number.
         *
         * @return random LotteryNumbers
         */
        fun createRandom(): LotteryNumbers = LotteryNumbers()

        /**
         * Creates lottery number from given set of numbers.
         *
         * @return given LotteryNumbers
         */
        fun create(givenNumbers: Set<Int>): LotteryNumbers = LotteryNumbers(givenNumbers)
    }
}
