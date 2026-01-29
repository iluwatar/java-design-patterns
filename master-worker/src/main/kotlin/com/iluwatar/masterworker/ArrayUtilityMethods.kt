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

// ABOUTME: Utility functions for matrix and array operations.
// ABOUTME: Provides comparison, creation, and printing methods for 2D integer arrays.
package com.iluwatar.masterworker

import io.github.oshai.kotlinlogging.KotlinLogging
import java.security.SecureRandom

private val logger = KotlinLogging.logger {}

/**
 * Object ArrayUtilityMethods has some utility methods for matrices and arrays.
 */
object ArrayUtilityMethods {

    private val RANDOM = SecureRandom()

    /**
     * Method arraysSame compares 2 arrays [a1] and [a2] and returns whether their values
     * are equal (boolean).
     */
    @JvmStatic
    fun arraysSame(a1: IntArray, a2: IntArray): Boolean {
        // compares if 2 arrays have the same value
        if (a1.size != a2.size) {
            return false
        }
        for (i in a1.indices) {
            if (a1[i] != a2[i]) {
                return false
            }
        }
        return a1.isNotEmpty()
    }

    /**
     * Method matricesSame compares 2 matrices [m1] and [m2] and returns whether their
     * values are equal (boolean).
     */
    @JvmStatic
    fun matricesSame(m1: Array<IntArray>, m2: Array<IntArray>): Boolean {
        if (m1.size != m2.size) {
            return false
        }
        for (i in m1.indices) {
            if (!arraysSame(m1[i], m2[i])) {
                return false
            }
        }
        return m1.isNotEmpty()
    }

    /**
     * Method createRandomIntMatrix creates a random matrix of size [rows] and [columns].
     *
     * @return it (int[][]).
     */
    @JvmStatic
    fun createRandomIntMatrix(rows: Int, columns: Int): Array<IntArray> {
        val matrix = Array(rows) { IntArray(columns) }
        for (i in 0 until rows) {
            for (j in 0 until columns) {
                // filling cells in matrix
                matrix[i][j] = RANDOM.nextInt(10)
            }
        }
        return matrix
    }

    /**
     * Method printMatrix prints input matrix [matrix].
     */
    @JvmStatic
    fun printMatrix(matrix: Array<IntArray>) {
        // prints out int[][]
        for (ints in matrix) {
            for (j in matrix[0].indices) {
                logger.info { "${ints[j]} " }
            }
            logger.info { "" }
        }
    }
}
