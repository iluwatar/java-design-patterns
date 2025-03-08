package com.iluwatar.binding;

public interface Observer<T> {
  void update(T newValue);

  void bind(ObservableProperty<T> observableProperty);

  void unbind();
}
