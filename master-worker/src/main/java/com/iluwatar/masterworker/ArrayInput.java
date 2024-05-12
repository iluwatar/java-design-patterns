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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class ArrayInput extends abstract class {@link Input} and contains data of type int[][].
 */

public class ArrayInput extends Input<int[][]> {

  public ArrayInput(int[][] data) {
    super(data);
  }

  static int[] makeDivisions(int[][] data, int num) {
    var initialDivision = data.length / num; //equally dividing
    var divisions = new int[num];
    Arrays.fill(divisions, initialDivision);
    if (initialDivision * num != data.length) {
      var extra = data.length - initialDivision * num;
      var l = 0;
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
  public List<Input<int[][]>> divideData(int num) {
    if (this.data == null) {
      return null;
    } else {
      var divisions = makeDivisions(this.data, num);
      var result = new ArrayList<Input<int[][]>>(num);
      var rowsDone = 0; //number of rows divided so far
      for (var i = 0; i < num; i++) {
        var rows = divisions[i];
        if (rows != 0) {
          var divided = new int[rows][this.data[0].length];
          System.arraycopy(this.data, rowsDone, divided, 0, rows);
          rowsDone += rows;
          var dividedInput = new ArrayInput(divided);
          result.add(dividedInput);
        } else {
          break; //rest of divisions will also be 0
        }
      }
      return result;
    }
  }
}
