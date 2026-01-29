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

// ABOUTME: Contains the game rules and matrix for the candy-crush style game.
// ABOUTME: Manages cell interactions, round progression, and point accumulation.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * The CandyGame class contains the rules for the continuation of the game and has the game matrix
 * (field 'cells') and totalPoints gained during the game.
 */
class CandyGame(num: Int, val pool: CellPool) {
  var cells: Array<Array<Cell>> = Array(num) { i ->
    Array(num) { j ->
      pool.getNewCell().also {
        it.positionX = j
        it.positionY = i
      }
    }
  }
  var totalPoints: Int = 0

  fun printGameStatus() {
    logger.info { "" }
    for (row in cells) {
      for (j in cells.indices) {
        val candyName = row[j].candy.name
        if (candyName.length < 20) {
          val totalSpaces = 20 - candyName.length
          logger.info {
            "${numOfSpaces(totalSpaces / 2)}${row[j].candy.name}${numOfSpaces(totalSpaces - totalSpaces / 2)}|"
          }
        } else {
          logger.info { "$candyName|" }
        }
      }
      logger.info { "" }
    }
    logger.info { "" }
  }

  fun adjacentCells(y: Int, x: Int): List<Cell> {
    val adjacent = mutableListOf<Cell>()
    if (y == 0) {
      adjacent.add(cells[1][x])
    }
    if (x == 0) {
      adjacent.add(cells[y][1])
    }
    if (y == cells.size - 1 && cells.size > 1) {
      adjacent.add(cells[cells.size - 2][x])
    }
    if (x == cells.size - 1 && cells.size > 1) {
      adjacent.add(cells[y][cells.size - 2])
    }
    if (y in 1 until cells.size - 1) {
      adjacent.add(cells[y - 1][x])
      adjacent.add(cells[y + 1][x])
    }
    if (y in cells.indices && x in 1 until cells[y].size - 1) {
      adjacent.add(cells[y][x - 1])
      adjacent.add(cells[y][x + 1])
    }
    return adjacent
  }

  fun continueRound(): Boolean {
    for (i in cells.indices) {
      if (cells[cells.size - 1][i].candy.type == Candy.Type.REWARD_FRUIT) {
        return true
      }
    }
    for (i in cells.indices) {
      for (j in cells.indices) {
        if (cells[i][j].candy.type != Candy.Type.REWARD_FRUIT) {
          val adj = adjacentCells(i, j)
          for (cell in adj) {
            if (cells[i][j].candy.name == cell.candy.name) {
              return true
            }
          }
        }
      }
    }
    return false
  }

  fun handleChange(points: Int) {
    logger.info { "+$points points!" }
    totalPoints += points
    printGameStatus()
  }

  @Suppress("kotlin:S3776") // "Cognitive Complexity of methods should not be too high"
  fun round(timeSoFar: Int, totalTime: Int) {
    val start = System.currentTimeMillis()
    var end = System.currentTimeMillis()
    while (end - start + timeSoFar < totalTime && continueRound()) {
      for (i in cells.indices) {
        var j = cells.size - 1
        while (cells[j][i].candy.type == Candy.Type.REWARD_FRUIT) {
          val points = cells[j][i].candy.points
          cells[j][i].crush(pool, cells)
          handleChange(points)
        }
      }
      for (i in cells.indices) {
        var j = cells.size - 1
        while (j > 0) {
          val points = cells[j][i].interact(cells[j - 1][i], pool, cells)
          if (points != 0) {
            handleChange(points)
          } else {
            j -= 1
          }
        }
      }
      for (row in cells) {
        var j = 0
        while (j < cells.size - 1) {
          val points = row[j].interact(row[j + 1], pool, cells)
          if (points != 0) {
            handleChange(points)
          } else {
            j += 1
          }
        }
      }
      end = System.currentTimeMillis()
    }
  }

  companion object {
    fun numOfSpaces(num: Int): String = " ".repeat(maxOf(0, num))
  }
}
