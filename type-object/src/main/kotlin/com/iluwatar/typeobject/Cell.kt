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

// ABOUTME: Represents a cell in the game matrix, holding a candy to be crushed or collected.
// ABOUTME: Contains logic for crushing, filling spaces, and interacting with adjacent cells.

/**
 * The Cell object is what the game matrix is made of and contains the candy which is to be crushed
 * or collected as reward.
 */
class Cell(
  var candy: Candy,
  var positionX: Int,
  var positionY: Int
) {

  constructor() : this(
    candy = Candy("", "", Candy.Type.CRUSHABLE_CANDY, 0),
    positionX = 0,
    positionY = 0
  )

  fun crush(pool: CellPool, cellMatrix: Array<Array<Cell>>) {
    // take out from this position and put back in pool
    pool.addNewCell(this)
    fillThisSpace(pool, cellMatrix)
  }

  fun fillThisSpace(pool: CellPool, cellMatrix: Array<Array<Cell>>) {
    for (y in positionY downTo 1) {
      cellMatrix[y][positionX] = cellMatrix[y - 1][positionX]
      cellMatrix[y][positionX].positionY = y
    }
    val newC = pool.getNewCell()
    cellMatrix[0][positionX] = newC
    cellMatrix[0][positionX].positionX = positionX
    cellMatrix[0][positionX].positionY = 0
  }

  fun handleCrush(c: Cell, pool: CellPool, cellMatrix: Array<Array<Cell>>) {
    if (positionY >= c.positionY) {
      crush(pool, cellMatrix)
      c.crush(pool, cellMatrix)
    } else {
      c.crush(pool, cellMatrix)
      crush(pool, cellMatrix)
    }
  }

  fun interact(c: Cell, pool: CellPool, cellMatrix: Array<Array<Cell>>): Int {
    if (candy.type == Candy.Type.REWARD_FRUIT || c.candy.type == Candy.Type.REWARD_FRUIT) {
      return 0
    }
    return if (candy.name == c.candy.name) {
      val pointsWon = candy.points + c.candy.points
      handleCrush(c, pool, cellMatrix)
      pointsWon
    } else {
      0
    }
  }
}
