---
title: "Bloc Pattern in Java: State Management Simplified"
shortTitle: Bloc
description: "Learn how the Bloc pattern helps manage state changes in Java applications. This guide covers dynamic listener management, real-world examples, and clean code practices for state management."
category: Structural
language: en
tag:
  - State Management
  - Event-driven
  - Listener Management
  - Object Composition
  - Dynamic Behavior
---

## Also known as

* Event-driven State Management
* State Listener Pattern

## Intent of the Bloc Pattern

The Bloc pattern manages the state of an object and allows for dynamically notifying interested listeners about state changes. It separates state management logic from the rest of the application, improving code organization and flexibility.

## Detailed explanation of the Bloc pattern with real-World examples

### Real-world example

> Consider a digital counter application where multiple parts of the UI need to be updated whenever the counter changes. For example, a label displaying the counter value and an activity log showing changes. Instead of directly modifying these UI components, the Bloc pattern manages the counter state and notifies all registered listeners about the state change. Listeners can dynamically subscribe or unsubscribe from receiving updates.

### In plain words

> The Bloc pattern manages a single state object and dynamically notifies registered listeners whenever the state changes.

### Wikipedia says

> While not a formalized "Gang of Four" design pattern, Bloc is widely used in state-driven applications. It centralizes state management and propagates state changes to registered observers, following principles of separation of concerns.

---

## Programmatic Example of the Bloc Pattern in Java

### **Core Components of the Bloc Pattern**

#### **1. State Object**

The `State` class holds the representation of the state of the application that will be passed to listeners whenever there is a change to do but in this example it's simplified to be a single value.

```java
package com.iluwatar.bloc;

import lombok.Getter;

@Getter
public class State {
    private final int value;

    public State(int value) {
        this.value = value;
    }

}
```
The `ListenerManager` interface manages the basic operations for the listeners and is implemented by bloc class
```java
import java.util.List;

public interface ListenerManager<T> {
    void addListener(StateListener<T> listener);
    void removeListener(StateListener<T> listener);
    List<StateListener<T>> getListeners();
}
```
The `StateListener` interface has a method that the listener needs to react to changes in the state and is used by bloC to notify listeners whenever there is an update to state.
```java
public interface StateListener<T> {
void onStateChange(T state);
}
```

The `Bloc` class holds the current state and manages logic of states and  notifies the list of listeners when states changes.
The `Bloc` class contains methods for listeners and states like emitstate which updates the currentstate and notifies listeners addlistener which adds new listener to the listeners list and notifies it with the currentstate removelistener which removes listener from the listeners list and increment which increases the state value by 1 which is like an update to the current state and notifies the listeners in listeners list with the new state which holds a value incremented by 1 and decrement functions which does the opposite of increment function and notifies listeners in listeners list.
```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bloc implements ListenerManager<State> {
private State currentState;
private final List<StateListener<State>> listeners = new ArrayList<>();

public Bloc() {
this.currentState = new State(0);
}

@Override
public void addListener(StateListener<State> listener) {
listeners.add(listener);
listener.onStateChange(currentState);
}

@Override
public void removeListener(StateListener<State> listener) {
listeners.remove(listener);
}

@Override
public List<StateListener<State>> getListeners() {
return Collections.unmodifiableList(listeners);
}

private void emitState(State newState) {
currentState = newState;
for (StateListener<State> listener : listeners) {
listener.onStateChange(currentState);
}
}

public void increment() {
emitState(new State(currentState.getValue() + 1));
}

public void decrement() {
emitState(new State(currentState.getValue() - 1));
}
}
```
The `main` class have a simple gui to try and test the bloc pattern components separately from the ui components.
the `main` class creates an instance of bloc then adds a listener to update the ui which resembles the counter and some buttons to change the states and toggle the listener dynamically 
```java
import javax.swing.*;
import java.awt.*;

public class Main {
public static void main(String[] args) {
Bloc bloc = new Bloc();
JFrame frame = new JFrame("Bloc Example");
frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
frame.setSize(400, 300);
JLabel counterLabel = new JLabel("Counter: 0", SwingConstants.CENTER);
counterLabel.setFont(new Font("Arial", Font.BOLD, 20));
JButton incrementButton = new JButton("Increment");
JButton decrementButton = new JButton("Decrement");
JButton toggleListenerButton = new JButton("Disable Listener");

    frame.setLayout(new BorderLayout());
    frame.add(counterLabel, BorderLayout.CENTER);
    frame.add(incrementButton, BorderLayout.NORTH);
    frame.add(decrementButton, BorderLayout.SOUTH);
    frame.add(toggleListenerButton, BorderLayout.EAST);

    StateListener<State> stateListener = state -> counterLabel.setText("Counter: " + state.getValue());

    bloc.addListener(stateListener);

    toggleListenerButton.addActionListener(e -> {
      if (bloc.getListeners().contains(stateListener)) {
        bloc.removeListener(stateListener);
        toggleListenerButton.setText("Enable Listener");
      } else {
        bloc.addListener(stateListener);
        toggleListenerButton.setText("Disable Listener");
      }
    });

    incrementButton.addActionListener(e -> bloc.increment());
    decrementButton.addActionListener(e -> bloc.decrement());

    frame.setVisible(true);
}
}
```
## Program Output

- **On Increment**  
  `Counter: 1`

- **On Decrement**  
  `Counter: 0`

- **Dynamic Listener Toggle**  
  - Listener disabled: Counter stops updating.  
  - Listener enabled: Counter updates again.

---

## When to Use the Bloc Pattern

Use the Bloc pattern when:

- You need a centralized system to manage state updates.
- You want to dynamically add/remove listeners without tight coupling.
- You are building an event-driven or state-driven system, such as UI frameworks.
---

## Real-World Applications of Bloc Pattern

- **UI State Management**: Reacting to button clicks, updating labels, and toggling views.
- **Event-driven Systems**: Handling multiple subscribers efficiently for state updates.
---

## Benefits and Trade-offs of Bloc Pattern

### Benefits:
- Clean separation of state management and UI logic.
- Flexibility to dynamically add/remove listeners.
- Centralized state propagation.

### Trade-offs:
- Adds some complexity with the listener management mechanism.
- May introduce performance concerns with excessive listeners.
- the bloc class handles too many methods which violates the single responsbility principle
---

## Related Patterns

- **Observer**: Bloc is a specialized implementation of the Observer pattern.
- **Mediator**: Bloc centralizes communication and state propagation.
- **cubit**: bloC is more general implementation than cubit
---

## References and Credits

- [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
- [Java Swing Documentation](https://docs.oracle.com/javase/tutorial/uiswing/)
- [Event-Driven Programming in Java](https://www.oracle.com/java/)
- [bloC archetecture](https://bloclibrary.dev/architecture/)
- [flutter bloC package](https://pub.dev/documentation/flutter_bloc/latest/)

