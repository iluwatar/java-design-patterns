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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;

import org.json.simple.parser.ParseException;

/**
 * The CellPool class allows the reuse of crushed cells instead of creation of new
 * cells each time. The reused cell is given a new candy to hold using the randomCode
 * field which holds all the candies available.
 */

public class CellPool {
  ArrayList<Cell> pool;
  int pointer;
  Candy[] randomCode;
  
  CellPool(int num) throws FileNotFoundException, IOException, ParseException {
    this.pool = new ArrayList<Cell>(num);
    this.randomCode = assignRandomCandytypes();
    for (int i = 0; i < num; i++) {
      Cell c = new Cell();
      Random rand = new Random();
      c.candy = randomCode[rand.nextInt(randomCode.length)];
      this.pool.add(c);
    }
    this.pointer = num - 1;
  }
  
  Cell getNewCell() {
    Cell newCell = this.pool.remove(pointer);
    pointer--;
    return newCell;
  }
  
  void addNewCell(Cell c) {
    Random rand = new Random();
    c.candy = randomCode[rand.nextInt(randomCode.length)]; //changing candytype to new
    this.pool.add(c);
    pointer++;
  }
  
  Candy[] assignRandomCandytypes() throws FileNotFoundException, IOException, ParseException {
    JsonParser jp = new JsonParser();
    jp.parse();
    Candy[] randomCode = new Candy[jp.candies.size() - 2]; //exclude generic types 'fruit' and 'candy'
    int i = 0;
    for (Enumeration<String> e = jp.candies.keys(); e.hasMoreElements();) {
      String s = e.nextElement();
      if (!s.equals("fruit") && !s.equals("candy")) {
        //not generic
        randomCode[i] = jp.candies.get(s);
        i++;
      }
    }
    return randomCode;
  }
}
