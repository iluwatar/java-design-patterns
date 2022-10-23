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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppDbSimulatorImplementation implements AppDbSimulator {
  private List<Order> orderList = new ArrayList<>();
  private List<Person> personList = new ArrayList<>();

  public List<Order> getOrderList() {
    return orderList;
  }

  public void setOrderList(List<Order> orderList) {
    this.orderList = orderList;
  }

  public List<Person> getPersonList() {
    return personList;
  }

  public void setPersonList(List<Person> personList) {
    this.personList = personList;
  }

  @Override
  public Object find(int id, String table) throws IdNotFoundException {
    if (table.toLowerCase(Locale.ROOT).equals("person")) {
      for (Person elem : personList) {
        if (elem.getPersonNationalId() == id) {
          return elem;
        }
      }
      LOGGER.info("Person Not Found");
    } else if (table.toLowerCase(Locale.ROOT).equals("order")) {
      for (Order elem : orderList) {
        if (elem.getOrderNationalId() == id) {
          return elem;
        }
      }
      LOGGER.info("Person Not Found");
    } else {
      LOGGER.info("Table Not Found");

    }
    throw new IdNotFoundException("ID not in DataBase");
  }

  @Override
  public void insert(Object object, String table) {
    if (table.toLowerCase(Locale.ROOT).equals("person")) {
      Person person = (Person) object;
      for (Person elem : personList) {
        if (elem.getPersonNationalId() == person.getPersonNationalId()) {
          LOGGER.info("Record already exists.");
          return;
        }
      }
      personList.add(person);
    } else if (table.toLowerCase(Locale.ROOT).equals("order")) {
      Order order = (Order) object;
      boolean find = false;
      for (Person person : personList) {
        if (person.equals(order.getOwner())) {
          find = true;
        }
      }
      if (find) {
        for (Order elem : orderList) {
          if (elem.getOrderNationalId() == order.getOrderNationalId()) {
            LOGGER.info("Record already exists.");
            return;
          }
        }
        orderList.add(order);
      } else {
        LOGGER.info("Person Not Found");
        throw new IdNotFoundException("OwnerId not in DataBase");
      }
    } else {
      LOGGER.info("Table Not Found");
    }
  }

  @Override
  public void update(Object object, String table) throws IdNotFoundException {
    if (table.toLowerCase(Locale.ROOT).equals("person")) {
      Person person = (Person) object;
      for (Person elem : personList) {
        if (elem.getPersonNationalId() == person.getPersonNationalId()) {
          elem.setFirstName(person.getFirstName());
          elem.setAge(person.getAge());
          LOGGER.info("Record updated successfully");
          return;
        }
      }
    } else if (table.toLowerCase(Locale.ROOT).equals("order")) {
      Order order = (Order) object;
      for (Order elem : orderList) {
        if (elem.getOrderNationalId() == order.getOrderNationalId()) {
          elem.setContent(order.getContent());
          elem.setOwner(order.getOwner());
          LOGGER.info("Record updated successfully");
          return;
        }
      }
    } else {
      LOGGER.info("Table Not Found");
    }

    throw new IdNotFoundException("ID not in DataBase");
  }

  /**
   * Delete specific person or order.
   *
   * @param id person's id or order's id
   * @param table delete from person table or order table
   */
  public void delete(int id, String table) throws IdNotFoundException {
    if (table.toLowerCase(Locale.ROOT).equals("person")) {
      for (Person elem : personList) {
        if (elem.getPersonNationalId() == id) {
          personList.remove(elem);
          deletePersonOrder(elem);
          LOGGER.info("Record deleted successfully.");
          return;
        }
      }
    } else if (table.toLowerCase(Locale.ROOT).equals("order")) {
      for (Order elem : orderList) {
        if (elem.getOrderNationalId() == id) {
          orderList.remove(elem);
          LOGGER.info("Record deleted successfully.");
          return;
        }
      }
    } else {
      LOGGER.info("Table Not Found");
    }

    throw new IdNotFoundException("ID : " + id + " not in DataBase");
  }

  /**
   * Delete person's orders in orderList.
   *
   * @param person the specific person
   */
  public void deletePersonOrder(Person person) throws IdNotFoundException {
    ArrayList<Order> deleteList = new ArrayList<>();
    for (Order elem : orderList) {
      if (elem.getOwner().equals(person)) {
        deleteList.add(elem);
      }
    }
    for (Order order : deleteList) {
      orderList.remove(order);
    }
  }

}
