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

package com.iluwatar.roleobject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Core class to store different customer roles.
 *
 * @see CustomerRole Note: not thread safe
 */
public class CustomerCore extends Customer {

  private Map<Role, CustomerRole> roles;

  public CustomerCore() {
    roles = new HashMap<>();
  }

  @Override
  public boolean addRole(Role role) {
    return role
        .instance()
        .map(inst -> {
          roles.put(role, inst);
          return true;
        })
        .orElse(false);
  }

  @Override
  public boolean hasRole(Role role) {
    return roles.containsKey(role);
  }

  @Override
  public boolean remRole(Role role) {
    return Objects.nonNull(roles.remove(role));
  }

  @Override
  public <T extends Customer> Optional<T> getRole(Role role, Class<T> expectedRole) {
    return Optional
        .ofNullable(roles.get(role))
        .filter(expectedRole::isInstance)
        .map(expectedRole::cast);
  }

  @Override
  public String toString() {
    String roles = Arrays.toString(this.roles.keySet().toArray());
    return "Customer{roles=" + roles + "}";
  }
}
