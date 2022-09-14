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

import java.security.SecureRandom;
import lombok.extern.slf4j.Slf4j;

/**
 * Class ArrayUtilityMethods has some utility methods for matrices and arrays.
 */

@Slf4j
public class ArrayUtilityMethods {

  private static final SecureRandom RANDOM = new SecureRandom();

  /**
   * Method arraysSame compares 2 arrays @param a1 and @param a2 and @return whether their values
   * are equal (boolean).
   */

  public static boolean arraysSame(int[] a1, int[] a2) {
    //compares if 2 arrays have the same value
    if (a1.length != a2.length) {
      return false;
    } else {
      var answer = false;
      for (var i = 0; i < a1.length; i++) {
        if (a1[i] == a2[i]) {
          answer = true;
        } else {
          answer = false;
          break;
        }
      }
      return answer;
    }
  }

  /**
   * Method matricesSame compares 2 matrices @param m1 and @param m2 and @return whether their
   * values are equal (boolean).
   */

  public static boolean matricesSame(int[][] m1, int[][] m2) {
    if (m1.length != m2.length) {
      return false;
    } else {
      var answer = false;
      for (var i = 0; i < m1.length; i++) {
        if (arraysSame(m1[i], m2[i])) {
          answer = true;
        } else {
          answer = false;
          break;
        }
      }
      return answer;
    }
  }

  /**
   * Method createRandomIntMatrix creates a random matrix of size @param rows and @param columns.
   *
   * @return it (int[][]).
   */
  public static int[][] createRandomIntMatrix(int rows, int columns) {
    var matrix = new int[rows][columns];
    for (var i = 0; i < rows; i++) {
      for (var j = 0; j < columns; j++) {
        //filling cells in matrix
        matrix[i][j] = RANDOM.nextInt(10);
      }
    }
    return matrix;
  }

  /**
   * Method printMatrix prints input matrix @param matrix.
   */

  public static void printMatrix(int[][] matrix) {
    //prints out int[][]
    for (var ints : matrix) {
      for (var j = 0; j < matrix[0].length; j++) {
        LOGGER.info(ints[j] + " ");
      }
      LOGGER.info("");
    }
  }

}
