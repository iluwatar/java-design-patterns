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

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class ArrayInput extends abstract class {@link Input} and contains data of type int[][].
 */

public class ArrayInput extends Input<int[][]> {

  public ArrayInput(int[][] data) {
    super(data);
  }

  static int[] makeDivisions(int[][] data, int num) {
    int initialDivision = data.length / num; //equally dividing
    int[] divisions = new int[num];
    Arrays.fill(divisions, initialDivision);
    if (initialDivision * num != data.length) {
      int extra = data.length - initialDivision * num;
      int l = 0;
      //equally dividing extra among all parts
      while (extra > 0) {
        divisions[l] = divisions[l] + 1;
        extra--;
        if (l == num - 1) {
          l = 0;
        } else {
          l++;
        }
      }
    }
    return divisions;
  }

  @Override
  public ArrayList<Input> divideData(int num) {
    if (this.data == null) {
      return null;
    } else {
      int[] divisions = makeDivisions(this.data, num);
      ArrayList<Input> result = new ArrayList<Input>(num);
      int rowsDone = 0; //number of rows divided so far
      for (int i = 0; i < num; i++) {
        int rows = divisions[i];
        if (rows != 0) {
          int[][] divided = new int[rows][this.data[0].length];
          for (int j = 0; j < rows; j++) {
            divided[j] = this.data[rowsDone + j];
          }
          rowsDone += rows;
          ArrayInput dividedInput = new ArrayInput(divided);
          result.add(dividedInput);
        } else {
          break; //rest of divisions will also be 0
        }
      }
      return result;
    }
  }
}
