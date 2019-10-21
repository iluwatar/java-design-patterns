/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Date: 12/20/15 - 2:10 PM
 *
 * @author Jeroen Meulemeester
 */
public class GiantModelTest {

  /**
   * Verify if the health value is set properly though the constructor and setter
   */
  @Test
  public void testSetHealth() {
    final GiantModel model = new GiantModel(Health.HEALTHY, Fatigue.ALERT, Nourishment.SATURATED);
    assertEquals(Health.HEALTHY, model.getHealth());
    for (final Health health : Health.values()) {
      model.setHealth(health);
      assertEquals(health, model.getHealth());
      assertEquals("The giant looks " + health.toString() + ", alert and saturated.", model.toString());
    }
  }

  /**
   * Verify if the fatigue level is set properly though the constructor and setter
   */
  @Test
  public void testSetFatigue() {
    final GiantModel model = new GiantModel(Health.HEALTHY, Fatigue.ALERT, Nourishment.SATURATED);
    assertEquals(Fatigue.ALERT, model.getFatigue());
    for (final Fatigue fatigue : Fatigue.values()) {
      model.setFatigue(fatigue);
      assertEquals(fatigue, model.getFatigue());
      assertEquals("The giant looks healthy, " + fatigue.toString() + " and saturated.", model.toString());
    }
  }

  /**
   * Verify if the nourishment level is set properly though the constructor and setter
   */
  @Test
  public void testSetNourishment() {
    final GiantModel model = new GiantModel(Health.HEALTHY, Fatigue.ALERT, Nourishment.SATURATED);
    assertEquals(Nourishment.SATURATED, model.getNourishment());
    for (final Nourishment nourishment : Nourishment.values()) {
      model.setNourishment(nourishment);
      assertEquals(nourishment, model.getNourishment());
      assertEquals("The giant looks healthy, alert and " + nourishment.toString() + ".", model.toString());
    }
  }

}
