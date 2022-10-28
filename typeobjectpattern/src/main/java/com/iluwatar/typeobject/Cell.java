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
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * The Cell object is what the game matrix is made of and contains the candy which is to be crushed
 * or collected as reward.
 */
@AllArgsConstructor
@NoArgsConstructor
public class Cell {
  Candy candy;
  int positionX;
  int positionY;

  void crush(CellPool pool, Cell[][] cellMatrix) {
    //take out from this position and put back in pool
    pool.addNewCell(this);
    this.fillThisSpace(pool, cellMatrix);
  }

  void fillThisSpace(CellPool pool, Cell[][] cellMatrix) {
    for (var y = this.positionY; y > 0; y--) {
      cellMatrix[y][this.positionX] = cellMatrix[y - 1][this.positionX];
      cellMatrix[y][this.positionX].positionY = y;
    }
    var newC = pool.getNewCell();
    cellMatrix[0][this.positionX] = newC;
    cellMatrix[0][this.positionX].positionX = this.positionX;
    cellMatrix[0][this.positionX].positionY = 0;
  }

  void handleCrush(Cell c, CellPool pool, Cell[][] cellMatrix) {
    if (this.positionY >= c.positionY) {
      this.crush(pool, cellMatrix);
      c.crush(pool, cellMatrix);
    } else {
      c.crush(pool, cellMatrix);
      this.crush(pool, cellMatrix);
    }
  }

  int interact(Cell c, CellPool pool, Cell[][] cellMatrix) {
    if (this.candy.getType().equals(Type.REWARD_FRUIT) || c.candy.getType()
        .equals(Type.REWARD_FRUIT)) {
      return 0;
    } else {
      if (this.candy.name.equals(c.candy.name)) {
        var pointsWon = this.candy.getPoints() + c.candy.getPoints();
        handleCrush(c, pool, cellMatrix);
        return pointsWon;
      } else {
        return 0;
      }
    }
  }
}
