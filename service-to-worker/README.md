---
title: Service to Worker
category: Architectural
language: en
tag:
- Decoupling
---

## Intent

Combine a controller and dispatcher with views and helpers to handle client requests and prepare a dynamic presentation as the response. Controllers delegate content retrieval to helpers, which manage the population of the intermediate model for the view. A dispatcher is responsible for view management and navigation and can be encapsulated either within a controller or a separate component.

## Explanation

Real world example

> In the classic MVC pattern, M refers to the business model, V refers to the user interface, and C is the controller. The purpose of using MVC is to separate the implementation code of M and V, so that the same program can use different forms of expression. In the Service to Worker pattern, the C directly controls the display of the V and can receive commands to control the dispatcher indirectly. The dispatcher stores different commands that can be used to modify the `model` with `action`s or to modify the display in the `view`s.

In plain words

> Service to Worker Pattern uses Dispatcher to combine the controller and the view to handle client requests and prepare a dynamic presentation as the response.

**Programmatic Example**

We modified this pattern based on a classic design patterns [Model View Controller Pattern](https://github.com/iluwatar/java-design-patterns/tree/master/model-view-controller) as the Class Diagram and two main classes `Dispatcher` and `Action` have been added.

The Dispatcher, which encapsulates worker and view selection based on request information and/or an internal navigation model.

```java
public class Dispatcher {

  private final GiantView giantView;
  private final List<Action> actions;

  /**
   * Instantiates a new Dispatcher.
   *
   * @param giantView the giant view
   */
  public Dispatcher(GiantView giantView) {
    this.giantView = giantView;
    this.actions = new ArrayList<>();
  }

  /**
   * Add an action.
   *
   * @param action the action
   */
  void addAction(Action action) {
    actions.add(action);
  }

  /**
   * Perform an action.
   *
   * @param s           the s
   * @param actionIndex the action index
   */
  public void performAction(Command s, int actionIndex) {
    actions.get(actionIndex).updateModel(s);
  }

  /**
   * Update view.
   *
   * @param giantModel the giant model
   */
  public void updateView(GiantModel giantModel) {
    giantView.displayGiant(giantModel);
  }
}
```

The Action (Worker), which can process user input and perform a specific update on the model.

```java
public class Action {

  private final GiantModel giant;

  /**
   * Instantiates a new Action.
   *
   * @param giant the giant
   */
  public Action(GiantModel giant) {
    this.giant = giant;
  }

  /**
   * Update model based on command.
   *
   * @param command the command
   */
  public void updateModel(Command command) {
    setFatigue(command.getFatigue());
    setHealth(command.getHealth());
    setNourishment(command.getNourishment());
  }
}
```

Therefore, this example leverages the Service to Worker pattern to increase functionality cohesion and improve the business logic.


## Class diagram
![alt text](./etc/service-to-worker.png "Service to Worker")

## Applicability
- For the business logic of web development, the responsibility of a dispatcher component may be to translate the logical name login into the resource name of an appropriate view, such as login.jsp, and dispatch to that view. To accomplish this translation, the dispatcher may access resources such as an XML configuration file that specifies the appropriate view to display.

## Credits
* [J2EE Design Patterns](https://www.oreilly.com/library/view/j2ee-design-patterns/0596004273/re05.html)
* [Core J2EE Patterns](http://corej2eepatterns.com/Patterns/ServiceToWorker.htm)
