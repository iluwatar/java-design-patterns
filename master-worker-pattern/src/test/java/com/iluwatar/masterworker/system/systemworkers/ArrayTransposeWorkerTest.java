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

package com.iluwatar.masterworker.system.systemworkers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.iluwatar.masterworker.ArrayUtilityMethods;
import com.iluwatar.masterworker.ArrayInput;
import com.iluwatar.masterworker.ArrayResult;
import com.iluwatar.masterworker.system.systemmaster.ArrayTransposeMaster;

/**
* Testing executeOperation method in {@link ArrayTransposeWorker} class.
*/

class ArrayTransposeWorkerTest {

  @Test
  void executeOperationTest() {
    ArrayTransposeMaster atm = new ArrayTransposeMaster(1);
    ArrayTransposeWorker atw = new ArrayTransposeWorker(atm, 1);
    int[][] matrix = new int[][] {{2,4}, {3,5}};
    int[][] matrixTranspose = new int[][] {{2,3}, {4,5}};
    ArrayInput i = new ArrayInput(matrix);
    atw.setReceivedData(atm, i);
    ArrayResult r = atw.executeOperation();
    assertTrue(ArrayUtilityMethods.matricesSame(r.data, matrixTranspose));
  }
  
}
