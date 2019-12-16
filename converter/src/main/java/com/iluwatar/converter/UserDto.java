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

package com.iluwatar.converter;

import java.util.Objects;

/**
 * User DTO class.
 */
public class UserDto {

  private String firstName;
  private String lastName;
  private boolean isActive;
  private String email;

  /**
   * Constructor.
   *
   * @param firstName user's first name
   * @param lastName  user's last name
   * @param isActive  flag indicating whether the user is active
   * @param email     user's email address
   */
  public UserDto(String firstName, String lastName, boolean isActive, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.isActive = isActive;
    this.email = email;
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

  public String getEmail() {
    return email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    var userDto = (UserDto) o;
    return isActive == userDto.isActive && Objects.equals(firstName, userDto.firstName) && Objects
        .equals(lastName, userDto.lastName) && Objects.equals(email, userDto.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, isActive, email);
  }

  @Override
  public String toString() {
    return "UserDto{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\''
        + ", isActive=" + isActive + ", email='" + email + '\'' + '}';
  }
}
