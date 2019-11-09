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

package com.iluwatar.masterworker;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
* Testing divideData method in {@link ArrayInput} class.
*/

class ArrayInputTest {

  @Test
  void divideDataTest() {
    int rows = 10;
    int columns = 10;
    int[][] inputMatrix = new int[rows][columns];
    Random rand = new Random();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        inputMatrix[i][j] = rand.nextInt(10);
      }
    }
    ArrayInput i = new ArrayInput(inputMatrix);
    ArrayList<Input> table = i.divideData(4);
    int[][] division1 = new int[][] {inputMatrix[0], inputMatrix[1], inputMatrix[2]};
    int[][] division2 = new int[][] {inputMatrix[3], inputMatrix[4], inputMatrix[5]};
    int[][] division3 = new int[][] {inputMatrix[6], inputMatrix[7]};
    int[][] division4 = new int[][] {inputMatrix[8], inputMatrix[9]};
    assertTrue(ArrayUtilityMethods.matricesSame((int[][]) table.get(0).data, division1) 
            && ArrayUtilityMethods.matricesSame((int[][]) table.get(1).data, division2)
            && ArrayUtilityMethods.matricesSame((int[][]) table.get(2).data, division3) 
            && ArrayUtilityMethods.matricesSame((int[][]) table.get(3).data, division4));
  }

}
