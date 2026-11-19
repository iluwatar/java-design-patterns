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
package com.iluwatar.functionalcoreimperativeshell.core;

/**
 * A library member and the fines they still owe, in cents. Immutable: every change returns a new
 * member.
 *
 * @param name the member's unique name
 * @param finesCents the unpaid fines, in cents
 */
public record Member(String name, long finesCents) {

  /** Validate the member's state. */
  public Member {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Member name must not be blank");
    }
    if (finesCents < 0) {
      throw new IllegalArgumentException("Fines must not be negative");
    }
  }

  /** Create a member who owes nothing. */
  public static Member newMember(String name) {
    return new Member(name, 0);
  }

  /** Return a copy of this member with the given fine added. */
  public Member withFineAdded(long fineCents) {
    return new Member(name, finesCents + fineCents);
  }
}
