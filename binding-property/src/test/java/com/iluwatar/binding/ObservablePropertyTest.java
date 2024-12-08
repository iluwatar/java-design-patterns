package com.iluwatar.binding;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ObservablePropertyTest {

  @Test
  public void testNotifyObservers() {
    ObservableProperty<String> observableProperty = new ObservableProperty<>("Initial");
    TestObserver testObserver = new TestObserver();
    observableProperty.addObserver(testObserver);

    observableProperty.setValue("Updated");

    assertEquals("Updated", testObserver.getReceivedValue());
  }

  private static class TestObserver implements Observer<String> {
    private String receivedValue;

    @Override
    public void update(String newValue) {
      receivedValue = newValue;
    }

    @Override
    public void bind(ObservableProperty<String> observableProperty) {
      // No binding logic needed for this test.
    }

    @Override
    public void unbind() {
      // No unbinding logic needed for this test.
    }

    public String getReceivedValue() {
      return receivedValue;
    }
  }
}
