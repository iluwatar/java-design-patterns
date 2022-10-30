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

package com.iluwatar.facet.dragon;

import com.iluwatar.facet.Attack;

/**
 * Dragon object needs to be protected, since the other objects shouldn't be
 * allowed to edit its health. That is why it is in its own package with
 * {@link DragonFacet} and all the functions have no access level modifiers,
 * meaning only classes in the same package have access.
 */
public class Dragon {
  private int health;

  public Dragon(int health) {
    this.health = health;
  }

  int facetedGetHealth() {
    return health;
  }

  /**
   * This has no access level modifier, so only other classes within
   * the same package can access this function. This protects the knight
   * from being able to arbitrarily change the dragon's health.
   *
   * @param health The new health of the dragon
   */
  void setHealth(int health) {
    this.health = health;
  }

  void facetedReceiveAttack(Attack attack) {
    switch (attack) {
      case ARROW:
        health -= 10;
        break;
      case WATER_PISTOL:
        health -= 15;
        break;
      default:
        health -= 5;
    }
  }
}
