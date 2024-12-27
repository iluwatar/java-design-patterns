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
package com.iluwatar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

 class HealthComponentTest {

  private HealthComponent healthComponent;

  @BeforeEach
 void setUp() {
    healthComponent = new HealthComponent(100);
  }

  @Test
 void testInitialHealth() {
    assertEquals(100, healthComponent.getCurrentHealth(), "Initial health should be 100.");
  }

  @Test
 void testApplyDamage() {
    healthComponent.applyDamage(30);
    assertEquals(70, healthComponent.getCurrentHealth(), "Health should decrease by 30 after damage.");

    healthComponent.applyDamage(100);
    assertEquals(0, healthComponent.getCurrentHealth(), "Health should not go below 0.");
  }

  @Test
 void testHeal() {
    healthComponent.applyDamage(50);
    healthComponent.heal(30);
    assertEquals(80, healthComponent.getCurrentHealth(), "Health should increase by 30 after healing.");

    healthComponent.heal(50);
    assertEquals(100, healthComponent.getCurrentHealth(), "Health should not exceed maximum (100).");
  }
  @Test
    void testUpdateFunction() {
     healthComponent = new HealthComponent(100);

    assertDoesNotThrow(() -> healthComponent.update(1.0f), "update function should not throw an exception");
  }

  @Test
    void testGetMaxHealth() {
    HealthComponent TesthealthComponent = new HealthComponent(100f);

    assertEquals(100f, TesthealthComponent.getMaxHealth(), "Max health should be 100.");
  }

  @Test
    void testSetMaxHealth() {
    HealthComponent TesthealthComponent = new HealthComponent(100f);
    healthComponent.setMaxHealth(120f);

    assertEquals(120f, TesthealthComponent.getMaxHealth(), "Max health should be updated to 120.");
  }

  @Test
    void testIsAlive() {

    HealthComponent TesthealthComponent = new HealthComponent(100f);
    assertTrue(TesthealthComponent.isAlive(), "Entity should be alive initially.");
    TesthealthComponent.applyDamage(100f);

    assertFalse(TesthealthComponent.isAlive(), "Entity should be dead after taking 100 damage.");
  }

  @Test
    void testSetAlive() {

    HealthComponent TesthealthComponent = new HealthComponent(100f);
    assertTrue(TesthealthComponent.isAlive(), "Entity should be alive initially.");
    TesthealthComponent.setAlive(false);

    assertFalse(TesthealthComponent.isAlive(), "Entity should be dead after setting alive to false.");
  }

  @Test
    void testSetCurrentHealth() {
    HealthComponent TesthealthComponent = new HealthComponent(100f);
    TesthealthComponent.setCurrentHealth(80f);

    assertEquals(80f, TesthealthComponent.getCurrentHealth(), "Current health should be updated to 80.");
  }
  @Test
    void testHealthCannotGoAboveMax() {
    healthComponent.heal(150);
    assertEquals(100, healthComponent.getCurrentHealth(), "Health should not exceed the max value (100).");
  }

  @Test
    void testHealthCannotGoBelowZero() {
    healthComponent.applyDamage(200);
    assertEquals(0, healthComponent.getCurrentHealth(), "Health should not go below 0.");
  }
}
