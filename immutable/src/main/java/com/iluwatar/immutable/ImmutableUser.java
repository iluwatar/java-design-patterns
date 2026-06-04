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
package com.iluwatar.immutable;

import java.util.List;

/**
 * An immutable representation of a user.
 *
 * <p>All fields are final and set only at construction time. The {@code roles} list is defensively
 * copied to prevent external mutation. Any "modification" produces a new {@link ImmutableUser}
 * instance, leaving the original unchanged.
 */
public final class ImmutableUser {

  private final String name;
  private final int age;
  private final List<String> roles;

  /**
   * Constructs an {@link ImmutableUser} with the given attributes.
   *
   * @param name the user's name
   * @param age the user's age
   * @param roles the user's roles; copied defensively so external changes have no effect
   */
  public ImmutableUser(String name, int age, List<String> roles) {
    this.name = name;
    this.age = age;
    this.roles = List.copyOf(roles);
  }

  /**
   * Returns the user's name.
   *
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the user's age.
   *
   * @return age
   */
  public int getAge() {
    return age;
  }

  /**
   * Returns an unmodifiable view of the user's roles.
   *
   * @return roles
   */
  public List<String> getRoles() {
    return roles;
  }

  /**
   * Returns a new {@link ImmutableUser} identical to this one but with the given age.
   *
   * @param newAge the new age value
   * @return a new instance with the updated age
   */
  public ImmutableUser withAge(int newAge) {
    return new ImmutableUser(this.name, newAge, this.roles);
  }

  @Override
  public String toString() {
    return "ImmutableUser{name='" + name + "', age=" + age + ", roles=" + roles + '}';
  }
}
