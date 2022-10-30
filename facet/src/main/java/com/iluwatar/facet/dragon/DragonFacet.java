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
 * The facet class which acts as an interface/wrapper for {@link Dragon}.
 * It only allows access to the methods with names beginning
 * with 'faceted', and also checks in receiveAttack() what
 * the type of attack is, so that it can filter some illegal
 * values.
 */
public class DragonFacet {
  private final Dragon dragon;

  public DragonFacet(Dragon dragon) {
    this.dragon = dragon;
  }

  public int getHealth() {
    return dragon.facetedGetHealth();
  }

  /**
   * This performs a check of the attack, and for certain illegal values
   * (namely FLAME_THROWER and SWORD), the attack will not even alert
   * the dragon.
   *
   * @param attack The attack which is attempted against dragon
   */
  public void receiveAttack(Attack attack) {
    if (attack == Attack.WATER_PISTOL || attack == Attack.ARROW) {
      dragon.facetedReceiveAttack(attack);
    }
  }
}
