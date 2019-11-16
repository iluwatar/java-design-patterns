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

import com.iluwatar.masterworker.Input;
import com.iluwatar.masterworker.Result;
import com.iluwatar.masterworker.system.systemworkers.Worker;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * The abstract Master class which contains private fields numOfWorkers (number of workers), workers
 * (arraylist of workers), expectedNumResults (number of divisions of input data, same as expected
 * number of results), allResultData (hashtable of results obtained from workers, mapped by their
 * ids) and finalResult (aggregated from allResultData).
 */

public abstract class Master {
  private final int numOfWorkers;
  private final ArrayList<Worker> workers;
  private int expectedNumResults;
  private Hashtable<Integer, Result> allResultData;
  private Result finalResult;

  Master(int numOfWorkers) {
    this.numOfWorkers = numOfWorkers;
    this.workers = setWorkers(numOfWorkers);
    this.expectedNumResults = 0;
    this.allResultData = new Hashtable<Integer, Result>(numOfWorkers);
    this.finalResult = null;
  }

  public Result getFinalResult() {
    return this.finalResult;
  }

  Hashtable<Integer, Result> getAllResultData() {
    return this.allResultData;
  }

  int getExpectedNumResults() {
    return this.expectedNumResults;
  }

  ArrayList<Worker> getWorkers() {
    return this.workers;
  }

  abstract ArrayList<Worker> setWorkers(int num);

  public void doWork(Input input) {
    divideWork(input);
  }

  private void divideWork(Input input) {
    ArrayList<Input> dividedInput = input.divideData(numOfWorkers);
    if (dividedInput != null) {
      this.expectedNumResults = dividedInput.size();
      for (int i = 0; i < this.expectedNumResults; i++) {
        //ith division given to ith worker in this.workers
        this.workers.get(i).setReceivedData(this, dividedInput.get(i));
        this.workers.get(i).run();
      }
    }
  }

  public void receiveData(Result data, Worker w) {
    //check if can receive..if yes:
    collectResult(data, w.getWorkerId());
  }

  private void collectResult(Result data, int workerId) {
    this.allResultData.put(workerId, data);
    if (this.allResultData.size() == this.expectedNumResults) {
      //all data received
      this.finalResult = aggregateData();
    }
  }

  abstract Result aggregateData();
}
