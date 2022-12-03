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
package com.iluwatar.featuretoggle.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the lists of users of different groups paid and free. Used to demonstrate the tiered
 * example of feature toggle. Allowing certain features to be available to only certain groups of
 * users.
 *
 * @see User
 */
public class UserGroup {

  private static final List<User> freeGroup = new ArrayList<>();
  private static final List<User> paidGroup = new ArrayList<>();

  /**
   * Add the passed {@link User} to the free user group list.
   *
   * @param user {@link User} to be added to the free group
   * @throws IllegalArgumentException when user is already added to the paid group
   * @see User
   */
  public static void addUserToFreeGroup(final User user) throws IllegalArgumentException {
    if (paidGroup.contains(user)) {
      throw new IllegalArgumentException("User already member of paid group.");
    } else {
      if (!freeGroup.contains(user)) {
        freeGroup.add(user);
      }
    }
  }

  /**
   * Add the passed {@link User} to the paid user group list.
   *
   * @param user {@link User} to be added to the paid group
   * @throws IllegalArgumentException when the user is already added to the free group
   * @see User
   */
  public static void addUserToPaidGroup(final User user) throws IllegalArgumentException {
    if (freeGroup.contains(user)) {
      throw new IllegalArgumentException("User already member of free group.");
    } else {
      if (!paidGroup.contains(user)) {
        paidGroup.add(user);
      }
    }
  }

  /**
   * Method to take a {@link User} to determine if the user is in the {@link UserGroup#paidGroup}.
   *
   * @param user {@link User} to check if they are in the {@link UserGroup#paidGroup}
   * @return true if the {@link User} is in {@link UserGroup#paidGroup}
   */
  public static boolean isPaid(User user) {
    return paidGroup.contains(user);
  }
}
