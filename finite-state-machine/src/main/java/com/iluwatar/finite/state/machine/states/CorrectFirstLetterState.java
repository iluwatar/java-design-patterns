package com.iluwatar.finite.state.machine.states;

import com.iluwatar.finite.state.machine.AutomatonInterfaceI;
import com.iluwatar.finite.state.machine.DataModel;
import com.iluwatar.finite.state.machine.Event;
import com.iluwatar.finite.state.machine.EventSink;
import lombok.extern.slf4j.Slf4j;

/**
 * State when first letter was correct.
 */
@Slf4j
public class CorrectFirstLetterState implements AutomatonInterfaceI {

  EventSink eventSink;
  DataModel model;

  public CorrectFirstLetterState(EventSink eventSink, DataModel model) {
    this.eventSink = eventSink;
    this.model = model;
  }

  @Override
  public void startNewQuery() {
    LOGGER.info("Input cache cleared");
    eventSink.castEvent(Event.CLEAR);
    model.clear();
  }

  @Override
  public void inputCharacter(char character) {
    // check if character is a lowercase letter
    if ((int) 'a' <= (int) character && (int) character <= (int) 'z') {
      eventSink.castEvent(Event.CORRECT);
    } else {
      eventSink.castEvent(Event.INCORRECT);
    }

    model.addCharacter(character);
  }

  @Override
  public void logStreamNameCorrectness() {
    LOGGER.info("String '{}' is not a correct name -> too short", model.getCurrentString());
  }

  @Override
  public boolean isCorrect() {
    return false;
  }
}
