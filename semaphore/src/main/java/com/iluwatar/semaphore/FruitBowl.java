/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
package com.iluwatar.semaphore;

import java.util.ArrayList;

/**
 * FruitBowl.
 */
public class FruitBowl {
    
  private ArrayList<Fruit> fruit = new ArrayList<>();

  public int countFruit() {
    return fruit.size();
  }

  public void put(Fruit f) {
    fruit.add(f);
  }
  
  /**
   * take method
   */   
  public Fruit take() {
    if (fruit.isEmpty()) {
      return null;
    } else {
      return fruit.remove(0);
    }
  }
  
  /**
   * toString method
   */   
  public String toString() {
    int apples = 0;
    int oranges = 0;
    int lemons = 0;
        
    for (Fruit f : fruit) {
      switch (f.getType()) {
        case APPLE:
          apples++;
          break;
        case ORANGE:
          oranges++;
          break;
        case LEMON:
          lemons++;
          break;
        default:
      }
    }
        
    return apples + " Apples, " + oranges + " Oranges, and " + lemons + " Lemons";
  }
}
