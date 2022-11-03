---
title: Finite State Machine
category: Behavioral
language: en
tags:
 - Gang of Four
---

## Intent

Allow an object to alter its behavior when its internal state changes. The object will appear to 
change its class.

## Explanation

Real-world example
> A car can change the state it's in and wildly change its behaviour. It can be either 
> moving, or have its engine cut. That state wildly changes how we can interact with it.

In plain words

> Finite state machine pattern allows an object to change its behavior. 

Wikipedia says

> It is an abstract machine that can be in exactly one of a finite number of states at 
> any given time. The FSM can change from one state to another in response to some inputs; 
> the change from one state to another is called a transition. An FSM is defined by a list 
> of its states, its initial state, and the inputs that trigger each transition.


**Programmatic Example**

Here is the Automaton Interface which will be used to define both FSM states and interaction with it.

```java
public interface AutomatonInterfaceI {

  void startNewQuery();

  void inputCharacter(char character);

  void logStreamNameCorrectness();

  boolean isCorrect();
}
```

Event defines events that can cause FSM to change its current state 

```java
public enum Event {
  CORRECT, INCORRECT, CLEAR
}
```

DataModel contains detailed information about FSMs state and is shared across all states
```java
public class DataModel {

  private String currentString = "";

  public void clear() {
    currentString = "";
  }

  public void addCharacter(char character) {
    currentString += character;
  }

  public String getCurrentString() {
    return currentString;
  }
}
```

EventSink is an interface used by FSMs states to inform it of events 
that can change its current state.

```java
public interface EventSink {

  void castEvent(Event event);
}

```


Here are the defined states.

```java
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

@Slf4j
public class CorrectNameState implements AutomatonInterfaceI {

  EventSink eventSink;
  DataModel model;

  public CorrectNameState(EventSink eventSink, DataModel model) {
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
    if ((int) 'a' <= (int) character && (int) character <= (int) 'z') {
      eventSink.castEvent(Event.CORRECT);
    } else {
      eventSink.castEvent(Event.INCORRECT);
    }

    model.addCharacter(character);
  }

  @Override
  public void logStreamNameCorrectness() {
    LOGGER.info("String '{}' is a correct name", model.getCurrentString());
  }

  @Override
  public boolean isCorrect() {
    return true;
  }
}

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


```

And finally RecognizeCorrectNameStateMachine declares our FSM 
and allows us to interact with it

```java
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
  public boolean isCorrect(){
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
```

Example how we can recognize if a name is valid with our FSM

```java
    var fsm = new RecognizeCorrectNameStateMachine();
    fsm.logStreamNameCorrectness();
    fsm.inputCharacter('J');
    fsm.logStreamNameCorrectness();
    fsm.inputCharacter('o');
    fsm.inputCharacter('h');
    fsm.inputCharacter('n');
    fsm.logStreamNameCorrectness();

    fsm.inputCharacter('1');
    fsm.logStreamNameCorrectness();
```

Program output:

```java
    String '' is not a correct name -> it's empty
    String 'J' is not a correct name -> too short
    String 'John' is a correct name
    String 'John1' is not a correct name -> incorrect character
```

## Class diagram

![alt text](./etc/finite-state-machine.urm.png "Finite State Machine")

## Applicability

Use the Finite State Machine pattern in either of the following cases

* An object's behavior depends on its state, and it must change its behavior at run-time depending on that state
* Operations have large, multipart conditional statements that depend on the object's state.
* The states have to be reused.

## Credits
* [State Machine Design Pattern](https://community.wvu.edu/~hhammar/rts/adv%20rts/statecharts%20patterns%20papers%20and%20%20examples/paper%20on%20state%20pattern%20B31-full.pdf)
* [FSM in Erlang - very nice explanation](https://erlang.org/documentation/doc-4.8.2/doc/design_principles/fsm.html)
* [The implementation of FSM in Akka](https://doc.akka.io/docs/akka/snapshot/fsm.html?language=scala)
