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
package com.iluwatar.iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * The Iterator pattern is a design pattern in which an iterator is used to traverse a container and
 * access the container's elements. The Iterator pattern decouples algorithms from containers.
 * <p>
 * In this example the Iterator ({@link ItemIterator}) adds abstraction layer on top of a collection
 * ({@link TreasureChest}). This way the collection can change its internal implementation without
 * affecting its clients.
 * 
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Program entry point
   * 
   * @param args command line args
   */
  public static void main(String[] args) {
    TreasureChest chest = new TreasureChest();

    ItemIterator ringIterator = chest.iterator(ItemType.RING);
    while (ringIterator.hasNext()) {
      LOGGER.info(ringIterator.next().toString());
    }

    LOGGER.info("----------");

    ItemIterator potionIterator = chest.iterator(ItemType.POTION);
    while (potionIterator.hasNext()) {
      LOGGER.info(potionIterator.next().toString());
    }

    LOGGER.info("----------");

    ItemIterator weaponIterator = chest.iterator(ItemType.WEAPON);
    while (weaponIterator.hasNext()) {
      LOGGER.info(weaponIterator.next().toString());
    }

    LOGGER.info("----------");

    ItemIterator it = chest.iterator(ItemType.ANY);
    while (it.hasNext()) {
      LOGGER.info(it.next().toString());
    }
  }
}
