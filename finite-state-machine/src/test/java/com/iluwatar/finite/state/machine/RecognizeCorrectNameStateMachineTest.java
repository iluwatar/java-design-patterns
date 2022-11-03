package com.iluwatar.finite.state.machine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * FixedStepGameLoop unit test class.
 */

public class RecognizeCorrectNameStateMachineTest {

  private RecognizeCorrectNameStateMachine machine;

  @BeforeEach
  public void setup() {
    machine = new RecognizeCorrectNameStateMachine();
  }

  @Test
  void correctlyRecognizeGoodName() {
    machine.inputCharacter('K');
    machine.inputCharacter('i');
    machine.inputCharacter('r');
    machine.inputCharacter('k');

    assert (machine.isCorrect());
  }

  @Test
  void correctlyRecognizeWrongName() {
    machine.inputCharacter('P');
    machine.inputCharacter('1');
    machine.inputCharacter('c');
    machine.inputCharacter('a');
    machine.inputCharacter('r');
    machine.inputCharacter('d');

    assert (!machine.isCorrect());
  }

  @Test
  void correctlyRecognizeNameTooShort() {
    machine.inputCharacter('S');
    assert (!machine.isCorrect());
    machine.inputCharacter('p');
    machine.inputCharacter('o');
    machine.inputCharacter('c');
    machine.inputCharacter('k');

    assert (machine.isCorrect());
  }

  @Test
  void correctlyRecognizeNameIsEmpty() {
    assert (!machine.isCorrect());
  }

  @Test
  void correctlyStartsNewQuery() {
    machine.inputCharacter('1');
    assert (!machine.isCorrect());
    machine.startNewQuery();
    machine.inputCharacter('S');
    machine.inputCharacter('c');
    machine.inputCharacter('o');
    machine.inputCharacter('t');
    machine.inputCharacter('y');
    assert (machine.isCorrect());
  }


}

