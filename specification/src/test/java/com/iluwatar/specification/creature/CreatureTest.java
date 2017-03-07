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
package com.iluwatar.specification.creature;

import com.iluwatar.specification.property.Color;
import com.iluwatar.specification.property.Movement;
import com.iluwatar.specification.property.Size;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Date: 12/29/15 - 7:47 PM
 *
 * @author Jeroen Meulemeester
 */
@RunWith(Parameterized.class)
public class CreatureTest {

  /**
   * @return The tested {@link Creature} instance and its expected specs
   */
  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(
            new Object[]{new Dragon(), "Dragon", Size.LARGE, Movement.FLYING, Color.RED},
            new Object[]{new Goblin(), "Goblin", Size.SMALL, Movement.WALKING, Color.GREEN},
            new Object[]{new KillerBee(), "KillerBee", Size.SMALL, Movement.FLYING, Color.LIGHT},
            new Object[]{new Octopus(), "Octopus", Size.NORMAL, Movement.SWIMMING, Color.DARK},
            new Object[]{new Shark(), "Shark", Size.NORMAL, Movement.SWIMMING, Color.LIGHT},
            new Object[]{new Troll(), "Troll", Size.LARGE, Movement.WALKING, Color.DARK}
    );
  }

  /**
   * The tested creature
   */
  private final Creature testedCreature;

  /**
   * The expected name of the tested creature
   */
  private final String name;

  /**
   * The expected size of the tested creature
   */
  private final Size size;

  /**
   * The expected movement type of the tested creature
   */
  private final Movement movement;

  /**
   * The expected color of the tested creature
   */
  private final Color color;

  /**
   * @param testedCreature The tested creature
   * @param name           The expected name of the creature
   * @param size           The expected size of the creature
   * @param movement       The expected movement type of the creature
   * @param color          The expected color of the creature
   */
  public CreatureTest(final Creature testedCreature, final String name, final Size size,
                      final Movement movement, final Color color) {
    this.testedCreature = testedCreature;
    this.name = name;
    this.size = size;
    this.movement = movement;
    this.color = color;
  }


  @Test
  public void testGetName() throws Exception {
    assertEquals(this.name, this.testedCreature.getName());
  }

  @Test
  public void testGetSize() throws Exception {
    assertEquals(this.size, this.testedCreature.getSize());
  }

  @Test
  public void testGetMovement() throws Exception {
    assertEquals(this.movement, this.testedCreature.getMovement());
  }

  @Test
  public void testGetColor() throws Exception {
    assertEquals(this.color, this.testedCreature.getColor());
  }

  @Test
  public void testToString() throws Exception {
    final String toString = this.testedCreature.toString();
    assertNotNull(toString);
    assertEquals(
            String.format("%s [size=%s, movement=%s, color=%s]", name, size, movement, color),
            toString
    );
  }
}