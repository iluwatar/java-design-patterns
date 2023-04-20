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
package com.iluwatar.roleobject;

import java.util.Arrays;
import java.util.Optional;

/**
 * The main abstraction to work with Customer.
 */
public abstract class Customer {

  /**
   * Add specific role @see {@link Role}.
   *
   * @param role to add
   * @return true if the operation has been successful otherwise false
   */
  public abstract boolean addRole(Role role);

  /**
   * Check specific role @see {@link Role}.
   *
   * @param role to check
   * @return true if the role exists otherwise false
   */

  public abstract boolean hasRole(Role role);

  /**
   * Remove specific role @see {@link Role}.
   *
   * @param role to remove
   * @return true if the operation has been successful otherwise false
   */
  public abstract boolean remRole(Role role);

  /**
   * Get specific instance associated with this role @see {@link Role}.
   *
   * @param role         to get
   * @param expectedRole instance class expected to get
   * @return optional with value if the instance exists and corresponds expected class
   */
  public abstract <T extends Customer> Optional<T> getRole(Role role, Class<T> expectedRole);


  public static Customer newCustomer() {
    return new CustomerCore();
  }

  /**
   * Create {@link Customer} with given roles.
   *
   * @param role roles
   * @return Customer
   */
  public static Customer newCustomer(Role... role) {
    var customer = newCustomer();
    Arrays.stream(role).forEach(customer::addRole);
    return customer;
  }

}
