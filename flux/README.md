---
title: Flux
category: Architectural
language: en
tag:
    - Client-server
    - Decoupling
    - Event-driven
    - Publish/subscribe
    - Reactive
---

## Intent

The Flux design pattern is intended to manage the flow of data in applications, particularly client-side web applications, by enforcing a unidirectional data flow. It aims to simplify the management of complex data interactions and promote a more predictable state behavior across components.

## Explanation

Real world example

> Consider a busy restaurant kitchen as an analogy for the Flux design pattern. In this scenario, the kitchen operates based on customer orders (actions) received and processed through a single point of control (the dispatcher), which could be represented by the head chef. When an order arrives, the head chef assigns specific tasks to various sections of the kitchen (stores), such as the grill, the salad station, or the dessert team. Each section updates the progress of their tasks (state changes) back to the head chef, who ensures that all parts of the order are coordinated and completed in a synchronized manner before the dishes are sent out to the customer (the view).

In plain words

> The Flux design pattern manages data flow in applications through a unidirectional architecture, coordinating actions, dispatchers, stores, and views to ensure stable and predictable state management.

Wikipedia says

> To support React's concept of unidirectional data flow (which might be contrasted with AngularJS's bidirectional flow), the Flux architecture was developed as an alternative to the popular model–view–controller architecture. Flux features actions which are sent through a central dispatcher to a store, and changes to the store are propagated back to the view.

**Programmatic Example**

The Flux design pattern is a pattern that Facebook uses for building client-side web applications. It advocates for a unidirectional data flow. When a user interacts with a view, the view propagates an action through a central dispatcher, to the various stores that hold the application's data and business logic, which updates all the views that are affected.

In the provided code, we can see an example of the Flux pattern in the `App` and `MenuStore` classes.

The `App` class is the entry point of the application. It initializes and wires the system, registers the stores with the dispatcher, registers the views with the stores, and triggers the initial rendering of the views. When a menu item is clicked, it triggers events through the dispatcher.

```java
public class App {

  public static void main(String[] args) {

    var menuStore = new MenuStore();
    Dispatcher.getInstance().registerStore(menuStore);
    var contentStore = new ContentStore();
    Dispatcher.getInstance().registerStore(contentStore);
    var menuView = new MenuView();
    menuStore.registerView(menuView);
    var contentView = new ContentView();
    contentStore.registerView(contentView);

    menuView.render();
    contentView.render();

    menuView.itemClicked(MenuItem.COMPANY);
  }
}
```

The `MenuStore` class is a concrete store that holds the state of the menu. It updates its state and notifies the views when it receives an action from the dispatcher.

```java
public class MenuStore extends Store {

  @Getter
  private MenuItem selected = MenuItem.HOME;

  @Override
  public void onAction(Action action) {
    if (action.getType().equals(ActionType.MENU_ITEM_SELECTED)) {
      var menuAction = (MenuAction) action;
      selected = menuAction.getMenuItem();
      notifyChange();
    }
  }
}
```

In this example, when a menu item is clicked, the `MenuView` triggers a `MENU_ITEM_SELECTED` action. The `Dispatcher` forwards this action to all registered stores. The `MenuStore` handles this action by updating its state and notifying its views, causing them to rerender with the new state.

This is a basic example of the Flux pattern, where actions are dispatched from the views, handled by the stores, and cause the views to update.

## Class diagram

![Flux](./etc/flux.png "Flux")

## Applicability

Flux is applicable in developing client-side applications, where maintaining consistent data across various components and managing complex state interactions are critical. It is especially suited for applications with dynamic user interfaces that react to frequent data updates.

## Known Uses

* Facebook extensively uses Flux in conjunction with React to build robust, scalable user interfaces that can handle complex data updates efficiently.
* Many modern web applications adopt Flux or its variations (like Redux) to manage state in environments that demand high responsiveness and predictability.

## Consequences

Benefits:

* Ensures a unidirectional data flow that simplifies debugging and testing.
* Enhances consistency across the application by centralizing the application state.
* Improves the predictability of data flow and interaction in large applications.

Trade-offs:

* Can introduce boilerplate and complexity in smaller applications.
* May require a learning curve to understand the pattern's architecture and its implementation nuances.

## Related Patterns

* [Observer](https://java-design-patterns.com/patterns/observer/): Flux's dispatcher component acts similarly to an observer, managing notifications about data changes to various stores.
* [Singleton](https://java-design-patterns.com/patterns/singleton/): Typically, the dispatcher in Flux is implemented as a singleton.
* https://java-design-patterns.com/patterns/mediator/: Flux can be considered a variation of the mediator pattern where the dispatcher mediates the flow of data and ensures components do not update the state directly.

## Credits

* [Flux - Application architecture for building user interfaces](http://facebook.github.io/flux/)
* [Learning React: Modern Patterns for Developing React Apps](https://amzn.to/3Qdn9Pg)
* [Pro React](https://amzn.to/3xNRttK)
