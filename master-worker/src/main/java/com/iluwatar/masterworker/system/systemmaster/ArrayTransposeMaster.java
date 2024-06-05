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
package com.iluwatar.masterworker.system.systemmaster;

import com.iluwatar.masterworker.ArrayResult;
import com.iluwatar.masterworker.system.systemworkers.ArrayTransposeWorker;
import com.iluwatar.masterworker.system.systemworkers.Worker;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class ArrayTransposeMaster extends abstract class {@link Master} and contains definition of
 * aggregateData, which will obtain final result from all data obtained and for setWorkers.
 */

public class ArrayTransposeMaster extends Master {
  public ArrayTransposeMaster(int numOfWorkers) {
    super(numOfWorkers);
  }

  @Override
  ArrayList<Worker> setWorkers(int num) {
    //i+1 will be id
    return IntStream.range(0, num)
        .mapToObj(i -> new ArrayTransposeWorker(this, i + 1))
        .collect(Collectors.toCollection(() -> new ArrayList<>(num)));
  }

  @Override
  ArrayResult aggregateData() {
    // number of rows in final result is number of rows in any of obtained results from workers
    var allResultData = this.getAllResultData();
    var rows = ((ArrayResult) allResultData.elements().nextElement()).data.length;
    var elements = allResultData.elements();
    var columns = 0; // columns = sum of number of columns in all results obtained from workers
    while (elements.hasMoreElements()) {
      columns += ((ArrayResult) elements.nextElement()).data[0].length;
    }
    var resultData = new int[rows][columns];
    var columnsDone = 0; //columns aggregated so far
    var workers = this.getWorkers();
    for (var i = 0; i < this.getExpectedNumResults(); i++) {
      //result obtained from ith worker
      var worker = workers.get(i);
      var workerId = worker.getWorkerId();
      var work = ((ArrayResult) allResultData.get(workerId)).data;
      for (var m = 0; m < work.length; m++) {
        //m = row number, n = columns number
        System.arraycopy(work[m], 0, resultData[m], columnsDone, work[0].length);
      }
      columnsDone += work[0].length;
    }
    return new ArrayResult(resultData);
  }

}
