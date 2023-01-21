package com.iluwatar.finite.state.machine.states;

import com.iluwatar.finite.state.machine.AutomatonInterface;
import com.iluwatar.finite.state.machine.DataModel;
import com.iluwatar.finite.state.machine.Event;
import com.iluwatar.finite.state.machine.EventSink;
import lombok.extern.slf4j.Slf4j;

/**
 * State when first letter was correct.
 */
@Slf4j
public class CorrectFirstLetterState implements AutomatonInterface {

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
  public CorrectFirstLetterState(
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
    // check if character is a lowercase letter
    var event =
        Character.isLowerCase(character) ? Event.CORRECT : Event.INCORRECT;
    eventSink.castEvent(event);
    model.addCharacter(character);
  }

  /**
   * Log if a name inputted up to now is correct.
   * */
  @Override
  public void logStreamNameCorrectness() {
    LOGGER.info(
        "String '{}' is not a correct name -> too short",
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
