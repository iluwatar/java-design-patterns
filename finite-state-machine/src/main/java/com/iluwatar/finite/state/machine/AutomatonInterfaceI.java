package com.iluwatar.finite.state.machine;

/**
 * Interface for interaction with FSM.
 */
public interface AutomatonInterfaceI {

  void startNewQuery();

  void inputCharacter(char character);

  void logStreamNameCorrectness();

  boolean isCorrect();
}
