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
import com.iluwatar.typeobject.Candy.Type;

/**
 * The CellTest class tests the methods in the {@link Cell} class.
 */

class CellTest {

  @Test
  void interactTest() {
    Candy c1 = new Candy("green jelly", "jelly", Type.crushableCandy, 5);
    Candy c2 = new Candy("green apple", "apple", Type.rewardFruit, 10);
    Cell[][] matrix = new Cell[4][4];
    matrix[0][0] = new Cell(c1,0,0);
    matrix[0][1] = new Cell(c1,1,0);
    matrix[0][2] = new Cell(c2,2,0);
    matrix[0][3] = new Cell(c1,3,0);
    CellPool cp = new CellPool(5);
    int points1 = matrix[0][0].interact(matrix[0][1], cp, matrix);
    int points2 = matrix[0][2].interact(matrix[0][3], cp, matrix);
    assertTrue(points1 > 0 && points2 == 0);
  }

  @Test
  void crushTest() {
    Candy c1 = new Candy("green jelly", "jelly", Type.crushableCandy, 5);
    Candy c2 = new Candy("purple candy", "candy", Type.crushableCandy, 5);
    Cell[][] matrix = new Cell[4][4];
    matrix[0][0] = new Cell(c1,0,0);
    matrix[1][0] = new Cell(c2,0,1);
    matrix[1][0].crush(new CellPool(5), matrix);
    assertTrue(matrix[1][0].candy.name.equals("green jelly"));
  }
}
