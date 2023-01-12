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

  /**
   * Event sink for sending state changes.
   * */
  private final EventSink eventSink;

  /**
   * Event sink for current inputs value.
   * */
  private final DataModel model;

  /**
   * Constructor.
   *
   * @param newEventSink -> evenSink value.
   * @param newModel -> model value.
   * */
  public EmptyState(
      final EventSink newEventSink,
      final DataModel newModel
  ) {
    this.eventSink = newEventSink;
    this.model = newModel;
  }

  /**
   * Restarts the FSM.
   * */
  @Override
  public void startNewQuery() {
    LOGGER.info("Input cache cleared");
    eventSink.castEvent(Event.CLEAR);
    model.clear();
  }

  /**
   * Input new character into FSM.
   *
   * @param character -> value of inputted character.
   * */
  @Override
  public void inputCharacter(final Character character) {
    // check if character is an uppercase letter
    if (Character.isUpperCase(character)) {
      eventSink.castEvent(Event.CORRECT);
    } else {
      eventSink.castEvent(Event.INCORRECT);
    }

    model.addCharacter(character);
  }

  /**
   * Log if a name inputted up to now is correct.
   * */
  @Override
  public void logStreamNameCorrectness() {
    LOGGER.info(
        "String '{}' is not a correct name -> it's empty",
        model.getCurrentString()
    );
  }

  /**
   * Is a name inputted up to now correct.
   *
   * @return is name created from inputs correct.
   * */
  @Override
  public boolean isCorrect() {
    return false;
  }
}
