package com.iluwatar.binding;

public class TextView implements Observer<String> {
  private String text;
  private ObservableProperty<String> boundProperty;

  @Override
  public void update(String newValue) {
    // Prevent unnecessary updates
    if (text != null && text.equals(newValue)) {
      return;
    }
    this.text = newValue;
    System.out.println("TextView updated: " + text);
  }

  @Override
  public void bind(ObservableProperty<String> observableProperty) {
    // Unbind from the current property if already bound
    if (this.boundProperty != null) {
      this.boundProperty.removeObserver(this);
    }
    this.boundProperty = observableProperty;
  }

  @Override
  public void unbind() {
    if (this.boundProperty != null) {
      this.boundProperty.removeObserver(this);
      this.boundProperty = null;
    }
  }

  public void setText(String newText) {
    if (boundProperty != null) {
      boundProperty.setValue(newText);
    }
    this.text = newText;
  }

  public String getText() {
    return text;
  }
}
