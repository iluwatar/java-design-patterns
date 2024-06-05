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
package com.iluwatar.typeobject;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>Type object pattern is the pattern we use when the OOP concept of creating a base class and
 * inheriting from it just doesn't work for the case in hand. This happens when we either don't know
 * what types we will need upfront, or want to be able to modify or add new types conveniently w/o
 * recompiling repeatedly. The pattern provides a solution by allowing flexible creation of required
 * objects by creating one class, which has a field which represents the 'type' of the object.</p>
 * <p>In this example, we have a mini candy-crush game in action. There are many different candies
 * in the game, which may change over time, as we may want to upgrade the game. To make the object
 * creation convenient, we have a class {@link Candy} which has a field name, parent, points and
 * Type. We have a json file {@link candy} which contains the details about the candies, and this is
 * parsed to get all the different candies in {@link JsonParser}. The {@link Cell} class is what the
 * game matrix is made of, which has the candies that are to be crushed, and contains information on
 * how crushing can be done, how the matrix is to be reconfigured and how points are to be gained.
 * The {@link CellPool} class is a pool which reuses the candy cells that have been crushed instead
 * of making new ones repeatedly. The {@link CandyGame} class has the rules for the continuation of
 * the game and the {@link App} class has the game itself.</p>
 */

@Slf4j
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    var givenTime = 50; //50ms
    var toWin = 500; //points
    var pointsWon = 0;
    var numOfRows = 3;
    var start = System.currentTimeMillis();
    var end = System.currentTimeMillis();
    var round = 0;
    while (pointsWon < toWin && end - start < givenTime) {
      round++;
      var pool = new CellPool(numOfRows * numOfRows + 5);
      var cg = new CandyGame(numOfRows, pool);
      if (round > 1) {
        LOGGER.info("Refreshing..");
      } else {
        LOGGER.info("Starting game..");
      }
      cg.printGameStatus();
      end = System.currentTimeMillis();
      cg.round((int) (end - start), givenTime);
      pointsWon += cg.totalPoints;
      end = System.currentTimeMillis();
    }
    LOGGER.info("Game Over");
    if (pointsWon >= toWin) {
      LOGGER.info("" + pointsWon);
      LOGGER.info("You win!!");
    } else {
      LOGGER.info("" + pointsWon);
      LOGGER.info("Sorry, you lose!");
    }
  }
}
