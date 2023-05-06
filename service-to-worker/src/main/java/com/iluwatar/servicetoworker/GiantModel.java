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
package com.iluwatar.servicetoworker;

import com.iluwatar.model.view.controller.Fatigue;
import com.iluwatar.model.view.controller.Health;
import com.iluwatar.model.view.controller.Nourishment;
import lombok.Getter;

/**
 * GiantModel contains the giant data.
 */
public class GiantModel {

  private final com.iluwatar.model.view.controller.GiantModel model;
  @Getter
  private final String name;

  /**
   * Instantiates a new Giant model.
   *
   * @param name        the name
   * @param health      the health
   * @param fatigue     the fatigue
   * @param nourishment the nourishment
   */
  GiantModel(String name, Health health, Fatigue fatigue, Nourishment nourishment) {
    this.name = name;
    this.model = new com.iluwatar.model.view.controller.GiantModel(health, fatigue,
        nourishment);
  }

  /**
   * Gets health.
   *
   * @return the health
   */
  Health getHealth() {
    return model.getHealth();
  }

  /**
   * Sets health.
   *
   * @param health the health
   */
  void setHealth(Health health) {
    model.setHealth(health);
  }

  /**
   * Gets fatigue.
   *
   * @return the fatigue
   */
  Fatigue getFatigue() {
    return model.getFatigue();
  }

  void setFatigue(Fatigue fatigue) {
    model.setFatigue(fatigue);
  }

  /**
   * Gets nourishment.
   *
   * @return the nourishment
   */
  Nourishment getNourishment() {
    return model.getNourishment();
  }

  /**
   * Sets nourishment.
   *
   * @param nourishment the nourishment
   */
  void setNourishment(Nourishment nourishment) {
    model.setNourishment(nourishment);
  }

  @Override
  public String toString() {
    return String
        .format("Giant %s, The giant looks %s, %s and %s.", name,
            model.getHealth(), model.getFatigue(), model.getNourishment());
  }
}
