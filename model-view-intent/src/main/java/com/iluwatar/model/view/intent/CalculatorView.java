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
import com.iluwatar.model.view.intent.actions.DivisionCalculatorAction;
import com.iluwatar.model.view.intent.actions.MultiplicationCalculatorAction;
import com.iluwatar.model.view.intent.actions.SetVariableCalculatorAction;
import com.iluwatar.model.view.intent.actions.SubtractionCalculatorAction;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Exposes changes to the state of calculator
 * to {@link CalculatorViewModel} through
 * {@link com.iluwatar.model.view.intent.actions.CalculatorAction}
 * and displays its updated {@link CalculatorModel}.
 */
@Slf4j
@Data
public class CalculatorView {

  /**
   * View model param handling the operations.
   */
  @Getter
  private final CalculatorViewModel viewModel;

  /**
   * Display current view model output with logger.
   */
  void displayTotal() {
    LOGGER.info(
        "Total value = {}",
        viewModel.getCalculatorModel().getOutput().toString()
    );
  }

  /**
   * Handle addition action.
   */
  void add() {
    viewModel.handleAction(new AdditionCalculatorAction());
  }

  /**
   * Handle subtraction action.
   */
  void subtract() {
    viewModel.handleAction(new SubtractionCalculatorAction());
  }

  /**
   * Handle multiplication action.
   */
  void multiply() {
    viewModel.handleAction(new MultiplicationCalculatorAction());
  }

  /**
   * Handle division action.
   */
  void divide() {
    viewModel.handleAction(new DivisionCalculatorAction());
  }

  /**
   * Handle setting new variable action.
   *
   * @param value -> new calculator variable.
   */
  void setVariable(final Double value) {
    viewModel.handleAction(new SetVariableCalculatorAction(value));
  }
}
