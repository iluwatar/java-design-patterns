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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Customer attempts to repeatedly take Fruit from the FruitShop by taking Fruit from FruitBowl
 * instances.
 */
public class Customer extends Thread {

  private static final Logger LOGGER = LoggerFactory.getLogger(Customer.class);

  /**
   * Name of the Customer.
   */
  private final String name;

  /**
   * The FruitShop he is using.
   */
  private final FruitShop fruitShop;

  /**
   * Their bowl of Fruit.
   */
  private final FruitBowl fruitBowl;

  /**
   * Customer constructor.
   */
  public Customer(String name, FruitShop fruitShop) {
    this.name = name;
    this.fruitShop = fruitShop;
    this.fruitBowl = new FruitBowl();
  }

  /**
   * The Customer repeatedly takes Fruit from the FruitShop until no Fruit remains.
   */
  public void run() {

    while (fruitShop.countFruit() > 0) {
      var bowl = fruitShop.takeBowl();
      if (bowl != null) {
        var fruit = bowl.take();
        if (fruit != null) {
          LOGGER.info("{} took an {}", name, fruit);
          fruitBowl.put(fruit);
          fruitShop.returnBowl(bowl);
        }
      }
    }

    LOGGER.info("{} took {}", name, fruitBowl);

  }

}
