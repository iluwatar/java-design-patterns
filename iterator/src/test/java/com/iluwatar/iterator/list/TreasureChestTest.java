/**
 * The MIT License Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.iluwatar.iterator.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import com.iluwatar.iterator.Iterator;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Date: 12/14/15 - 2:58 PM
 *
 * @author Jeroen Meulemeester
 */
public class TreasureChestTest {

  /**
   * Create a list of all expected items in the chest.
   *
   * @return The set of all expected items in the chest
   */
  public static List<Object[]> dataProvider() {
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
   * Test if the expected item can be retrieved from the chest using the {@link
   * TreasureChestItemIterator}
   */
  @ParameterizedTest
  @MethodSource("dataProvider")
  public void testIterator(Item expectedItem) {
    final TreasureChest chest = new TreasureChest();
    final Iterator<Item> iterator = chest.iterator(expectedItem.getType());
    assertNotNull(iterator);

    while (iterator.hasNext()) {
      final Item item = iterator.next();
      assertNotNull(item);
      assertEquals(expectedItem.getType(), item.getType());

      final String name = item.toString();
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
  public void testGetItems(Item expectedItem) throws Exception {
    final TreasureChest chest = new TreasureChest();
    final List<Item> items = chest.getItems();
    assertNotNull(items);

    for (final Item item : items) {
      assertNotNull(item);
      assertNotNull(item.getType());
      assertNotNull(item.toString());

      final boolean sameType = expectedItem.getType() == item.getType();
      final boolean sameName = expectedItem.toString().equals(item.toString());
      if (sameType && sameName) {
        return;
      }
    }

    fail("Expected to find item [" + expectedItem + "] in the item list, but we didn't.");

  }

}