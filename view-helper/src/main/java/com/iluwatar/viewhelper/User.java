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
package com.iluwatar.viewhelper;

/**
 * Represents a User with a name, age, and email address.
 */
public class User {

  private final String name;
  private final int age;
  private final String email;

  /**
   * Constructs a User with the specified name, age, and email.
   *
   * @param name  the user's name
   * @param age   the user's age
   * @param email the user's email address
   */
  public User(String name, int age, String email) {
    this.name = name;
    this.age = age;
    this.email = email;
  }

  /**
   * Gets the user's name.
   *
   * @return the user's name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the user's age.
   *
   * @return the user's age
   */
  public int getAge() {
    return age;
  }

  /**
   * Gets the user's email address.
   *
   * @return the user's email address
   */
  public String getEmail() {
    return email;
  }
}
