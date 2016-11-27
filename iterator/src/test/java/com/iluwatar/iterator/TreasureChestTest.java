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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Date: 12/14/15 - 2:58 PM
 *
 * @author Jeroen Meulemeester
 */
@RunWith(Parameterized.class)
public class TreasureChestTest {

  /**
   * Create a list of all expected items in the chest.
   *
   * @return The set of all expected items in the chest
   */
  @Parameterized.Parameters
  public static List<Object[]> data() {
    final List<Object[]> parameters = new ArrayList<>();
    parameters.add(new Object[]{new Item(ItemType.POTION, "Potion of courage")});
    parameters.add(new Object[]{new Item(ItemType.RING, "Ring of shadows")});
    parameters.add(new Object[]{new Item(ItemType.POTION, "Potion of wisdom")});
    parameters.add(new Object[]{new Item(ItemType.POTION, "Potion of blood")});
    parameters.add(new Object[]{new Item(ItemType.WEAPON, "Sword of silver +1")});
    parameters.add(new Object[]{new Item(ItemType.POTION, "Potion of rust")});
    parameters.add(new Object[]{new Item(ItemType.POTION, "Potion of healing")});
    parameters.add(new Object[]{new Item(ItemType.RING, "Ring of armor")});
    parameters.add(new Object[]{new Item(ItemType.WEAPON, "Steel halberd")});
    parameters.add(new Object[]{new Item(ItemType.WEAPON, "Dagger of poison")});
    return parameters;
  }

  /**
   * One of the expected items in the chest
   */
  private final Item expectedItem;

  /**
   * Create a new test instance, test if the given expected item can be retrieved from the chest
   *
   * @param expectedItem One of the items that should be in the chest
   */
  public TreasureChestTest(final Item expectedItem) {
    this.expectedItem = expectedItem;
  }

  /**
   * Test if the expected item can be retrieved from the chest using the {@link ItemIterator}
   */
  @Test
  public void testIterator() {
    final TreasureChest chest = new TreasureChest();
    final ItemIterator iterator = chest.iterator(expectedItem.getType());
    assertNotNull(iterator);

    while (iterator.hasNext()) {
      final Item item = iterator.next();
      assertNotNull(item);
      assertEquals(this.expectedItem.getType(), item.getType());

      final String name = item.toString();
      assertNotNull(name);
      if (this.expectedItem.toString().equals(name)) {
        return;
      }
    }

    fail("Expected to find item [" + this.expectedItem + "] using iterator, but we didn't.");

  }

  /**
   * Test if the expected item can be retrieved from the chest using the {@link
   * TreasureChest#getItems()} method
   */
  @Test
  public void testGetItems() throws Exception {
    final TreasureChest chest = new TreasureChest();
    final List<Item> items = chest.getItems();
    assertNotNull(items);

    for (final Item item : items) {
      assertNotNull(item);
      assertNotNull(item.getType());
      assertNotNull(item.toString());

      final boolean sameType = this.expectedItem.getType() == item.getType();
      final boolean sameName = this.expectedItem.toString().equals(item.toString());
      if (sameType && sameName) {
        return;
      }
    }

    fail("Expected to find item [" + this.expectedItem + "] in the item list, but we didn't.");

  }

}