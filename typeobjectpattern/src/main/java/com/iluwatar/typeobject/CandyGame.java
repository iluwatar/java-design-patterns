/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

import com.iluwatar.typeobject.Candy.Type;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * The CandyGame class contains the rules for the continuation of the game and has the game matrix
 * (field 'cells') and totalPoints gained during the game.
 */

@Slf4j
public class CandyGame {

  Cell[][] cells;
  CellPool pool;
  int totalPoints;

  CandyGame(int num, CellPool pool) {
    this.cells = new Cell[num][num];
    this.pool = pool;
    this.totalPoints = 0;
    for (var i = 0; i < num; i++) {
      for (var j = 0; j < num; j++) {
        this.cells[i][j] = this.pool.getNewCell();
        this.cells[i][j].positionX = j;
        this.cells[i][j].positionY = i;
      }
    }
  }

  static String numOfSpaces(int num) {
    return " ".repeat(Math.max(0, num));
  }

  void printGameStatus() {
    LOGGER.info("");
    for (Cell[] cell : cells) {
      for (var j = 0; j < cells.length; j++) {
        var candyName = cell[j].candy.name;
        if (candyName.length() < 20) {
          var totalSpaces = 20 - candyName.length();
          LOGGER.info(numOfSpaces(totalSpaces / 2) + cell[j].candy.name
              + numOfSpaces(totalSpaces - totalSpaces / 2) + "|");
        } else {
          LOGGER.info(candyName + "|");
        }
      }
      LOGGER.info("");
    }
    LOGGER.info("");
  }

  List<Cell> adjacentCells(int y, int x) {
    var adjacent = new ArrayList<Cell>();
    if (y == 0) {
      adjacent.add(this.cells[1][x]);
    }
    if (x == 0) {
      adjacent.add(this.cells[y][1]);
    }
    if (y == cells.length - 1) {
      adjacent.add(this.cells[cells.length - 2][x]);
    }
    if (x == cells.length - 1) {
      adjacent.add(this.cells[y][cells.length - 2]);
    }
    if (y > 0 && y < cells.length - 1) {
      adjacent.add(this.cells[y - 1][x]);
      adjacent.add(this.cells[y + 1][x]);
    }
    if (x > 0 && x < cells.length - 1) {
      adjacent.add(this.cells[y][x - 1]);
      adjacent.add(this.cells[y][x + 1]);
    }
    return adjacent;
  }

  boolean continueRound() {
    for (var i = 0; i < this.cells.length; i++) {
      if (this.cells[cells.length - 1][i].candy.getType().equals(Type.REWARD_FRUIT)) {
        return true;
      }
    }
    for (var i = 0; i < this.cells.length; i++) {
      for (var j = 0; j < this.cells.length; j++) {
        if (!this.cells[i][j].candy.getType().equals(Type.REWARD_FRUIT)) {
          var adj = adjacentCells(i, j);
          for (Cell cell : adj) {
            if (this.cells[i][j].candy.name.equals(cell.candy.name)) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  void handleChange(int points) {
    LOGGER.info("+" + points + " points!");
    this.totalPoints += points;
    printGameStatus();
  }

  void round(int timeSoFar, int totalTime) {
    var start = System.currentTimeMillis();
    var end = System.currentTimeMillis();
    while (end - start + timeSoFar < totalTime && continueRound()) {
      for (var i = 0; i < this.cells.length; i++) {
        var points = 0;
        var j = this.cells.length - 1;
        while (this.cells[j][i].candy.getType().equals(Type.REWARD_FRUIT)) {
          points = this.cells[j][i].candy.getPoints();
          this.cells[j][i].crush(pool, this.cells);
          handleChange(points);
        }
      }
      for (var i = 0; i < this.cells.length; i++) {
        var j = cells.length - 1;
        var points = 0;
        while (j > 0) {
          points = this.cells[j][i].interact(this.cells[j - 1][i], this.pool, this.cells);
          if (points != 0) {
            handleChange(points);
          } else {
            j = j - 1;
          }
        }
      }
      for (Cell[] cell : this.cells) {
        var j = 0;
        var points = 0;
        while (j < cells.length - 1) {
          points = cell[j].interact(cell[j + 1], this.pool, this.cells);
          if (points != 0) {
            handleChange(points);
          } else {
            j = j + 1;
          }
        }
      }
      end = System.currentTimeMillis();
    }
  }

}
