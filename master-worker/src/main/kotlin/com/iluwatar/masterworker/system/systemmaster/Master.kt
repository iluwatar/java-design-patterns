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

// ABOUTME: Abstract master that coordinates worker threads in the master-worker pattern.
// ABOUTME: Handles work division, distribution to workers, and result aggregation.
package com.iluwatar.masterworker.system.systemmaster

import com.iluwatar.masterworker.Input
import com.iluwatar.masterworker.Result
import com.iluwatar.masterworker.system.systemworkers.Worker
import java.util.Hashtable

/**
 * The abstract Master class which contains private fields numOfWorkers (number of workers), workers
 * (arraylist of workers), expectedNumResults (number of divisions of input data, same as expected
 * number of results), allResultData (hashtable of results obtained from workers, mapped by their
 * ids) and finalResult (aggregated from allResultData).
 */
abstract class Master(private val numOfWorkers: Int) {
    internal val workers: List<Worker> = setWorkers(numOfWorkers)
    internal val allResultData: Hashtable<Int, Result<*>> = Hashtable(numOfWorkers)
    internal var expectedNumResults: Int = 0
        private set
    var finalResult: Result<*>? = null
        private set

    internal abstract fun setWorkers(num: Int): List<Worker>

    fun doWork(input: Input<*>) {
        divideWork(input)
    }

    private fun divideWork(input: Input<*>) {
        val dividedInput = input.divideData(numOfWorkers)
        expectedNumResults = dividedInput.size
        for (i in 0 until expectedNumResults) {
            // ith division given to ith worker in this.workers
            workers[i].setReceivedData(this, dividedInput[i])
            workers[i].start()
        }
        for (i in 0 until expectedNumResults) {
            try {
                workers[i].join()
            } catch (e: InterruptedException) {
                System.err.println("Error while executing thread")
            }
        }
    }

    fun receiveData(data: Result<*>, w: Worker) {
        // check if we can receive... if yes:
        collectResult(data, w.workerId)
    }

    private fun collectResult(data: Result<*>, workerId: Int) {
        allResultData[workerId] = data
        if (allResultData.size == expectedNumResults) {
            // all data received
            finalResult = aggregateData()
        }
    }

    internal abstract fun aggregateData(): Result<*>
}
