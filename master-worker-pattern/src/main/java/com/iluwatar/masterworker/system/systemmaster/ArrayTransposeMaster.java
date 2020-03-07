/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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
import java.util.Enumeration;

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
    ArrayList<Worker> ws = new ArrayList<Worker>(num);
    for (int i = 0; i < num; i++) {
      ws.add(new ArrayTransposeWorker(this, i + 1));
      //i+1 will be id
    }
    return ws;
  }

  @Override
  ArrayResult aggregateData() {
    // number of rows in final result is number of rows in any of obtained results from workers
    int rows = ((ArrayResult) this.getAllResultData()
        .get(this.getAllResultData().keys().nextElement())).data.length;
    int columns =
        0; //number of columns is sum of number of columns in all results obtained from workers
    for (Enumeration<Integer> e = this.getAllResultData().keys(); e.hasMoreElements(); ) {
      columns += ((ArrayResult) this.getAllResultData().get(e.nextElement())).data[0].length;
    }
    int[][] resultData = new int[rows][columns];
    int columnsDone = 0; //columns aggregated so far
    for (int i = 0; i < this.getExpectedNumResults(); i++) {
      //result obtained from ith worker
      int[][] work =
          ((ArrayResult) this.getAllResultData().get(this.getWorkers().get(i).getWorkerId())).data;
      for (int m = 0; m < work.length; m++) {
        //m = row number, n = columns number
        for (int n = 0; n < work[0].length; n++) {
          resultData[m][columnsDone + n] = work[m][n];
        }
      }
      columnsDone += work[0].length;
    }
    return new ArrayResult(resultData);
  }

}
