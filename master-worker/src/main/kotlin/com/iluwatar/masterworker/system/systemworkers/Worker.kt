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

// ABOUTME: Abstract worker thread for parallel processing in master-worker pattern.
// ABOUTME: Extends Thread to process data independently and return results to master.
package com.iluwatar.masterworker.system.systemworkers

import com.iluwatar.masterworker.Input
import com.iluwatar.masterworker.Result
import com.iluwatar.masterworker.system.systemmaster.Master

/**
 * The abstract Worker class which extends Thread class to enable parallel processing. Contains
 * fields master(holding reference to master), workerId (unique id) and receivedData(from master).
 */
abstract class Worker(
    private val master: Master,
    val workerId: Int
) : Thread() {
    internal var receivedData: Input<*>? = null
        private set

    fun setReceivedData(m: Master, i: Input<*>) {
        // check if we are ready to receive... if yes:
        this.receivedData = i
    }

    internal abstract fun executeOperation(): Result<*>

    private fun sendToMaster(data: Result<*>) {
        master.receiveData(data, this)
    }

    override fun run() { // from Thread class
        val work = executeOperation()
        sendToMaster(work)
    }
}
