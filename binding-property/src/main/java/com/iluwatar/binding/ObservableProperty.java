package com.iluwatar.binding;

import java.util.ArrayList;
import java.util.List;

public class ObservableProperty<T> {
  private T value;
  private final List<Observer<T>> observers = new ArrayList<>();
  private boolean isUpdating = false;

  public ObservableProperty(T initialValue) {
    this.value = initialValue;
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    if (isUpdating || this.value == value || (this.value != null && this.value.equals(value))) {
      return;
    }
    this.value = value;
    notifyObservers();
  }

  public void addObserver(Observer<T> observer) {
    observers.add(observer);
    observer.bind(this);
  }

  public void removeObserver(Observer<T> observer) {
    observers.remove(observer);
    observer.unbind();
  }

  private void notifyObservers() {
    for (Observer<T> observer : observers) {
      isUpdating = true;
      observer.update(value);
      isUpdating = false;
    }
  }
}
