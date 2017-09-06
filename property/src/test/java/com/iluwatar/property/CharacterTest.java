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
package com.iluwatar.property;

import org.junit.Test;

import static com.iluwatar.property.Character.Type;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Date: 12/28/15 - 7:46 PM
 *
 * @author Jeroen Meulemeester
 */
public class CharacterTest {

  @Test
  public void testPrototypeStats() throws Exception {
    final Character prototype = new Character();

    for (final Stats stat : Stats.values()) {
      assertFalse(prototype.has(stat));
      assertNull(prototype.get(stat));

      final Integer expectedValue = stat.ordinal();
      prototype.set(stat, expectedValue);
      assertTrue(prototype.has(stat));
      assertEquals(expectedValue, prototype.get(stat));

      prototype.remove(stat);
      assertFalse(prototype.has(stat));
      assertNull(prototype.get(stat));
    }

  }

  @Test
  public void testCharacterStats() throws Exception {
    final Character prototype = new Character();
    for (final Stats stat : Stats.values()) {
      prototype.set(stat, stat.ordinal());
    }

    final Character mage = new Character(Type.MAGE, prototype);
    for (final Stats stat : Stats.values()) {
      final Integer expectedValue = stat.ordinal();
      assertTrue(mage.has(stat));
      assertEquals(expectedValue, mage.get(stat));
    }
  }

  @Test
  public void testToString() throws Exception {
    final Character prototype = new Character();
    prototype.set(Stats.ARMOR, 1);
    prototype.set(Stats.AGILITY, 2);
    prototype.set(Stats.INTELLECT, 3);
    assertEquals("Stats:\n - AGILITY:2\n - ARMOR:1\n - INTELLECT:3\n", prototype.toString());

    final Character stupid = new Character(Type.ROGUE, prototype);
    stupid.remove(Stats.INTELLECT);
    assertEquals("Character type: ROGUE\nStats:\n - AGILITY:2\n - ARMOR:1\n", stupid.toString());

    final Character weak = new Character("weak", prototype);
    weak.remove(Stats.ARMOR);
    assertEquals("Player: weak\nStats:\n - AGILITY:2\n - INTELLECT:3\n", weak.toString());

  }

  @Test
  public void testName() throws Exception {
    final Character prototype = new Character();
    prototype.set(Stats.ARMOR, 1);
    prototype.set(Stats.INTELLECT, 2);
    assertNull(prototype.name());

    final Character stupid = new Character(Type.ROGUE, prototype);
    stupid.remove(Stats.INTELLECT);
    assertNull(stupid.name());

    final Character weak = new Character("weak", prototype);
    weak.remove(Stats.ARMOR);
    assertEquals("weak", weak.name());
  }

  @Test
  public void testType() throws Exception {
    final Character prototype = new Character();
    prototype.set(Stats.ARMOR, 1);
    prototype.set(Stats.INTELLECT, 2);
    assertNull(prototype.type());

    final Character stupid = new Character(Type.ROGUE, prototype);
    stupid.remove(Stats.INTELLECT);
    assertEquals(Type.ROGUE, stupid.type());

    final Character weak = new Character("weak", prototype);
    weak.remove(Stats.ARMOR);
    assertNull(weak.type());
  }

}