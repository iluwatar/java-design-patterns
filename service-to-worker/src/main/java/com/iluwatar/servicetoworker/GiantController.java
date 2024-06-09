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

/**
 * GiantController can update the giant data and redraw it using the view. Singleton object that
 * intercepts all requests and performs common functions.
 */
public class GiantController {

  public Dispatcher dispatcher;

  /**
   * Instantiates a new Giant controller.
   *
   * @param dispatcher the dispatcher
   */
  public GiantController(Dispatcher dispatcher) {
    this.dispatcher = dispatcher;
  }

  /**
   * Sets command to control the dispatcher.
   *
   * @param s     the s
   * @param index the index
   */
  public void setCommand(Command s, int index) {
    dispatcher.performAction(s, index);
  }

  /**
   * Update view. This is a simple implementation, in fact, View can be implemented in a concrete
   * way
   *
   * @param giantModel the giant model
   */
  public void updateView(GiantModel giantModel) {
    dispatcher.updateView(giantModel);
  }
}
