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

import com.iluwatar.model.view.intent.actions.AdditionCalculatorAction;
import com.iluwatar.model.view.intent.actions.CalculatorAction;
import com.iluwatar.model.view.intent.actions.DivisionCalculatorAction;
import com.iluwatar.model.view.intent.actions.MultiplicationCalculatorAction;
import com.iluwatar.model.view.intent.actions.SetVariableCalculatorAction;
import com.iluwatar.model.view.intent.actions.SubtractionCalculatorAction;

/**
 * Handle transformations to {@link CalculatorModel}
 * based on intercepted {@link CalculatorAction}.
 */
public final class CalculatorViewModel {

  /**
   * Current calculator model (can be changed).
   */
  private CalculatorModel model =
      new CalculatorModel(0.0, 0.0);

  /**
   * Handle calculator action.
   *
   * @param action -> transforms calculator model.
   */
  void handleAction(final CalculatorAction action) {
    switch (action.tag()) {
      case AdditionCalculatorAction.TAG -> add();
      case SubtractionCalculatorAction.TAG -> subtract();
      case MultiplicationCalculatorAction.TAG -> multiply();
      case DivisionCalculatorAction.TAG -> divide();
      case SetVariableCalculatorAction.TAG -> {
        SetVariableCalculatorAction setVariableAction =
                (SetVariableCalculatorAction) action;
        setVariable(setVariableAction.getVariable());
      }
      default -> {
      }
    }
  }

  /**
   * Getter.
   *
   * @return current calculator model.
   */
  public CalculatorModel getCalculatorModel() {
    return model;
  }

  /**
   * Set new calculator model variable.
   *
   * @param variable -> value of new calculator model variable.
   */
  private void setVariable(final Double variable) {
    model = new CalculatorModel(
        variable,
        model.getOutput()
    );
  }

  /**
   * Add variable to model output.
   */
  private void add() {
    model = new CalculatorModel(
        model.getVariable(),
        model.getOutput() + model.getVariable()
    );
  }

  /**
   * Subtract variable from model output.
   */
  private void subtract() {
    model = new CalculatorModel(
        model.getVariable(),
        model.getOutput() - model.getVariable()
    );
  }

  /**
   * Multiply model output by variable.
   */
  private void multiply() {
    model = new CalculatorModel(
        model.getVariable(),
        model.getOutput() * model.getVariable()
    );
  }

  /**
   * Divide model output by variable.
   */
  private void divide() {
    model = new CalculatorModel(
        model.getVariable(),
        model.getOutput() / model.getVariable()
    );
  }
}
