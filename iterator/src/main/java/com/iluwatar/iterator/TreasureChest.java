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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * TreasureChest, the collection class.
 * 
 */
public class TreasureChest {

  private List<Item> items;

  /**
   * Constructor
   */
  public TreasureChest() {
    items = new ArrayList<>();
    items.add(new Item(ItemType.POTION, "Potion of courage"));
    items.add(new Item(ItemType.RING, "Ring of shadows"));
    items.add(new Item(ItemType.POTION, "Potion of wisdom"));
    items.add(new Item(ItemType.POTION, "Potion of blood"));
    items.add(new Item(ItemType.WEAPON, "Sword of silver +1"));
    items.add(new Item(ItemType.POTION, "Potion of rust"));
    items.add(new Item(ItemType.POTION, "Potion of healing"));
    items.add(new Item(ItemType.RING, "Ring of armor"));
    items.add(new Item(ItemType.WEAPON, "Steel halberd"));
    items.add(new Item(ItemType.WEAPON, "Dagger of poison"));
  }

  ItemIterator iterator(ItemType itemType) {
    return new TreasureChestItemIterator(this, itemType);
  }

  /**
   * Get all items
   */
  public List<Item> getItems() {
    List<Item> list = new ArrayList<>();
    list.addAll(items);
    return list;
  }

}
