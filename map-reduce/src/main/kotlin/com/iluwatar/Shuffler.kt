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

// ABOUTME: Shuffler component of the MapReduce pattern that groups word occurrences.
// ABOUTME: Merges multiple word count maps into a single grouped map for reduction.
package com.iluwatar

/**
 * The Shuffler object is responsible for grouping word occurrences from multiple mappers.
 */
object Shuffler {

    /**
     * Merges multiple word count maps into a single grouped map.
     *
     * @param mapped List of maps containing word counts from the mapping phase.
     * @return A map where keys are words and values are lists of their occurrences across inputs.
     */
    fun shuffleAndSort(mapped: List<Map<String, Int>>): Map<String, List<Int>> {
        val grouped = mutableMapOf<String, MutableList<Int>>()
        for (map in mapped) {
            for ((key, value) in map) {
                grouped.getOrPut(key) { mutableListOf() }.add(value)
            }
        }
        return grouped
    }
}
