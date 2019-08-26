/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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

/**
 * The Cell object is what the game matrix is made of and contains the candy which is
 * to be crushed or collected as reward.
 */

public class Cell {
  Candy candy;
  int xIndex;
  int yIndex;
  
  Cell(Candy candy, int xIndex, int yIndex) {
    this.candy = candy;
    this.xIndex = xIndex;
    this.yIndex = yIndex;
  }
  
  Cell() {
    this.candy = null;
    this.xIndex = 0;
    this.yIndex = 0;
  }
  
  void crush(CellPool pool, Cell[][] cellMatrix) {
    //take out from this position and put back in pool
    pool.addNewCell(this);
    this.fillThisSpace(pool, cellMatrix);
  }
  
  void fillThisSpace(CellPool pool, Cell[][] cellMatrix) {
    for (int y = this.yIndex; y > 0; y--) {
      cellMatrix[y][this.xIndex] = cellMatrix[y - 1][this.xIndex];
      cellMatrix[y][this.xIndex].yIndex = y;
    }
    Cell newC = pool.getNewCell();
    cellMatrix[0][this.xIndex] = newC;
    cellMatrix[0][this.xIndex].xIndex = this.xIndex;
    cellMatrix[0][this.xIndex].yIndex = 0;
  }
  
  void handleCrush(Cell c, CellPool pool, Cell[][] cellMatrix) {
    if (this.yIndex >= c.yIndex) {      
      this.crush(pool, cellMatrix);       
      c.crush(pool, cellMatrix);
    } else {
      c.crush(pool, cellMatrix);
      this.crush(pool, cellMatrix);
    }
  }
  
  int interact(Cell c, CellPool pool, Cell[][] cellMatrix) {
    if (this.candy.getType().equals(Type.rewardFruit) || c.candy.getType().equals(Type.rewardFruit)) {
      return 0;
    } else {
      if (this.candy.name.equals(c.candy.name)) {
        int pointsWon = this.candy.getPoints() + c.candy.getPoints();
        handleCrush(c,pool,cellMatrix);
        return pointsWon;
      } else {
        return 0;
      }
    }
  }
}
