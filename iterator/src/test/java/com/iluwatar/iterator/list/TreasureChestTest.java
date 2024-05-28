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
package com.iluwatar.iterator.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * TreasureChestTest
 *
 */
class TreasureChestTest {

  /**
   * Create a list of all expected items in the chest.
   *
   * @return The set of all expected items in the chest
   */
  public static List<Object[]> dataProvider() {
    return List.of(
        new Object[]{new Item(ItemType.POTION, "Potion of courage")},
        new Object[]{new Item(ItemType.RING, "Ring of shadows")},
        new Object[]{new Item(ItemType.POTION, "Potion of wisdom")},
        new Object[]{new Item(ItemType.POTION, "Potion of blood")},
        new Object[]{new Item(ItemType.WEAPON, "Sword of silver +1")},
        new Object[]{new Item(ItemType.POTION, "Potion of rust")},
        new Object[]{new Item(ItemType.POTION, "Potion of healing")},
        new Object[]{new Item(ItemType.RING, "Ring of armor")},
        new Object[]{new Item(ItemType.WEAPON, "Steel halberd")},
        new Object[]{new Item(ItemType.WEAPON, "Dagger of poison")}
    );
  }

  /**
   * Test if the expected item can be retrieved from the chest using the {@link
   * TreasureChestItemIterator}
   */
  @ParameterizedTest
  @MethodSource("dataProvider")
  void testIterator(Item expectedItem) {
    final var chest = new TreasureChest();
    final var iterator = chest.iterator(expectedItem.getType());
    assertNotNull(iterator);

    while (iterator.hasNext()) {
      final var item = iterator.next();
      assertNotNull(item);
      assertEquals(expectedItem.getType(), item.getType());

      final var name = item.toString();
      assertNotNull(name);
      if (expectedItem.toString().equals(name)) {
        return;
      }
    }

    fail("Expected to find item [" + expectedItem + "] using iterator, but we didn't.");

  }

  /**
   * Test if the expected item can be retrieved from the chest using the {@link
   * TreasureChest#getItems()} method
   */
  @ParameterizedTest
  @MethodSource("dataProvider")
  void testGetItems(Item expectedItem) {
    final var chest = new TreasureChest();
    final var items = chest.getItems();
    assertNotNull(items);

    for (final var item : items) {
      assertNotNull(item);
      assertNotNull(item.getType());
      assertNotNull(item.toString());

      final var sameType = expectedItem.getType() == item.getType();
      final var sameName = expectedItem.toString().equals(item.toString());
      if (sameType && sameName) {
        return;
      }
    }

    fail("Expected to find item [" + expectedItem + "] in the item list, but we didn't.");

  }

}