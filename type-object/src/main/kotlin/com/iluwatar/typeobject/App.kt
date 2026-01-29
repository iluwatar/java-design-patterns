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

// ABOUTME: Entry point for the type-object pattern demo running a mini candy-crush game.
// ABOUTME: Demonstrates flexible object creation via type objects parsed from JSON configuration.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Type object pattern is the pattern we use when the OOP concept of creating a base class and
 * inheriting from it just doesn't work for the case in hand. This happens when we either don't know
 * what types we will need upfront, or want to be able to modify or add new types conveniently w/o
 * recompiling repeatedly. The pattern provides a solution by allowing flexible creation of required
 * objects by creating one class, which has a field which represents the 'type' of the object. In
 * this example, we have a mini candy-crush game in action. There are many different candies in the
 * game, which may change over time, as we may want to upgrade the game. To make the object creation
 * convenient, we have a class [Candy] which has a field name, parent, points and Type. We
 * have a json file which contains the details about the candies, and this is parsed
 * to get all the different candies in [JsonParser]. The [Cell] class is what the game
 * matrix is made of, which has the candies that are to be crushed, and contains information on how
 * crushing can be done, how the matrix is to be reconfigured and how points are to be gained. The
 * [CellPool] class is a pool which reuses the candy cells that have been crushed instead of
 * making new ones repeatedly. The [CandyGame] class has the rules for the continuation of the
 * game and the [main] function has the game itself.
 */
fun main() {
  val givenTime = 50 // 50ms
  val toWin = 500 // points
  var pointsWon = 0
  val numOfRows = 3
  val start = System.currentTimeMillis()
  var end = System.currentTimeMillis()
  var round = 0
  while (pointsWon < toWin && end - start < givenTime) {
    round++
    val pool = CellPool(numOfRows * numOfRows + 5)
    val cg = CandyGame(numOfRows, pool)
    if (round > 1) {
      logger.info { "Refreshing.." }
    } else {
      logger.info { "Starting game.." }
    }
    cg.printGameStatus()
    end = System.currentTimeMillis()
    cg.round((end - start).toInt(), givenTime)
    pointsWon += cg.totalPoints
    end = System.currentTimeMillis()
  }
  logger.info { "Game Over" }
  if (pointsWon >= toWin) {
    logger.info { "$pointsWon" }
    logger.info { "You win!!" }
  } else {
    logger.info { "$pointsWon" }
    logger.info { "Sorry, you lose!" }
  }
}
