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

package com.iluwatar.auditlog;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Class used to represent a customer with a unique id, a name, and an address.
 */
@Getter
public class Customer {
  private String address;
  private String name;
  private final int id;

  public Customer(String name, int id) {
    this.name = name;
    this.id = id;
  }

  /**
   * Sets the current address of this customer with the change occurring on the given date.
   *
   * @param address Address that the customer is changing to.
   * @param changeDate The date that the customer changed address.
   */
  public void setAddress(String address, SimpleDate changeDate) {
    AuditLog.log(changeDate, this.getClass().getTypeName(), String.valueOf(id),
            address.getClass().getTypeName(), "address", this.address, address);
    this.address = address;
  }

  /**
   * Sets the name of this customer with the change occurring on the given date.
   *
   * @param name Name that the customer is changing to.
   * @param changeDate The date that the customer changed name.
   */
  public void setName(String name, SimpleDate changeDate) {
    AuditLog.log(changeDate, this.getClass().getTypeName(), String.valueOf(id),
            name.getClass().getTypeName(), "name", this.name, name);
    this.name = name;
  }
}
