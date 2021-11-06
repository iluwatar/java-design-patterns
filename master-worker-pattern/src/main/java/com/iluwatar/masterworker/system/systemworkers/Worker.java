/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.masterworker.system.systemworkers;

import com.iluwatar.masterworker.Input;
import com.iluwatar.masterworker.Result;
import com.iluwatar.masterworker.system.systemmaster.Master;

/**
 * The abstract Worker class which extends Thread class to enable parallel processing. Contains
 * fields master(holding reference to master), workerId (unique id) and receivedData(from master).
 */

public abstract class Worker extends Thread {
  private final Master master;
  private final int workerId;
  private Input<?> receivedData;

  Worker(Master master, int id) {
    this.master = master;
    this.workerId = id;
    this.receivedData = null;
  }

  public int getWorkerId() {
    return this.workerId;
  }

  Input<?> getReceivedData() {
    return this.receivedData;
  }

  public void setReceivedData(Master m, Input<?> i) {
    //check if ready to receive..if yes:
    this.receivedData = i;
  }

  abstract Result<?> executeOperation();

  private void sendToMaster(Result<?> data) {
    this.master.receiveData(data, this);
  }

  public void run() { //from Thread class
    var work = executeOperation();
    sendToMaster(work);
  }
}
