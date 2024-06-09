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
package com.iluwatar.masterworker.system.systemworkers;

import com.iluwatar.masterworker.ArrayInput;
import com.iluwatar.masterworker.ArrayResult;
import com.iluwatar.masterworker.system.systemmaster.Master;

/**
 * Class ArrayTransposeWorker extends abstract class {@link Worker} and defines method
 * executeOperation(), to be performed on data received from master.
 */

public class ArrayTransposeWorker extends Worker {

  public ArrayTransposeWorker(Master master, int id) {
    super(master, id);
  }

  @Override
  ArrayResult executeOperation() {
    //number of rows in result matrix is equal to number of columns in input matrix and vice versa
    var arrayInput = (ArrayInput) this.getReceivedData();
    final var rows = arrayInput.data[0].length;
    final var cols = arrayInput.data.length;
    var resultData = new int[rows][cols];
    for (var i = 0; i < cols; i++) {
      for (var j = 0; j < rows; j++) {
        //flipping element positions along diagonal
        resultData[j][i] = arrayInput.data[i][j];
      }
    }
    return new ArrayResult(resultData);
  }
}
