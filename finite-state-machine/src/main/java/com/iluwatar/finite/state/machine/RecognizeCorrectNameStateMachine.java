package com.iluwatar.finite.state.machine;

import com.iluwatar.finite.state.machine.states.CorrectFirstLetterState;
import com.iluwatar.finite.state.machine.states.CorrectNameState;
import com.iluwatar.finite.state.machine.states.EmptyState;
import com.iluwatar.finite.state.machine.states.IncorrectNameState;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;


/**
 * FSM, handles edges between states.
 */
@Slf4j
public class RecognizeCorrectNameStateMachine implements AutomatonInterfaceI, EventSink {

  private final List<AutomatonInterfaceI> states = new ArrayList<>();
  private final Map<Integer, Map<Event, Integer>> edges = new HashMap<>();
  private final DataModel model = new DataModel();
  private int currentStateId = 0;

  RecognizeCorrectNameStateMachine() {
    addAllStates();
    addAllEdges();
  }

  @Override
  public void startNewQuery() {
    AutomatonInterfaceI state = states.get(currentStateId);
    state.startNewQuery();
  }

  @Override
  public void inputCharacter(char character) {
    AutomatonInterfaceI state = states.get(currentStateId);
    state.inputCharacter(character);
  }

  @Override
  public void logStreamNameCorrectness() {
    AutomatonInterfaceI state = states.get(currentStateId);
    state.logStreamNameCorrectness();
  }

  @Override
  public boolean isCorrect() {
    AutomatonInterfaceI state = states.get(currentStateId);
    return state.isCorrect();
  }

  @Override
  public void castEvent(Event event) {
    try {
      currentStateId = edges.get(currentStateId).get(event);

    } catch (Exception e) {
      LOGGER.info("Appropriate edge not found");
    }
  }

  private void addAllStates() {
    states.add(new EmptyState(this, this.model));
    states.add(new CorrectFirstLetterState(this, this.model));
    states.add(new CorrectNameState(this, this.model));
    states.add(new IncorrectNameState(this, this.model));
  }

  private void addAllEdges() {
    for (int i = 0; i < states.size(); i++) {
      addEdge(i, Event.CLEAR, 0);
    }

    for (int i = 0; i < states.size(); i++) {
      addEdge(i, Event.INCORRECT, 3);
    }

    addEdge(0, Event.CORRECT, 1);
    addEdge(1, Event.CORRECT, 2);
    addEdge(2, Event.CORRECT, 2);
  }

  private void addEdge(Integer startingStateId, Event event, Integer resultingStateId) {
    if (!edges.containsKey(startingStateId)) {
      edges.put(startingStateId, new HashMap<>());
    }

    edges.get(startingStateId).put(event, resultingStateId);
  }
}

