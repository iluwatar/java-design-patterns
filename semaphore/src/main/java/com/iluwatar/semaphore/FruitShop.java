/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

/**
 * A FruitShop contains three FruitBowl instances and controls access to them.
 */
public class FruitShop {

  /**
   * The FruitBowl instances stored in the class.
   */
  private FruitBowl[] bowls = {
      new FruitBowl(),
      new FruitBowl(),
      new FruitBowl()
  };

  /**
   * Access flags for each of the FruitBowl instances.
   */
  private boolean[] available = {
      true,
      true,
      true
  };

  /**
   * The Semaphore that controls access to the class resources.
   */
  private Semaphore semaphore;

  /**
   * FruitShop constructor.
   */
  public FruitShop() {
    for (var i = 0; i < 100; i++) {
      bowls[0].put(new Fruit(Fruit.FruitType.APPLE));
      bowls[1].put(new Fruit(Fruit.FruitType.ORANGE));
      bowls[2].put(new Fruit(Fruit.FruitType.LEMON));
    }

    semaphore = new Semaphore(3);
  }

  /**
   * Returns the amount of fruits left in shop.
   *
   * @return The amount of Fruit left in the shop.
   */
  public synchronized int countFruit() {
    return bowls[0].countFruit() + bowls[1].countFruit() + bowls[2].countFruit();
  }

  /**
   * Method called by Customer to get a FruitBowl from the shop. This method will try to acquire the
   * Semaphore before returning the first available FruitBowl.
   */
  public synchronized FruitBowl takeBowl() {

    FruitBowl bowl = null;

    try {
      semaphore.acquire();

      if (available[0]) {
        bowl = bowls[0];
        available[0] = false;
      } else if (available[1]) {
        bowl = bowls[1];
        available[1] = false;
      } else if (available[2]) {
        bowl = bowls[2];
        available[2] = false;
      }

    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      semaphore.release();
    }
    return bowl;
  }

  /**
   * Method called by a Customer instance to return a FruitBowl to the shop. This method releases
   * the Semaphore, making the FruitBowl available to another Customer.
   */
  public synchronized void returnBowl(FruitBowl bowl) {
    if (bowl == bowls[0]) {
      available[0] = true;
    } else if (bowl == bowls[1]) {
      available[1] = true;
    } else if (bowl == bowls[2]) {
      available[2] = true;
    }
  }

}
