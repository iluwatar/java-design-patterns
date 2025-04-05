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
package com.iluwatar.model.view.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** GiantModelTest */
class GiantModelTest {

  /** Verify if the health value is set properly though the constructor and setter */
  @Test
  void testSetHealth() {
    final var model = new GiantModel(Health.HEALTHY, Fatigue.ALERT, Nourishment.SATURATED);
    assertEquals(Health.HEALTHY, model.getHealth());
    var messageFormat = "The giant looks %s, alert and saturated.";
    for (final var health : Health.values()) {
      model.setHealth(health);
      assertEquals(health, model.getHealth());
      assertEquals(String.format(messageFormat, health), model.toString());
    }
  }

  /** Verify if the fatigue level is set properly though the constructor and setter */
  @Test
  void testSetFatigue() {
    final var model = new GiantModel(Health.HEALTHY, Fatigue.ALERT, Nourishment.SATURATED);
    assertEquals(Fatigue.ALERT, model.getFatigue());
    var messageFormat = "The giant looks healthy, %s and saturated.";
    for (final var fatigue : Fatigue.values()) {
      model.setFatigue(fatigue);
      assertEquals(fatigue, model.getFatigue());
      assertEquals(String.format(messageFormat, fatigue), model.toString());
    }
  }

  /** Verify if the nourishment level is set properly though the constructor and setter */
  @Test
  void testSetNourishment() {
    final var model = new GiantModel(Health.HEALTHY, Fatigue.ALERT, Nourishment.SATURATED);
    assertEquals(Nourishment.SATURATED, model.getNourishment());
    var messageFormat = "The giant looks healthy, alert and %s.";
    for (final var nourishment : Nourishment.values()) {
      model.setNourishment(nourishment);
      assertEquals(nourishment, model.getNourishment());
      assertEquals(String.format(messageFormat, nourishment), model.toString());
    }
  }
}
