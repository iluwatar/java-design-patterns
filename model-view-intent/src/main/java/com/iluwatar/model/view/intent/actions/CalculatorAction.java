package com.iluwatar.model.view.intent.actions;

/**
 * Defines what outside interactions can be consumed by view model.
 * */
public interface CalculatorAction {

  /**
   * Makes identifying action trivial.
   *
   * @return subclass tag.
   * */
  String tag();
}

