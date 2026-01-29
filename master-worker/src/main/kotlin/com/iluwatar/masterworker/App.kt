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

// ABOUTME: Entry point demonstrating the Master-Worker pattern for matrix transpose.
// ABOUTME: Creates workers that process matrix segments in parallel and aggregates results.
package com.iluwatar.masterworker

import com.iluwatar.masterworker.system.ArrayTransposeMasterWorker
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * The **Master-Worker** pattern is used when the problem at hand can be solved by
 * dividing into multiple parts which need to go through the same computation and may need to be
 * aggregated to get final result. Parallel processing is performed using a system consisting of a
 * master and some number of workers, where a master divides the work among the workers, gets the
 * result back from them and assimilates all the results to give final result. The only
 * communication is between the master and the worker - none of the workers communicate among one
 * another and the user only communicates with the master to get required job done.
 *
 * In our example, we have generic abstract classes [com.iluwatar.masterworker.system.MasterWorker],
 * [com.iluwatar.masterworker.system.systemmaster.Master] and
 * [com.iluwatar.masterworker.system.systemworkers.Worker] which have to be extended by the classes
 * which will perform the specific job at hand (in this case finding transpose of matrix, done by
 * [ArrayTransposeMasterWorker],
 * [com.iluwatar.masterworker.system.systemmaster.ArrayTransposeMaster] and
 * [com.iluwatar.masterworker.system.systemworkers.ArrayTransposeWorker]). The Master class divides
 * the work into parts to be given to the workers, collects the results from the workers and
 * aggregates it when all workers have responded before returning the solution. The Worker class
 * extends the Thread class to enable parallel processing, and does the work once the data has been
 * received from the Master. The MasterWorker contains a reference to the Master class, gets the
 * input from the App and passes it on to the Master. These 3 classes define the system which
 * computes the result. We also have 2 abstract classes [Input] and [Result], which contain the
 * input data and result data respectively. The Input class also has an abstract method divideData
 * which defines how the data is to be divided into segments. These classes are extended by
 * [ArrayInput] and [ArrayResult].
 */
fun main() {
    val mw = ArrayTransposeMasterWorker()
    val rows = 10
    val columns = 20
    val inputMatrix = ArrayUtilityMethods.createRandomIntMatrix(rows, columns)
    val input = ArrayInput(inputMatrix)
    val result = mw.getResult(input) as? ArrayResult
    if (result != null) {
        ArrayUtilityMethods.printMatrix(inputMatrix)
        ArrayUtilityMethods.printMatrix(result.data)
    } else {
        logger.info { "Please enter non-zero input" }
    }
}
