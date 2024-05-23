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
package com.iluwatar.property;

import static com.iluwatar.property.Character.Type;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * CharacterTest
 *
 */
class CharacterTest {

  @Test
  void testPrototypeStats() {
    final var prototype = new Character();

    for (final var stat : Stats.values()) {
      assertFalse(prototype.has(stat));
      assertNull(prototype.get(stat));

      final var expectedValue = stat.ordinal();
      prototype.set(stat, expectedValue);
      assertTrue(prototype.has(stat));
      assertEquals(expectedValue, prototype.get(stat));

      prototype.remove(stat);
      assertFalse(prototype.has(stat));
      assertNull(prototype.get(stat));
    }

  }

  @Test
  void testCharacterStats() {
    final var prototype = new Character();
    Arrays.stream(Stats.values()).forEach(stat -> prototype.set(stat, stat.ordinal()));

    final var mage = new Character(Type.MAGE, prototype);
    for (final var stat : Stats.values()) {
      final var expectedValue = stat.ordinal();
      assertTrue(mage.has(stat));
      assertEquals(expectedValue, mage.get(stat));
    }
  }

  @Test
  void testToString() {
    final var prototype = new Character();
    prototype.set(Stats.ARMOR, 1);
    prototype.set(Stats.AGILITY, 2);
    prototype.set(Stats.INTELLECT, 3);
    var message = """
            Stats:
             - AGILITY:2
             - ARMOR:1
             - INTELLECT:3
            """;
    assertEquals(message, prototype.toString());

    final var stupid = new Character(Type.ROGUE, prototype);
    stupid.remove(Stats.INTELLECT);
    String expectedStupidString = """
            Character type: ROGUE
            Stats:
             - AGILITY:2
             - ARMOR:1
            """;
    assertEquals(expectedStupidString, stupid.toString());

    final var weak = new Character("weak", prototype);
    weak.remove(Stats.ARMOR);
    String expectedWeakString = """
            Player: weak
            Stats:
             - AGILITY:2
             - INTELLECT:3
            """;
    assertEquals(expectedWeakString, weak.toString());

  }

  @Test
  void testName() {
    final var prototype = new Character();
    prototype.set(Stats.ARMOR, 1);
    prototype.set(Stats.INTELLECT, 2);
    assertNull(prototype.name());

    final var stupid = new Character(Type.ROGUE, prototype);
    stupid.remove(Stats.INTELLECT);
    assertNull(stupid.name());

    final var weak = new Character("weak", prototype);
    weak.remove(Stats.ARMOR);
    assertEquals("weak", weak.name());
  }

  @Test
  void testType() {
    final var prototype = new Character();
    prototype.set(Stats.ARMOR, 1);
    prototype.set(Stats.INTELLECT, 2);
    assertNull(prototype.type());

    final var stupid = new Character(Type.ROGUE, prototype);
    stupid.remove(Stats.INTELLECT);
    assertEquals(Type.ROGUE, stupid.type());

    final var weak = new Character("weak", prototype);
    weak.remove(Stats.ARMOR);
    assertNull(weak.type());
  }

}