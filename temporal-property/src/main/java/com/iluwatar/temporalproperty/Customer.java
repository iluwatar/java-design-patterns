/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.temporalproperty;

/**
 * Class used to represent a customer with a unique id, a name, and a history of address.
 */
public class Customer {
  private final int id;
  private String name;

  private final AddressHistory addressHistory;

  /**
   * Create new customer.
   *
   * @param id Id of the customer
   * @param name name of the customer
   */
  public Customer(int id, String name) {
    this.id = id;
    this.name = name;
    addressHistory = new AddressHistory();
  }

  /**
   * Set the customers address on the given date to the given address.
   *
   * @param date Date to set
   * @param address address to set
   */
  public void putAddress(String address, SimpleDate date) {
    addressHistory.put(date, address);
  }

  /**
   * Set the customers address today to be the given address.
   *
   * @param address Address to set for today
   */
  public void putAddress(String address) {
    putAddress(address, SimpleDate.getToday());
  }

  /**
   * Returns the address of the customer on given date.
   *
   * @param date Date to check the Customer's address on
   * @return The address of the customer at given date
   */
  public String getAddress(SimpleDate date) throws IllegalStateException {
    return addressHistory.get(date);
  }

  /**
   * Gets the customer's current address.
   *
   * @return The address of the customer today
   */
  public String getAddress() throws IllegalStateException {
    return getAddress(SimpleDate.getToday());
  }

  public int getId() {
    return id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
