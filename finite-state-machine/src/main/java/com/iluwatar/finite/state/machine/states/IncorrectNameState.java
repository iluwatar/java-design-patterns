package com.iluwatar.finite.state.machine.states;

import com.iluwatar.finite.state.machine.AutomatonInterfaceI;
import com.iluwatar.finite.state.machine.DataModel;
import com.iluwatar.finite.state.machine.Event;
import com.iluwatar.finite.state.machine.EventSink;
import lombok.extern.slf4j.Slf4j;

/**
 * State when current model string is an incorrect name.
 */
@Slf4j
public class IncorrectNameState implements AutomatonInterfaceI {

  EventSink eventSink;
  DataModel model;

  public IncorrectNameState(EventSink eventSink, DataModel model) {
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
    model.addCharacter(character);
  }

  @Override
  public void logStreamNameCorrectness() {
    LOGGER.info("String '{}' is not a correct name -> incorrect character",
        model.getCurrentString());
  }

  @Override
  public boolean isCorrect() {
    return false;
  }
}
