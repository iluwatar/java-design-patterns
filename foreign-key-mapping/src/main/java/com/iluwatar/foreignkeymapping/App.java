/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Sepp?l?
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
package com.iluwatar.foreignkeymapping;

import lombok.extern.slf4j.Slf4j;

/**
 * Multiple lines of Javadoc text are written here,
 * wrapped normally...
 */
@Slf4j
public class App {

  static final String ORDERTABLENAME = "order";
  static final String PERSONTABLENAME = "person";

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    Person person1 = new Person(1, "John", "Loli", 33);
    Person person2 = new Person(2, "Thomas", "Funny", 22);
    Person person3 = new Person(3, "Arthur", "Chiken", 11);
    Person person4 = new Person(4, "Finn", "Wash", 55);
    Person person5 = new Person(5, "Michael", "Linux", 67);

    Order order1 = new Order(1, "cola", person1);
    Order order2 = new Order(2, "cafe", person1);
    Order order3 = new Order(3, "juice", person3);
    Order order4 = new Order(4, "wola", person3);
    Order order5 = new Order(5, "kala", person1);

    AppDbSimulatorImplementation database = new AppDbSimulatorImplementation();
    database.insert(person1, PERSONTABLENAME);
    database.insert(person2, PERSONTABLENAME);
    database.insert(person3, PERSONTABLENAME);
    database.insert(person4, PERSONTABLENAME);
    database.insert(person5, PERSONTABLENAME);
    database.insert(order1, ORDERTABLENAME);
    database.insert(order2, ORDERTABLENAME);
    database.insert(order3, ORDERTABLENAME);
    database.insert(order4, ORDERTABLENAME);
    database.insert(order5, ORDERTABLENAME);

    LOGGER.info(database.find(2, PERSONTABLENAME).toString());
    LOGGER.info(database.find(4, PERSONTABLENAME).toString());
    LOGGER.info(database.find(5, PERSONTABLENAME).toString());

    LOGGER.info(database.find(2, ORDERTABLENAME).toString());
    LOGGER.info(database.find(4, ORDERTABLENAME).toString());
    LOGGER.info(database.find(5, ORDERTABLENAME).toString());

    // get order owner
    LOGGER.info(order2.getOwner().toString());

    // get person's orders
    LOGGER.info(person1.getAllOrder(database.getOrderList()).toString());

    // person place order
    Order order6 = new Order(6, "fula", person1);
    database.insert(order6, ORDERTABLENAME);
    LOGGER.info(person1.getAllOrder(database.getOrderList()).toString());
  }

}
