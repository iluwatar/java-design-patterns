package com.iluwatar.finite.state.machine;

/**
 * Finite State Machine Pattern is very similar to the State Pattern.
 * Key difference is decoupling states from graph,
 * which allows us to easily reuse them in different FSMs.
 */
public final class App {
  /**
   * Avoid lint error.
   */
  private App() {
  }

  /**
   * Check if 'John' is a correct name.
   *
   * @param args -> not used
   */
  public static void main(final String[] args) {
    RecognizeCorrectNameStateMachine fsm =
        new RecognizeCorrectNameStateMachine();

    // Correct name is string starting with an uppercase letter
    // being followed by lowercase letters (at least 1)
    fsm.logStreamNameCorrectness(); // Empty name is incorrect
    fsm.inputCharacter('J');

    // Name consisting of only capital letter is incorrect
    fsm.logStreamNameCorrectness();
    fsm.inputCharacter('o');
    fsm.inputCharacter('h');
    fsm.inputCharacter('n');

    // Name == John -> name is correct
    fsm.logStreamNameCorrectness();
    fsm.inputCharacter('1');

    // Name contains an illegal character -> name is incorrect
    fsm.logStreamNameCorrectness();
  }
}
