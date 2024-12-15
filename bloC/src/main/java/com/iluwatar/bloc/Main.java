package com.iluwatar.bloc;

/**
 * The Main class demonstrates the use of the Bloc pattern in a simple GUI application.
 * It initializes the UI and sets up actions for the buttons and listener management.
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