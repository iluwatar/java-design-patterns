/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.flyweight;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * AlchemistShop holds potions on its shelves. It uses PotionFactory to provide the potions.
 */
@Slf4j
public class AlchemistShop {

  private final List<Potion> topShelf;
  private final List<Potion> bottomShelf;

  /**
   * Constructor.
   */
  public AlchemistShop() {
    var factory = new PotionFactory();
    topShelf = List.of(
        factory.createPotion(PotionType.INVISIBILITY),
        factory.createPotion(PotionType.INVISIBILITY),
        factory.createPotion(PotionType.STRENGTH),
        factory.createPotion(PotionType.HEALING),
        factory.createPotion(PotionType.INVISIBILITY),
        factory.createPotion(PotionType.STRENGTH),
        factory.createPotion(PotionType.HEALING),
        factory.createPotion(PotionType.HEALING)
    );
    bottomShelf = List.of(
        factory.createPotion(PotionType.POISON),
        factory.createPotion(PotionType.POISON),
        factory.createPotion(PotionType.POISON),
        factory.createPotion(PotionType.HOLY_WATER),
        factory.createPotion(PotionType.HOLY_WATER)
    );
  }

  /**
   * Get a read-only list of all the items on the top shelf.
   *
   * @return The top shelf potions
   */
  public final List<Potion> getTopShelf() {
    return List.copyOf(this.topShelf);
  }

  /**
   * Get a read-only list of all the items on the bottom shelf.
   *
   * @return The bottom shelf potions
   */
  public final List<Potion> getBottomShelf() {
    return List.copyOf(this.bottomShelf);
  }

  /**
   * Enumerate potions.
   */
  public void enumerate() {
    LOGGER.info("Enumerating top shelf potions\n");
    topShelf.forEach(Potion::drink);
    LOGGER.info("Enumerating bottom shelf potions\n");
    bottomShelf.forEach(Potion::drink);
  }
}
