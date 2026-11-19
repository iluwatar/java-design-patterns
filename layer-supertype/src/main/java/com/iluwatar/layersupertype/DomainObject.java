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
package com.iluwatar.layersupertype;

import lombok.Getter;
import lombok.ToString;

/**
 * Layer Supertype of the domain layer. Every domain object extends it and so inherits one place for
 * identity: the id field, the check whether the object has been persisted yet, and equality based
 * on that id. Without this class each entity would repeat the same code.
 */
@Getter
@ToString
public abstract class DomainObject {

  private Long id;

  /**
   * Tells whether the object has been saved yet.
   *
   * @return true while no id has been assigned
   */
  public boolean isNew() {
    return id == null;
  }

  /**
   * Assigns the identity once. Only the data access layer supertype in this package calls it, so
   * client code cannot change an id.
   *
   * @param newId the id handed out by a repository
   */
  void assignId(long newId) {
    if (id != null) {
      throw new IllegalStateException("Id already assigned: " + id);
    }
    id = newId;
  }

  /**
   * Two domain objects are equal when they are of the same type and carry the same id. Objects that
   * have not been saved yet are equal only to themselves.
   */
  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    var other = (DomainObject) o;
    return id != null && id.equals(other.id);
  }

  /** Based on the type only, so the hash code does not change when the id is assigned. */
  @Override
  public final int hashCode() {
    return getClass().hashCode();
  }
}
