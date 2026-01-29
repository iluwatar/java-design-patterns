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

// ABOUTME: Concrete implementation of Input for 2D integer arrays.
// ABOUTME: Handles division of matrix data for parallel processing by workers.
package com.iluwatar.masterworker

import java.util.Arrays

/**
 * Class ArrayInput extends abstract class [Input] and contains data of type int[][].
 */
class ArrayInput(data: Array<IntArray>) : Input<Array<IntArray>>(data) {

    override fun divideData(num: Int): List<Input<Array<IntArray>>> {
        val divisions = makeDivisions(data, num)
        val result = ArrayList<Input<Array<IntArray>>>(num)
        var rowsDone = 0 // number of rows divided so far
        for (i in 0 until num) {
            val rows = divisions[i]
            if (rows != 0) {
                val divided = Array(rows) { IntArray(data[0].size) }
                System.arraycopy(data, rowsDone, divided, 0, rows)
                rowsDone += rows
                val dividedInput = ArrayInput(divided)
                result.add(dividedInput)
            } else {
                break // rest of divisions will also be 0
            }
        }
        return result
    }

    companion object {
        @JvmStatic
        fun makeDivisions(data: Array<IntArray>, num: Int): IntArray {
            val initialDivision = data.size / num // equally dividing
            val divisions = IntArray(num)
            Arrays.fill(divisions, initialDivision)
            if (initialDivision * num != data.size) {
                var extra = data.size - initialDivision * num
                var l = 0
                // equally dividing extra among all parts
                while (extra > 0) {
                    divisions[l] = divisions[l] + 1
                    extra--
                    l = if (l == num - 1) 0 else l + 1
                }
            }
            return divisions
        }
    }
}
