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
package com.iluwatar.typeobject

// ABOUTME: Tests the Cell class methods including interact and crush behavior.
// ABOUTME: Validates candy matching, point calculation, and matrix reorganization after crushing.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/** The CellTest class tests the methods in the [Cell] class. */
class CellTest {

  @Test
  fun interactTest() {
    val c1 = Candy("green jelly", "jelly", Candy.Type.CRUSHABLE_CANDY, 5)
    val c2 = Candy("green apple", "apple", Candy.Type.REWARD_FRUIT, 10)
    val matrix = Array(4) { Array(4) { Cell() } }
    matrix[0][0] = Cell(c1, 0, 0)
    matrix[0][1] = Cell(c1, 1, 0)
    matrix[0][2] = Cell(c2, 2, 0)
    matrix[0][3] = Cell(c1, 3, 0)
    val cp = CellPool(5)
    val points1 = matrix[0][0].interact(matrix[0][1], cp, matrix)
    val points2 = matrix[0][2].interact(matrix[0][3], cp, matrix)
    assertTrue(points1 > 0 && points2 == 0)
  }

  @Test
  fun crushTest() {
    val c1 = Candy("green jelly", "jelly", Candy.Type.CRUSHABLE_CANDY, 5)
    val c2 = Candy("purple candy", "candy", Candy.Type.CRUSHABLE_CANDY, 5)
    val matrix = Array(4) { Array(4) { Cell() } }
    matrix[0][0] = Cell(c1, 0, 0)
    matrix[1][0] = Cell(c2, 0, 1)
    matrix[1][0].crush(CellPool(5), matrix)
    assertEquals("green jelly", matrix[1][0].candy.name)
  }
}
