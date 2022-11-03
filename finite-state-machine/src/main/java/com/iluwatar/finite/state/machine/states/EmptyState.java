package com.iluwatar.finite.state.machine.states;

import com.iluwatar.finite.state.machine.AutomatonInterfaceI;
import com.iluwatar.finite.state.machine.DataModel;
import com.iluwatar.finite.state.machine.Event;
import com.iluwatar.finite.state.machine.EventSink;
import lombok.extern.slf4j.Slf4j;

/**
 * State when current model string is empty.
 */
@Slf4j
public class EmptyState implements AutomatonInterfaceI {

  EventSink eventSink;
  DataModel model;

  public EmptyState(EventSink eventSink, DataModel model) {
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
    // check if character is an uppercase letter
    if ((int) 'A' <= (int) character && (int) character <= (int) 'Z') {
      eventSink.castEvent(Event.CORRECT);
    } else {
      eventSink.castEvent(Event.INCORRECT);
    }

    model.addCharacter(character);
  }

  @Override
  public void logStreamNameCorrectness() {
    LOGGER.info("String '{}' is not a correct name -> it's empty", model.getCurrentString());
  }

  @Override
  public boolean isCorrect() {
    return false;
  }
}