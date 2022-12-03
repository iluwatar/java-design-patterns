package com.iluwatar.finite.state.machine;

/**
 * Main.
 */
public final class Main {
  /**
   * Avoid lint error.
   */
  private Main() {
  }

  /**
   * Check if 'John' is a correct name.
   *
   * @param args -> not used
   */
  public static void main(final String[] args) {
    RecognizeCorrectNameStateMachine fsm =
        new RecognizeCorrectNameStateMachine();

    fsm.logStreamNameCorrectness();
    fsm.inputCharacter('J');
    fsm.logStreamNameCorrectness();
    fsm.inputCharacter('o');
    fsm.inputCharacter('h');
    fsm.inputCharacter('n');
    fsm.logStreamNameCorrectness();

    fsm.inputCharacter('1');
    fsm.logStreamNameCorrectness();
  }
}
