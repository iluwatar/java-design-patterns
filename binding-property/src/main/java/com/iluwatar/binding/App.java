package com.iluwatar.binding;

public class App {
  public static void main(String[] args) {
    ObservableProperty<String> observableProperty = new ObservableProperty<>("Initial Value");
    TextView textView = new TextView();

    // Bind TextView to ObservableProperty
    observableProperty.addObserver(textView);

    // Update ObservableProperty
    System.out.println("Setting value in ObservableProperty...");
    observableProperty.setValue("Hello, Design Patterns!");

    // Simulate user input through TextView
    System.out.println("User updates TextView...");
    textView.setText("User Input!");

    // Set another value in ObservableProperty to observe two-way binding
    System.out.println("Setting another value in ObservableProperty...");
    observableProperty.setValue("Two-Way Binding Works!");
  }
}
