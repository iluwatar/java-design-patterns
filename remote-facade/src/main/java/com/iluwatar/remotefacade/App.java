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

package com.iluwatar.remotefacade;

import java.time.Duration;
import java.time.Instant;

/**
 * The Remote Facade pattern should be used when a remote client makes many calls to a system
 * with fine-grained methods. A facade is used to make the client's interaction with the system
 * simpler by providing a more general, coarse-grained interface.
 * 
 * <p>This example uses ({@link OrderFacade}) as the facade which simplifies the process of
 * a remote client placing an order.
 */
public class App {

  /**
   * Program main entry point.
   */
  public static void main(String[] args) {
    final Instant start1 = Instant.now();
    Order order1 = new Order();
    order1.setDrink("water");
    order1.getDrink();

    Order order2 = new Order();
    order2.setEntree("pizza");
    order2.getEntree();

    Order order3 = new Order();
    order3.setSide("fries");
    order3.getSide();
    final Instant finish1 = Instant.now();


    final Instant start2 = Instant.now();
    OrderFacade order4 = new OrderFacade();
    order4.setOrder("soda", "burger", "salad");
    order4.getOrder();
    final Instant finish2 = Instant.now();

    long executionTime1 = Duration.between(start1, finish1).toMillis();
    System.out.print("Without facade: " + executionTime1 + "ms\n");

    long executionTime2 = Duration.between(start2, finish2).toMillis();
    System.out.print("With facade: " + executionTime2 + "ms");
  }
}
