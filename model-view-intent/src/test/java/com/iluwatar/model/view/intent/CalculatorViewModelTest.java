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

import com.iluwatar.model.view.intent.actions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorViewModelTest {

  private CalculatorModel modelAfterExecutingActions(List<CalculatorAction> actions) {
    CalculatorViewModel viewModel = new CalculatorViewModel();
    for (CalculatorAction action : actions) {
      viewModel.handleAction(action);
    }
    return viewModel.getCalculatorModel();
  }

  @Test
  void testSetup() {
    CalculatorModel model = modelAfterExecutingActions(new ArrayList<>());
    assertEquals(0, model.getVariable());
    assertEquals(0, model.getOutput());
  }

  @Test
  void testSetVariable() {
    List<CalculatorAction> actions = List.of(
        new SetVariableCalculatorAction(10.0)
    );
    CalculatorModel model = modelAfterExecutingActions(actions);
    assertEquals(10.0, model.getVariable());
    assertEquals(0, model.getOutput());
  }

  @Test
  void testAddition() {
    List<CalculatorAction> actions = List.of(
        new SetVariableCalculatorAction(2.0),
        new AdditionCalculatorAction(),
        new AdditionCalculatorAction(),
        new SetVariableCalculatorAction(7.0),
        new AdditionCalculatorAction()
    );
    CalculatorModel model = modelAfterExecutingActions(actions);
    assertEquals(7.0, model.getVariable());
    assertEquals(11.0, model.getOutput());
  }

  @Test
  void testSubtraction() {
    List<CalculatorAction> actions = List.of(
        new SetVariableCalculatorAction(2.0),
        new AdditionCalculatorAction(),
        new AdditionCalculatorAction(),
        new SubtractionCalculatorAction()
    );
    CalculatorModel model = modelAfterExecutingActions(actions);
    assertEquals(2.0, model.getVariable());
    assertEquals(2.0, model.getOutput());
  }

  @Test
  void testMultiplication() {
    List<CalculatorAction> actions = List.of(
        new SetVariableCalculatorAction(2.0),
        new AdditionCalculatorAction(),
        new AdditionCalculatorAction(),
        new MultiplicationCalculatorAction()
    );
    CalculatorModel model = modelAfterExecutingActions(actions);
    assertEquals(2.0, model.getVariable());
    assertEquals(8.0, model.getOutput());
  }

  @Test
  void testDivision() {
    List<CalculatorAction> actions = List.of(
        new SetVariableCalculatorAction(2.0),
        new AdditionCalculatorAction(),
        new AdditionCalculatorAction(),
        new SetVariableCalculatorAction(2.0),
        new DivisionCalculatorAction()
    );
    CalculatorModel model = modelAfterExecutingActions(actions);
    assertEquals(2.0, model.getVariable());
    assertEquals(2.0, model.getOutput());
  }
}