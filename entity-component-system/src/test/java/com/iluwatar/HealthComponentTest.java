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

public class HealthComponentTest {

  private HealthComponent healthComponent;

  @BeforeEach
  public void setUp() {
    healthComponent = new HealthComponent(100);
  }

  @Test
  public void testInitialHealth() {
    assertEquals(100, healthComponent.getCurrentHealth(), "Initial health should be 100.");
  }

  @Test
  public void testApplyDamage() {
    healthComponent.applyDamage(30);
    assertEquals(70, healthComponent.getCurrentHealth(), "Health should decrease by 30 after damage.");

    healthComponent.applyDamage(100);
    assertEquals(0, healthComponent.getCurrentHealth(), "Health should not go below 0.");
  }

  @Test
  public void testHeal() {
    healthComponent.applyDamage(50);
    healthComponent.heal(30);
    assertEquals(80, healthComponent.getCurrentHealth(), "Health should increase by 30 after healing.");

    healthComponent.heal(50);
    assertEquals(100, healthComponent.getCurrentHealth(), "Health should not exceed maximum (100).");
  }
  @Test
  public void testUpdateFunction() {
     healthComponent = new HealthComponent(100);

    assertDoesNotThrow(() -> healthComponent.update(1.0f), "update function should not throw an exception");
  }

  @Test
  public void testGetMaxHealth() {
    HealthComponent healthComponent = new HealthComponent(100f);

    assertEquals(100f, healthComponent.getMaxHealth(), "Max health should be 100.");
  }

  @Test
  public void testSetMaxHealth() {
    HealthComponent healthComponent = new HealthComponent(100f);
    healthComponent.setMaxHealth(120f);

    assertEquals(120f, healthComponent.getMaxHealth(), "Max health should be updated to 120.");
  }

  @Test
  public void testIsAlive() {

    HealthComponent healthComponent = new HealthComponent(100f);
    assertTrue(healthComponent.isAlive(), "Entity should be alive initially.");
    healthComponent.applyDamage(100f);

    assertFalse(healthComponent.isAlive(), "Entity should be dead after taking 100 damage.");
  }

  @Test
  public void testSetAlive() {

    HealthComponent healthComponent = new HealthComponent(100f);
    assertTrue(healthComponent.isAlive(), "Entity should be alive initially.");
    healthComponent.setAlive(false);

    assertFalse(healthComponent.isAlive(), "Entity should be dead after setting alive to false.");
  }

  @Test
  public void testSetCurrentHealth() {
    HealthComponent healthComponent = new HealthComponent(100f);
    healthComponent.setCurrentHealth(80f);

    assertEquals(80f, healthComponent.getCurrentHealth(), "Current health should be updated to 80.");
  }
  @Test
  public void testHealthCannotGoAboveMax() {
    healthComponent.heal(150);
    assertEquals(100, healthComponent.getCurrentHealth(), "Health should not exceed the max value (100).");
  }

  @Test
  public void testHealthCannotGoBelowZero() {
    healthComponent.applyDamage(200);
    assertEquals(0, healthComponent.getCurrentHealth(), "Health should not go below 0.");
  }
}
