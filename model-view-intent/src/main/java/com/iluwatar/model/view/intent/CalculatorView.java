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
