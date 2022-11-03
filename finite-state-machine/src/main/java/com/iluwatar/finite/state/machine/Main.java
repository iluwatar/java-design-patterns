package com.iluwatar.finite.state.machine;

/**
 * Main.
 */
public class Main {

  /**
   * Check if 'John' is a correct name.
   */
  public static void main(String[] args) {
    RecognizeCorrectNameStateMachine fsm = new RecognizeCorrectNameStateMachine();

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