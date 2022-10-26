package com.iluwatar.model.view.intent;

import com.iluwatar.model.view.intent.actions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CalculatorViewModelTest {

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
    assert model.variable == 0 && model.output == 0;
  }

  @Test
  void testSetVariable() {
    List<CalculatorAction> actions = List.of(
        new SetVariableCalculatorAction(10.0)
    );
    CalculatorModel model = modelAfterExecutingActions(actions);
    assert model.variable == 10.0 && model.output == 0;
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
    assert model.variable == 7.0 && model.output == 11.0;
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
    assert model.variable == 2.0 && model.output == 2.0;
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
    assert model.variable == 2.0 && model.output == 8.0;
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
    assert model.variable == 2.0 && model.output == 2.0;
  }
}
