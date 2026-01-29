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

// ABOUTME: Concrete master implementation for matrix transpose operations.
// ABOUTME: Creates ArrayTransposeWorkers and aggregates their transposed matrix results.
package com.iluwatar.masterworker.system.systemmaster

import com.iluwatar.masterworker.ArrayResult
import com.iluwatar.masterworker.system.systemworkers.ArrayTransposeWorker
import com.iluwatar.masterworker.system.systemworkers.Worker

/**
 * Class ArrayTransposeMaster extends abstract class [Master] and contains definition of
 * aggregateData, which will obtain final result from all data obtained and for setWorkers.
 */
class ArrayTransposeMaster(numOfWorkers: Int) : Master(numOfWorkers) {

    override fun setWorkers(num: Int): List<Worker> {
        // i+1 will be id
        return (0 until num).map { i -> ArrayTransposeWorker(this, i + 1) }
    }

    override fun aggregateData(): ArrayResult {
        // number of rows in final result is number of rows in any of obtained results from workers
        val rows = (allResultData.elements().nextElement() as ArrayResult).data.size
        val elements = allResultData.elements()
        var columns = 0 // columns = sum of number of columns in all results obtained from workers
        while (elements.hasMoreElements()) {
            columns += (elements.nextElement() as ArrayResult).data[0].size
        }
        val resultData = Array(rows) { IntArray(columns) }
        var columnsDone = 0 // columns aggregated so far
        for (i in 0 until expectedNumResults) {
            // result obtained from ith worker
            val worker = workers[i]
            val workerId = worker.workerId
            val work = (allResultData[workerId] as ArrayResult).data
            for (m in work.indices) {
                // m = row number, n = columns number
                System.arraycopy(work[m], 0, resultData[m], columnsDone, work[0].size)
            }
            columnsDone += work[0].size
        }
        return ArrayResult(resultData)
    }
}
