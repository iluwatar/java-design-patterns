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

import java.util.ArrayList;
import com.iluwatar.typeobject.Candy.Type;

/**
 * The CandyGame class contains the rules for the continuation of the game and has
 * the game matrix (field 'cells') and totalPoints gained during the game.
 */

public class CandyGame {
  Cell[][] cells;
  CellPool pool;
  int totalPoints;
  
  CandyGame(int num, CellPool pool) {
    this.cells = new Cell[num][num];
    this.pool = pool;
    this.totalPoints = 0;
    for (int i = 0; i < num; i++) {
      for (int j = 0; j < num; j++) {
        this.cells[i][j] = this.pool.getNewCell();
        this.cells[i][j].xIndex = j;
        this.cells[i][j].yIndex = i;
      }
    }
  }
  
  static String numOfSpaces(int num) {
    String result = "";
    for (int i = 0; i < num; i++) {
      result += " ";
    }
    return result;
  }
  
  void printGameStatus() {
    System.out.println("");
    for (int i = 0; i < cells.length; i++) {
      for (int j = 0; j < cells.length; j++) {
        String candyName = cells[i][j].candy.name;
        if (candyName.length() < 20) {
          int totalSpaces = 20 - candyName.length();
          System.out.print(numOfSpaces(totalSpaces / 2) + cells[i][j].candy.name
              + numOfSpaces(totalSpaces - totalSpaces / 2) + "|");
        } else {
          System.out.print(candyName + "|");
        }
      }
      System.out.println("");
    }
    System.out.println("");
  }
  
  ArrayList<Cell> adjacentCells(int yIndex, int xIndex) {
    ArrayList<Cell> adjacent = new ArrayList<Cell>();
    if (yIndex == 0) {
      adjacent.add(this.cells[1][xIndex]);
    }
    if (xIndex == 0) {
      adjacent.add(this.cells[yIndex][1]);
    }
    if (yIndex == cells.length - 1) {
      adjacent.add(this.cells[cells.length - 2][xIndex]);
    }
    if (xIndex == cells.length - 1) {
      adjacent.add(this.cells[yIndex][cells.length - 2]);
    }
    if (yIndex > 0 && yIndex < cells.length - 1) {
      adjacent.add(this.cells[yIndex - 1][xIndex]);
      adjacent.add(this.cells[yIndex + 1][xIndex]);
    }
    if (xIndex > 0 && xIndex < cells.length - 1) {
      adjacent.add(this.cells[yIndex][xIndex - 1]);
      adjacent.add(this.cells[yIndex][xIndex + 1]);
    }
    return adjacent;
  }
   
  boolean continueRound() {
    for (int i = 0; i < this.cells.length; i++) {
      if (this.cells[cells.length - 1][i].candy.getType().equals(Type.rewardFruit)) {
        return true;
      }
    }
    for (int i = 0; i < this.cells.length; i++) {
      for (int j = 0; j < this.cells.length; j++) {
        if (!this.cells[i][j].candy.getType().equals(Type.rewardFruit)) {
          ArrayList<Cell> adj = adjacentCells(i,j);
          for (int a = 0; a < adj.size(); a++) {
            if (this.cells[i][j].candy.name.equals(adj.get(a).candy.name)) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }
  
  void handleChange(int points) {
    System.out.println("+" + points + " points!");
    this.totalPoints += points; 
    printGameStatus();
  }
  
  void round(int timeSoFar, int totalTime) {
    long start = System.currentTimeMillis();
    long end = System.currentTimeMillis();
    while (end - start + timeSoFar < totalTime && continueRound()) {
      for (int i = 0; i < this.cells.length; i++) {
        int points = 0;
        int j = this.cells.length - 1;
        while (this.cells[j][i].candy.getType().equals(Type.rewardFruit)) {
          points = this.cells[j][i].candy.getPoints();
          this.cells[j][i].crush(pool, this.cells);
          handleChange(points);
        }
      }
      for (int i = 0; i < this.cells.length; i++) {
        int j = cells.length - 1;
        int points = 0;
        while (j > 0) { 
          points = this.cells[j][i].interact(this.cells[j - 1][i], this.pool, this.cells);
          if (points != 0) {  
            handleChange(points);
          } else {
            j = j - 1;
          }
        }
      }
      for (int i = 0; i < this.cells.length; i++) {
        int j = 0;
        int points = 0;
        while (j < cells.length - 1) {       
          points = this.cells[i][j].interact(this.cells[i][j + 1], this.pool, this.cells);
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
