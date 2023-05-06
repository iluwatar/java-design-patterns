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

/**
 * The type Action (Worker), which can process user input and perform a specific update on the
 * model.
 */
public class Action {

  public GiantModel giant;

  /**
   * Instantiates a new Action.
   *
   * @param giant the giant
   */
  public Action(GiantModel giant) {
    this.giant = giant;
  }

  /**
   * Update model based on command.
   *
   * @param command the command
   */
  public void updateModel(Command command) {
    setFatigue(command.fatigue());
    setHealth(command.health());
    setNourishment(command.nourishment());
  }

  /**
   * Sets health.
   *
   * @param health the health
   */
  public void setHealth(Health health) {
    giant.setHealth(health);
  }

  /**
   * Sets fatigue.
   *
   * @param fatigue the fatigue
   */
  public void setFatigue(Fatigue fatigue) {
    giant.setFatigue(fatigue);
  }

  /**
   * Sets nourishment.
   *
   * @param nourishment the nourishment
   */
  public void setNourishment(Nourishment nourishment) {
    giant.setNourishment(nourishment);
  }
}
