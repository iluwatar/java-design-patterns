---
title: Model-View-Intent
category: Architectural
language: en
tags:
 - Decoupling
 - Encapsulation
---

## Intent
MVI is a derivation of the original MVC architectural pattern. Instead of working with a 
proactive controller MVI works with the reactive component called intent: it's a component
which translates user input events into model updates.

## Explanation

> MVI is a Reactive Architecture Pattern which is short for Model -View-Intent. 
It introduces two new concepts: the intent and the state. 
UI might have different states — Loading State, Fetch Data State, Error State, 
and user events are submitted in the form of an Intent.

* [Stateful Android Apps With MVI (MODEL — VIEW — INTENT)](https://medium.com/huawei-developers/stateful-android-apps-with-mvi-architecture-model-view-intent-d106b09bd967)

## Class diagram
![alt text](./etc/model-view-intent.png "Model-View-Intent")

**Programmatic Example**

CalculatorAction defines our Intent in MVI for user interactions. It has to be an interface 
instead of enum, so that we can pass parameters to certain children.
```java
public interface CalculatorAction {

  /**
   * Makes identifying action trivial.
   *
   * @return subclass tag.
   * */
  String tag();
}
```

CalculatorModel defines the state of our view or in out case, variable and output of the calculator.
```java
@Data
public class CalculatorModel {

  /**
   * Current calculator variable used for operations.
   **/
  final Double variable;

  /**
   * Current calculator output -> is affected by operations.
   **/
  final Double output;
}
```


CalculatorView will serve as a mock view which will expose potential user actions and 
display calculator state -> output and current variable
```java
@Slf4j
public class CalculatorView {

  /**
   * View model param handling the operations.
   * */
  private final CalculatorViewModel viewModel = new CalculatorViewModel();

  /**
   * Display current view model output with logger.
   * */
  void displayTotal() {
    LOGGER.info(
        "Total value = {}",
        viewModel.getCalculatorModel().getOutput().toString()
    );
  }

  /**
   * Handle addition action.
   * */
  void add() {
    viewModel.handleAction(new AdditionCalculatorAction());
  }

  /**
   * Handle subtraction action.
   * */
  void subtract() {
    viewModel.handleAction(new SubtractionCalculatorAction());
  }

  /**
   * Handle multiplication action.
   * */
  void multiply() {
    viewModel.handleAction(new MultiplicationCalculatorAction());
  }

  /**
   * Handle division action.
   * */
  void divide() {
    viewModel.handleAction(new DivisionCalculatorAction());
  }

  /**
   * Handle setting new variable action.
   *
   * @param value -> new calculator variable.
   * */
  void setVariable(final Double value) {
    viewModel.handleAction(new SetVariableCalculatorAction(value));
  }
}
```

Finally, ViewModel handles the exposed events with the handleAction(event) method, which delegates
the specific handling to private methods. Initially calculator output and variable are equal to 0.
```java
public final class CalculatorViewModel {

  /**
   * Current calculator model (can be changed).
   */
  private CalculatorModel model =
      new CalculatorModel(0.0, 0.0);

  /**
   * Handle calculator action.
   *
   * @param action -> transforms calculator model.
   */
  void handleAction(final CalculatorAction action) {
    switch (action.tag()) {
      case AdditionCalculatorAction.TAG -> add();
      case SubtractionCalculatorAction.TAG -> subtract();
      case MultiplicationCalculatorAction.TAG -> multiply();
      case DivisionCalculatorAction.TAG -> divide();
      case SetVariableCalculatorAction.TAG -> {
        SetVariableCalculatorAction setVariableAction =
            (SetVariableCalculatorAction) action;
        setVariable(setVariableAction.getVariable());
      }
      default -> {
      }
    }
  }

  /**
   * Getter.
   *
   * @return current calculator model.
   */
  public CalculatorModel getCalculatorModel() {
    return model;
  }

  /**
   * Set new calculator model variable.
   *
   * @param variable -> value of new calculator model variable.
   */
  private void setVariable(final Double variable) {
    model = new CalculatorModel(
        variable,
        model.getOutput()
    );
  }

  /**
   * Add variable to model output.
   */
  private void add() {
    model = new CalculatorModel(
        model.getVariable(),
        model.getOutput() + model.getVariable()
    );
  }

  /**
   * Subtract variable from model output.
   */
  private void subtract() {
    model = new CalculatorModel(
        model.getVariable(),
        model.getOutput() - model.getVariable()
    );
  }

  /**
   * Multiply model output by variable.
   */
  private void multiply() {
    model = new CalculatorModel(
        model.getVariable(),
        model.getOutput() * model.getVariable()
    );
  }

  /**
   * Divide model output by variable.
   */
  private void divide() {
    model = new CalculatorModel(
        model.getVariable(),
        model.getOutput() / model.getVariable()
    );
  }
}
```

## Applicability
Use the Model-View-Intent pattern when

* You want to clearly separate the domain data from its user interface representation
* You want to minimise the public api of the view model

## Known uses
A popular architecture pattern in android. The small public api is particularly powerful 
with the new Android Compose UI, as you can pass a single method (viewModel::handleEvent) 
to all Composables(parts of UI) as a callback for user input event.

## Consequences
Pros:
* Encapsulation
* Separation of concerns
* Clear list of all possible user events

Cons:
* More boilerplate code compared to alternatives (especially in Java)

## Related patterns
MVC:
* [Trygve Reenskaug - Model-view-controller](http://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)

## Credits

* [Model View Intent: a new Android Architecture Pattern](https://apiumacademy.com/blog/model-view-intent-pattern/)
* [MVI Architecture for Android Tutorial](https://www.kodeco.com/817602-mvi-architecture-for-android-tutorial-getting-started)

