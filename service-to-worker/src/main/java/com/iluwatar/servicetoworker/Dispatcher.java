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

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * The type Dispatcher, which encapsulates worker and view selection based on request information
 * and/or an internal navigation model.
 */
public class Dispatcher {

  @Getter
  private final GiantView giantView;
  private final List<Action> actions;

  /**
   * Instantiates a new Dispatcher.
   *
   * @param giantView the giant view
   */
  public Dispatcher(GiantView giantView) {
    this.giantView = giantView;
    this.actions = new ArrayList<>();
  }

  /**
   * Add an action.
   *
   * @param action the action
   */
  void addAction(Action action) {
    actions.add(action);
  }

  /**
   * Perform an action.
   *
   * @param s           the s
   * @param actionIndex the action index
   */
  public void performAction(Command s, int actionIndex) {
    actions.get(actionIndex).updateModel(s);
  }

  /**
   * Update view.
   *
   * @param giantModel the giant model
   */
  public void updateView(GiantModel giantModel) {
    giantView.displayGiant(giantModel);
  }

}
