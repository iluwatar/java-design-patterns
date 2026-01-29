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

// ABOUTME: Tests the CandyGame class methods including adjacentCells and continueRound logic.
// ABOUTME: Validates game matrix navigation and round continuation conditions.

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/** The CandyGameTest class tests the methods in the [CandyGame] class. */
class CandyGameTest {

  @Test
  fun adjacentCellsTest() {
    val cg = CandyGame(3, CellPool(9))
    val arr1 = cg.adjacentCells(0, 0)
    val arr2 = cg.adjacentCells(1, 2)
    val arr3 = cg.adjacentCells(1, 1)
    assertTrue(arr1.size == 2 && arr2.size == 3 && arr3.size == 4)
  }

  @Test
  fun continueRoundTest() {
    val matrix = Array(2) { Array(2) { Cell() } }
    val c1 = Candy("green jelly", "jelly", Candy.Type.CRUSHABLE_CANDY, 5)
    val c2 = Candy("purple jelly", "jelly", Candy.Type.CRUSHABLE_CANDY, 5)
    val c3 = Candy("green apple", "apple", Candy.Type.REWARD_FRUIT, 10)
    matrix[0][0] = Cell(c1, 0, 0)
    matrix[0][1] = Cell(c2, 1, 0)
    matrix[1][0] = Cell(c3, 0, 1)
    matrix[1][1] = Cell(c2, 1, 1)
    val p = CellPool(4)
    val cg = CandyGame(2, p)
    cg.cells = matrix
    val fruitInLastRow = cg.continueRound()
    matrix[1][0].crush(p, matrix)
    matrix[0][0] = Cell(c3, 0, 0)
    val matchingCandy = cg.continueRound()
    matrix[0][1].crush(p, matrix)
    matrix[0][1] = Cell(c3, 1, 0)
    val noneLeft = cg.continueRound()
    assertTrue(fruitInLastRow && matchingCandy && !noneLeft)
  }
}
