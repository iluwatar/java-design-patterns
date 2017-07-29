/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
package com.iluwatar.cqrs.dto;

import java.util.Objects;

/**
 * 
 * This is a DTO (Data Transfer Object) author, contains only useful information to be returned
 *
 */
public class Author {

  private String name;
  private String email;
  private String username;

  /**
   * 
   * @param name
   *          name of the author
   * @param email
   *          email of the author
   * @param username
   *          username of the author
   */
  public Author(String name, String email, String username) {
    super();
    this.name = name;
    this.email = email;
    this.username = username;
  }

  public Author() {
    super();
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getUsername() {
    return username;
  }

  @Override
  public String toString() {
    return "AuthorDTO [name=" + name + ", email=" + email + ", username=" + username + "]";
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, name, email);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Author)) {
      return false;
    }
    Author other = (Author) obj;
    return username.equals(other.getUsername()) && email.equals(other.getEmail()) && name.equals(other.getName());

  }

}
