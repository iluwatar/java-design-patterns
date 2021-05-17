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

package com.iluwatar.specification.creature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.iluwatar.specification.property.Color;
import com.iluwatar.specification.property.Mass;
import com.iluwatar.specification.property.Movement;
import com.iluwatar.specification.property.Size;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Date: 12/29/15 - 7:47 PM
 *
 * @author Jeroen Meulemeester
 */
class CreatureTest {

  /**
   * @return The tested {@link Creature} instance and its expected specs
   */
  public static Collection<Object[]> dataProvider() {
    return List.of(
        new Object[]{new Dragon(), "Dragon", Size.LARGE, Movement.FLYING, Color.RED,
            new Mass(39300.0)},
        new Object[]{new Goblin(), "Goblin", Size.SMALL, Movement.WALKING, Color.GREEN,
            new Mass(30.0)},
        new Object[]{new KillerBee(), "KillerBee", Size.SMALL, Movement.FLYING, Color.LIGHT,
            new Mass(6.7)},
        new Object[]{new Octopus(), "Octopus", Size.NORMAL, Movement.SWIMMING, Color.DARK,
            new Mass(12.0)},
        new Object[]{new Shark(), "Shark", Size.NORMAL, Movement.SWIMMING, Color.LIGHT,
            new Mass(500.0)},
        new Object[]{new Troll(), "Troll", Size.LARGE, Movement.WALKING, Color.DARK,
            new Mass(4000.0)}
    );
  }

  @ParameterizedTest
  @MethodSource("dataProvider")
  void testGetName(Creature testedCreature, String name) {
    assertEquals(name, testedCreature.getName());
  }

  @ParameterizedTest
  @MethodSource("dataProvider")
  void testGetSize(Creature testedCreature, String name, Size size) {
    assertEquals(size, testedCreature.getSize());
  }

  @ParameterizedTest
  @MethodSource("dataProvider")
  void testGetMovement(Creature testedCreature, String name, Size size, Movement movement) {
    assertEquals(movement, testedCreature.getMovement());
  }

  @ParameterizedTest
  @MethodSource("dataProvider")
  void testGetColor(Creature testedCreature, String name, Size size, Movement movement,
                    Color color) {
    assertEquals(color, testedCreature.getColor());
  }

  @ParameterizedTest
  @MethodSource("dataProvider")
  void testGetMass(Creature testedCreature, String name, Size size, Movement movement,
                   Color color, Mass mass) {
    assertEquals(mass, testedCreature.getMass());
  }

  @ParameterizedTest
  @MethodSource("dataProvider")
  void testToString(Creature testedCreature, String name, Size size, Movement movement,
                    Color color, Mass mass) {
    final var toString = testedCreature.toString();
    assertNotNull(toString);
    assertEquals(String
        .format("%s [size=%s, movement=%s, color=%s, mass=%s]", name, size, movement, color, mass), toString);
  }
}