/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.converter;

import java.util.Objects;

/**
 * User class
 */
public class User {
  private String firstName;
  private String lastName;
  private boolean isActive;
  private String userId;

  /**
   * @param firstName user's first name
   * @param lastName  user's last name
   * @param isActive  flag indicating whether the user is active
   * @param userId user's identificator
   */
  public User(String firstName, String lastName, boolean isActive, String userId) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.isActive = isActive;
    this.userId = userId;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public boolean isActive() {
    return isActive;
  }

  public String getUserId() {
    return userId;
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return isActive == user.isActive && Objects.equals(firstName, user.firstName) && Objects
      .equals(lastName, user.lastName) && Objects.equals(userId, user.userId);
  }

  @Override public int hashCode() {
    return Objects.hash(firstName, lastName, isActive, userId);
  }

  @Override public String toString() {
    return "User{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\''
      + ", isActive=" + isActive + ", userId='" + userId + '\'' + '}';
  }
}
