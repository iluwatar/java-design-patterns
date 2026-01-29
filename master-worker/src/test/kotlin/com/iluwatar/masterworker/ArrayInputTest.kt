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

// ABOUTME: Tests for ArrayInput class data division functionality.
// ABOUTME: Verifies that matrix data is correctly split among workers.
package com.iluwatar.masterworker

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.Random

/**
 * Testing divideData method in [ArrayInput] class.
 */
class ArrayInputTest {

    @Test
    fun divideDataTest() {
        val rows = 10
        val columns = 10
        val inputMatrix = Array(rows) { IntArray(columns) }
        val rand = Random()
        for (i in 0 until rows) {
            for (j in 0 until columns) {
                inputMatrix[i][j] = rand.nextInt(10)
            }
        }
        val input = ArrayInput(inputMatrix)
        val table = input.divideData(4)
        val division1 = arrayOf(inputMatrix[0], inputMatrix[1], inputMatrix[2])
        val division2 = arrayOf(inputMatrix[3], inputMatrix[4], inputMatrix[5])
        val division3 = arrayOf(inputMatrix[6], inputMatrix[7])
        val division4 = arrayOf(inputMatrix[8], inputMatrix[9])
        assertTrue(
            ArrayUtilityMethods.matricesSame(table[0].data, division1) &&
                ArrayUtilityMethods.matricesSame(table[1].data, division2) &&
                ArrayUtilityMethods.matricesSame(table[2].data, division3) &&
                ArrayUtilityMethods.matricesSame(table[3].data, division4)
        )
    }
}
