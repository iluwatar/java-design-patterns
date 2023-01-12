package com.iluwatar.finite.state.machine;

import com.iluwatar.finite.state.machine.states.CorrectFirstLetterState;
import com.iluwatar.finite.state.machine.states.CorrectNameState;
import com.iluwatar.finite.state.machine.states.EmptyState;
import com.iluwatar.finite.state.machine.states.IncorrectNameState;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;


/**
 * FSM, handles edges between states.
 */
@Slf4j
public class RecognizeCorrectNameStateMachine
    implements AutomatonInterfaceI, EventSink {

  /**
   * Incorrect state id in state list.
   */
  private static final int INCORRECT_STATE_ID = 3;

  /**
   * List of FSM graph nodes (states).
   */
  private final List<AutomatonInterfaceI> states = new ArrayList<>();

  /**
   * Representation of FSM graph.
   */
  private final Map<Integer, Map<Event, Integer>> edges = new HashMap<>();

  /**
   * FSM model (current name).
   */
  private final DataModel model = new DataModel();

  /**
   * Current FSM state id in state list.
   */
  private int currentStateId = 0;

  /**
   * Constructor.
   */
  RecognizeCorrectNameStateMachine() {
    addAllStates();
    addAllEdges();
  }

  /**
   * Restarts the FSM.
   */
  @Override
  public void startNewQuery() {
    var state = states.get(currentStateId);
    state.startNewQuery();
  }

  /**
   * Input new character into FSM.
   *
   * @param character -> value of inputted character.
   */
  @Override
  public void inputCharacter(final Character character) {
    var state = states.get(currentStateId);
    state.inputCharacter(character);
  }

  /**
   * Log if a name inputted up to now is correct.
   */
  @Override
  public void logStreamNameCorrectness() {
    var state = states.get(currentStateId);
    state.logStreamNameCorrectness();
  }

  /**
   * Is a name inputted up to now correct.
   *
   * @return is name created from inputs correct.
   */
  @Override
  public boolean isCorrect() {
    var state = states.get(currentStateId);
    return state.isCorrect();
  }

  /**
   * Used to inform FSM about changes to state.
   *
   * @param event -> affects FSM state
   */
  @Override
  public void castEvent(final Event event) {
    try {
      currentStateId = edges.get(currentStateId).get(event);

    } catch (NullPointerException e) {
      LOGGER.info("Appropriate edge not found");
    } catch (ClassCastException e) {
      LOGGER.info("Wrong key type");
    }
  }

  /**
   * Initialize states list.
   */
  private void addAllStates() {
    states.add(new EmptyState(this, this.model));
    states.add(new CorrectFirstLetterState(this, this.model));
    states.add(new CorrectNameState(this, this.model));
    states.add(new IncorrectNameState(this, this.model));
  }

  /**
   * Initialize edges in FSM graph.
   */
  private void addAllEdges() {
    IntStream.range(0, states.size())
        .forEach(index -> addEdge(index, Event.CLEAR, 0));

    IntStream.range(0, states.size())
        .forEach(index -> addEdge(index, Event.INCORRECT, INCORRECT_STATE_ID));

    addEdge(0, Event.CORRECT, 1);
    addEdge(1, Event.CORRECT, 2);
    addEdge(2, Event.CORRECT, 2);
  }

  /**
   * Add edge in FSM graph.
   *
   * @param event            -> causes movement through edge
   * @param startingStateId  -> id of starting state in state list
   * @param resultingStateId -> id of resulting state in state list
   */
  private void addEdge(
      final Integer startingStateId,
      final Event event,
      final Integer resultingStateId
  ) {
    if (!edges.containsKey(startingStateId)) {
      edges.put(startingStateId, new HashMap<>());
    }

    edges.get(startingStateId).put(event, resultingStateId);
  }
}
