package com.iluwatar.finite.state.machine;

/**
 * Interface for interaction with FSM.
 */
public interface AutomatonInterfaceI {

  /**
   * Restarts the FSM.
   * */
  void startNewQuery();

  /**
   * Input new character into FSM.
   *
   * @param character -> value of inputted character.
   * */
  void inputCharacter(Character character);

  /**
   * Log if a name inputted up to now is correct.
   * */
  void logStreamNameCorrectness();

  /**
   * Is a name inputted up to now correct.
   *
   * @return is name created from inputs correct.
   * */
  boolean isCorrect();
}
