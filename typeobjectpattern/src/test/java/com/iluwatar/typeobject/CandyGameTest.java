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

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import com.iluwatar.typeobject.Candy.Type;

/**
 * The CandyGameTest class tests the methods in the {@link CandyGame} class.
 */

class CandyGameTest {

  @Test
  void adjacentCellsTest() {
    CandyGame cg = new CandyGame(3,new CellPool(9));
    ArrayList<Cell> arr1 = cg.adjacentCells(0, 0);
    ArrayList<Cell> arr2 = cg.adjacentCells(1, 2);
    ArrayList<Cell> arr3 = cg.adjacentCells(1, 1);
    assertTrue(arr1.size() == 2 && arr2.size() == 3 && arr3.size() == 4);
  }

  @Test
  void continueRoundTest() {
    Cell[][] matrix = new Cell[2][2];
    Candy c1 = new Candy("green jelly", "jelly", Type.crushableCandy, 5);
    Candy c2 = new Candy("purple jelly", "jelly", Type.crushableCandy, 5);
    Candy c3 = new Candy("green apple", "apple", Type.rewardFruit, 10);
    matrix[0][0] = new Cell(c1,0,0);;
    matrix[0][1] = new Cell(c2,1,0);
    matrix[1][0] = new Cell(c3,0,1);
    matrix[1][1] = new Cell(c2,1,1);
    CellPool p = new CellPool(4);
    CandyGame cg = new CandyGame(2,p);
    cg.cells = matrix;
    boolean fruitInLastRow = cg.continueRound();
    matrix[1][0].crush(p, matrix);
    matrix[0][0] = new Cell(c3,0,0);
    boolean matchingCandy = cg.continueRound(); 
    matrix[0][1].crush(p,matrix);
    matrix[0][1] = new Cell(c3,1,0);
    boolean noneLeft = cg.continueRound();
    assertTrue(fruitInLastRow && matchingCandy && !noneLeft);
  }

}
