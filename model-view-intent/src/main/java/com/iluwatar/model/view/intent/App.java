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
package com.iluwatar.model.view.intent;

/**
 * Model-View-Intent is a pattern for implementing user interfaces.
 * Its main advantage over MVVM  which it closely mirrors is a
 * minimal public api with which user events can be exposed to the ViewModel.
 * In case of the MVI every event is exposed by using a single method
 * with 1 argument which implements UserEvent interface.
 * Specific parameters can be expressed as its parameters. In this case,
 * we'll be using MVI to implement a simple calculator
 * with +, -, /, * operations and the ability to set the variable.
 * It's important to note, that every user action happens through the
 * view, we never interact with the ViewModel directly.
 */
public final class App {


  /**
   * To avoid magic value lint error.
   */
  private static final double RANDOM_VARIABLE = 10.0;

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(final String[] args) {
    // create model, view and controller

    // initialize calculator view, output and variable = 0
    var view = new CalculatorView(new CalculatorViewModel());
    var variable1 = RANDOM_VARIABLE;

    // calculator variable = RANDOM_VARIABLE -> 10.0
    view.setVariable(variable1);

    // add calculator variable to output -> calculator output = 10.0
    view.add();
    view.displayTotal();  // display output

    variable1 = 2.0;
    view.setVariable(variable1);  // calculator variable = 2.0

    // subtract calculator variable from output -> calculator output = 8
    view.subtract();

    // divide calculator output by variable -> calculator output = 4.0
    view.divide();

    // multiply calculator output by variable -> calculator output = 8.0
    view.multiply();
    view.displayTotal();
  }

  /**
   * Avoid default constructor lint error.
   */
  private App() {
  }
}
