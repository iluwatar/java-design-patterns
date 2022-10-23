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

public class SampleTest {
  static final String order = "order";
  static final String person = "person";

  Person person1 = new Person(1, "John", "Loli", 33);
  Person person2 = new Person(2, "Thomas", "Funny", 22);
  Person person3 = new Person(3, "Arthur", "Chiken", 11);
  Person person4 = new Person(4, "Finn", "Wash", 55);
  Person person5 = new Person(5, "Michael", "Linux", 67);

  Order order1 = new Order(1, "2132131", 1);
  Order order2 = new Order(2, "12321321", 1);
  Order order3 = new Order(3, "213213123", 3);
  Order order4 = new Order(4, "123213213", 3);
  Order order5 = new Order(5, "12312321231", 1);


  @Test
  public void insertFindPerson() {
    AppDbSimulatorImplementation database = new AppDbSimulatorImplementation();
    database.insert(person1,person);
    assertEquals(person1, database.find(1,person));
  }

  @Test(expected = IdNotFoundException.class)
  public void findPersonNotExist() {
    AppDbSimulatorImplementation database = new AppDbSimulatorImplementation();
    database.insert(person1,person);
    database.find(2,person);
  }

  @Test
  public void insertfindOrder() {
    AppDbSimulatorImplementation database = new AppDbSimulatorImplementation();
    database.insert(person1,person);
    database.insert(order1,order);
    assertEquals(order1, database.find(1,order));
  }

  @Test(expected = IdNotFoundException.class)
  public void findOrderNotExist() {
    AppDbSimulatorImplementation database = new AppDbSimulatorImplementation();
    database.insert(person1,person);
    database.insert(order1,order);
    database.find(2,order);
  }

  @Test(expected = IdNotFoundException.class)
  public void insertOrderWithUnkownOwner() {
    AppDbSimulatorImplementation database = new AppDbSimulatorImplementation();
    database.insert(order1,order);
  }

  @Test
  public void updatePerson() {
    AppDbSimulatorImplementation database = new AppDbSimulatorImplementation();
    database.insert(person1,person);
    Person personUpdate = new Person(1, "John", "Loli", 30);
    database.update(personUpdate,person);
    assertEquals(personUpdate, database.find(1,person));
  }

  @Test
  public void updateOrder() {
    AppDbSimulatorImplementation database = new AppDbSimulatorImplementation();
    database.insert(person1,person);
    database.insert(order1,order);
    Order orderUpdate = new Order(1, "123", 1);
    database.update(orderUpdate,order);
    assertEquals(orderUpdate, database.find(1,order));
  }

  @Test(expected = IdNotFoundException.class)
  public void deletePerson() {
    AppDbSimulatorImplementation database = new AppDbSimulatorImplementation();
    database.insert(person1,person);
    database.delete(1,person);
    database.find(1,person);
  }

  @Test(expected = IdNotFoundException.class)
  public void deletePersonNotExist() {
    AppDbSimulatorImplementation database = new AppDbSimulatorImplementation();
    database.delete(1,person);
  }

  @Test(expected = IdNotFoundException.class)
  public void deleteOrder() {
    AppDbSimulatorImplementation database = new AppDbSimulatorImplementation();
    database.insert(person1,person);
    database.delete(1,person);
    database.find(1,person);
  }

  @Test(expected = IdNotFoundException.class)
  public void deleteOrderNotExist() {
    AppDbSimulatorImplementation database = new AppDbSimulatorImplementation();
    database.delete(1,order);
  }

  @Test(expected = IdNotFoundException.class)
  public void deletePersonWithOrder() {
    AppDbSimulatorImplementation database = new AppDbSimulatorImplementation();
    database.insert(person1,person);
    database.insert(order1,order);
    database.delete(1,person);
    database.find(1,order);
  }

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
  public void getPersonOrderNoRecord() {
    AppDbSimulatorImplementation database = new AppDbSimulatorImplementation();
    database.insert(person1,person);
    List<Order> compare = new ArrayList<>();
    assertEquals(compare, person1.getAllOrder(database.getOrderList()));
  }
}
