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
package com.iluwatar.factorykit.factorykit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.factorykit.Axe;
import com.iluwatar.factorykit.Spear;
import com.iluwatar.factorykit.Sword;
import com.iluwatar.factorykit.Weapon;
import com.iluwatar.factorykit.WeaponFactory;
import com.iluwatar.factorykit.WeaponType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test Factory Kit Pattern
 */
class FactoryKitTest {

  private WeaponFactory factory;

  @BeforeEach
  void init() {
    factory = WeaponFactory.factory(builder -> {
      builder.add(WeaponType.SPEAR, Spear::new);
      builder.add(WeaponType.AXE, Axe::new);
      builder.add(WeaponType.SWORD, Sword::new);
    });
  }

  /**
   * Testing {@link WeaponFactory} to produce a SPEAR asserting that the Weapon is an instance of
   * {@link Spear}
   */
  @Test
  void testSpearWeapon() {
    var weapon = factory.create(WeaponType.SPEAR);
    verifyWeapon(weapon, Spear.class);
  }

  /**
   * Testing {@link WeaponFactory} to produce a AXE asserting that the Weapon is an instance of
   * {@link Axe}
   */
  @Test
  void testAxeWeapon() {
    var weapon = factory.create(WeaponType.AXE);
    verifyWeapon(weapon, Axe.class);
  }


  /**
   * Testing {@link WeaponFactory} to produce a SWORD asserting that the Weapon is an instance of
   * {@link Sword}
   */
  @Test
  void testWeapon() {
    var weapon = factory.create(WeaponType.SWORD);
    verifyWeapon(weapon, Sword.class);
  }

  /**
   * This method asserts that the weapon object that is passed is an instance of the clazz
   *
   * @param weapon weapon object which is to be verified
   * @param clazz  expected class of the weapon
   */
  private void verifyWeapon(Weapon weapon, Class<?> clazz) {
    assertTrue(clazz.isInstance(weapon), "Weapon must be an object of: " + clazz.getName());
  }
}
