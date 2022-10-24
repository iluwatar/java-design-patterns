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

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PersonTest {
  static final String order = "order";
  static final String person = "person";

  Person person1 = new Person(1, "John", "Loli", 33);

  Order order1 = new Order(1, "fula", person1);
  Order order2 = new Order(2, "cola", person1);

  @Test
  public void getPersonOrder() {
    AppDbSimulatorImplementation database = new AppDbSimulatorImplementation();
    database.insert(person1,person);
    database.insert(order1,order);
    database.insert(order2,order);
    List<Order> compare = new ArrayList<>();
    compare.add(order1);
    compare.add(order2);
    assertEquals(compare, person1.getAllOrder(database.getOrderList()));
  }

  @Test
  public void getPersonOrderNoOrder() {
    AppDbSimulatorImplementation database = new AppDbSimulatorImplementation();
    database.insert(person1,person);
    List<Order> compare = new ArrayList<>();
    assertEquals(compare, person1.getAllOrder(database.getOrderList()));
  }

  @Test
  public void toStringTest() {
    assertEquals("Person ID is : 1 ; Person Last Name is : John ; Person First Name is : Loli ; Age is :33", person1.toString());
  }
}
