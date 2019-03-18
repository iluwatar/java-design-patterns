/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * AlchemistShop holds potions on its shelves. It uses PotionFactory to provide the potions.
 * 
 */
public class AlchemistShop {

  private static final Logger LOGGER = LoggerFactory.getLogger(AlchemistShop.class);

  private List<Potion> topShelf;
  private List<Potion> bottomShelf;

  /**
   * Constructor
   */
  public AlchemistShop() {
    topShelf = new ArrayList<>();
    bottomShelf = new ArrayList<>();
    fillShelves();
  }

  private void fillShelves() {

    PotionFactory factory = new PotionFactory();

    topShelf.add(factory.createPotion(PotionType.INVISIBILITY));
    topShelf.add(factory.createPotion(PotionType.INVISIBILITY));
    topShelf.add(factory.createPotion(PotionType.STRENGTH));
    topShelf.add(factory.createPotion(PotionType.HEALING));
    topShelf.add(factory.createPotion(PotionType.INVISIBILITY));
    topShelf.add(factory.createPotion(PotionType.STRENGTH));
    topShelf.add(factory.createPotion(PotionType.HEALING));
    topShelf.add(factory.createPotion(PotionType.HEALING));

    bottomShelf.add(factory.createPotion(PotionType.POISON));
    bottomShelf.add(factory.createPotion(PotionType.POISON));
    bottomShelf.add(factory.createPotion(PotionType.POISON));
    bottomShelf.add(factory.createPotion(PotionType.HOLY_WATER));
    bottomShelf.add(factory.createPotion(PotionType.HOLY_WATER));
  }

  /**
   * Get a read-only list of all the items on the top shelf
   *
   * @return The top shelf potions
   */
  public final List<Potion> getTopShelf() {
    return Collections.unmodifiableList(this.topShelf);
  }

  /**
   * Get a read-only list of all the items on the bottom shelf
   *
   * @return The bottom shelf potions
   */
  public final List<Potion> getBottomShelf() {
    return Collections.unmodifiableList(this.bottomShelf);
  }

  /**
   * Enumerate potions
   */
  public void enumerate() {

    LOGGER.info("Enumerating top shelf potions\n");

    for (Potion p : topShelf) {
      p.drink();
    }

    LOGGER.info("Enumerating bottom shelf potions\n");

    for (Potion p : bottomShelf) {
      p.drink();
    }
  }
}
