package com.iluwatar.binding;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TextViewTest {

  @Test
  public void testTextViewUpdatesFromObservable() {
    ObservableProperty<String> observableProperty = new ObservableProperty<>("Initial");
    TextView textView = new TextView();

    // Bind TextView to ObservableProperty
    observableProperty.addObserver(textView);

    // Change observable value
    observableProperty.setValue("New Value");

    assertEquals("New Value", textView.getText());
  }

  @Test
  public void testTextViewUpdatesObservable() {
    ObservableProperty<String> observableProperty = new ObservableProperty<>("Initial");
    TextView textView = new TextView();

    // Bind TextView to ObservableProperty
    observableProperty.addObserver(textView);

    // Simulate user updating TextView
    textView.setText("User Input");

    assertEquals("User Input", observableProperty.getValue());
  }
}
