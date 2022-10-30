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

package com.iluwatar.facet;

import com.iluwatar.facet.dragon.DragonFacet;
import lombok.extern.slf4j.Slf4j;

/**
 * Simple knight class. It is given a reference only to a DragonFacet,
 * not a dragon itself. This restricts its access to its functions and
 * disallows some incorrect parameters in the receiveAttack() function.
 */
@Slf4j
public class Knight {
  private final String name;
  private final Attack attack;
  private final DragonFacet dragonFacet;

  /**
   * Simple constructor for a Knight.
   *
   * @param name The name of the knight
   * @param attack His type of attack
   * @param dragonFacet The reference to the dragon wrapped by a facet
   */
  public Knight(String name, Attack attack, DragonFacet dragonFacet) {
    this.name = name;
    this.attack = attack;
    this.dragonFacet = dragonFacet;
  }

  /**
   * Try to attack the dragon through the facet.
   */
  public void attackDragon() {
    int oldHealth = dragonFacet.getHealth();
    dragonFacet.receiveAttack(attack);
    if (oldHealth == dragonFacet.getHealth()) {
      LOGGER.info("{}: Darn it! {} did nothing.", name, attack);
    } else {
      LOGGER.info("{}: Huzzah! {} hurt that dastardly dragon.", name, attack);
    }
  }
}
