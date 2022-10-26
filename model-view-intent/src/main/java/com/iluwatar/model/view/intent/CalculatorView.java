package com.iluwatar.model.view.intent;

import com.iluwatar.model.view.intent.actions.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Exposes changes to the state of calculator to {@link CalculatorViewModel} through {@link CalculatorAction}
 * and displays its updated {@link CalculatorModel}.
 * */
@Slf4j
public class CalculatorView {
  CalculatorViewModel viewModel = new CalculatorViewModel();

  void displayTotal() {
    LOGGER.info("Total value = {}", viewModel.getCalculatorModel().output.toString());
  }

  void add() {
    viewModel.handleAction(new AdditionCalculatorAction());
  }

  void subtract() {
    viewModel.handleAction(new SubtractionCalculatorAction());
  }

  void multiply() {
    viewModel.handleAction(new MultiplicationCalculatorAction());
  }

  void divide() {
    viewModel.handleAction(new DivisionCalculatorAction());
  }

  void setVariable(Double value) {
    viewModel.handleAction(new SetVariableCalculatorAction(value));
  }
}
