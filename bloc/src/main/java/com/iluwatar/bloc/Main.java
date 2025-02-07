package com.iluwatar.bloc;

/**
 * The BLoC (Business Logic Component) pattern is a software design pattern primarily used
 * in Flutter applications. It facilitates the separation of business logic from UI code,
 * making the application more modular, testable, and scalable. The BLoC pattern uses streams
 * to manage the flow of data and state changes, allowing widgets to react to new states as
 * they arrive.
 * In the BLoC pattern, the application is divided into three key components:
    * - Input streams: Represent user interactions or external events fed into the BLoC.
    * - Business logic: Processes the input and determines the resulting state or actions.
    * - Output streams: Emit the updated state for the UI to consume.
    * The BLoC pattern is especially useful in reactive programming scenarios and aligns well with the declarative nature of Flutter.
 * By using this pattern, developers can ensure a clear separation of concerns, enhance reusability, and maintain consistent state management throughout the application.
 */

public class Main {

  /**
   * The entry point of the application. Initializes the GUI.
   *
   * @param args command-line arguments (not used in this example)
   */
  public static void main(String[] args) {
    BlocUi blocUi = new BlocUi();
    blocUi.createAndShowUi();
  }
}