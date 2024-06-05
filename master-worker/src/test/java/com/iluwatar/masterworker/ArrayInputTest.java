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
package com.iluwatar.masterworker;

import static com.iluwatar.masterworker.ArrayUtilityMethods.matricesSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Testing divideData method in {@link ArrayInput} class.
 */

class ArrayInputTest {

  @Test
  void divideDataTest() {
    var rows = 10;
    var columns = 10;
    var inputMatrix = new int[rows][columns];
    var rand = new Random();
    for (var i = 0; i < rows; i++) {
      for (var j = 0; j < columns; j++) {
        inputMatrix[i][j] = rand.nextInt(10);
      }
    }
    var i = new ArrayInput(inputMatrix);
    var table = i.divideData(4);
    var division1 = new int[][]{inputMatrix[0], inputMatrix[1], inputMatrix[2]};
    var division2 = new int[][]{inputMatrix[3], inputMatrix[4], inputMatrix[5]};
    var division3 = new int[][]{inputMatrix[6], inputMatrix[7]};
    var division4 = new int[][]{inputMatrix[8], inputMatrix[9]};
    assertTrue(matricesSame(table.get(0).data, division1)
        && matricesSame(table.get(1).data, division2)
        && matricesSame(table.get(2).data, division3)
        && matricesSame(table.get(3).data, division4));
  }

}
