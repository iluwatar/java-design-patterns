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
 * A Semaphore mediates access by a group of threads to a pool of resources.
 *
 * <p>In this example a group of customers are taking fruit from a fruit shop. There is a bowl each
 * of apples, oranges and lemons. Only one customer can access a bowl simultaneously. A Semaphore is
 * used to indicate how many resources are currently available and must be acquired in order for a
 * bowl to be given to a customer. Customers continually try to take fruit until there is no fruit
 * left in the shop.
 */
public class App {

  /**
   * main method.
   */
  public static void main(String[] args) {
    var shop = new FruitShop();
    new Customer("Peter", shop).start();
    new Customer("Paul", shop).start();
    new Customer("Mary", shop).start();
    new Customer("John", shop).start();
    new Customer("Ringo", shop).start();
    new Customer("George", shop).start();
  }

}
