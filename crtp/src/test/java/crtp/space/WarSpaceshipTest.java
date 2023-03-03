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
package crtp.space;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * tests {@link WarSpaceship}.
 */
class WarSpaceshipTest {

  @Test
  void testMissingName() {
    assertThrows(IllegalArgumentException.class, () -> new WarSpaceship.Builder(null, 100));
  }

  @Test
  void testNegativeOrNullFuelCapacity() {
    assertThrows(IllegalArgumentException.class, () -> new WarSpaceship.Builder("Gravity", -1));
    assertThrows(IllegalArgumentException.class, () -> new WarSpaceship.Builder("Gravity", 0));
  }

  @Test
  void testBuildWarShip() {
    final var ship = new WarSpaceship.Builder("Gravity", 500)
        .withSpeed(Speed.HALF_C)
        .withWeapon(Weapon.PLASMA_CANNON)
        .withSize(Size.LARGE)
        .build();

    assertNotNull(ship);
    assertNotNull(ship.toString());
    assertEquals(Speed.HALF_C, ship.getSpeed());
    assertEquals(Weapon.PLASMA_CANNON, ship.getWeapon());
    assertEquals(Size.LARGE, ship.getSize());
  }


}