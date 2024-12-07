package com.iluwatar.bloc;
import java.util.List;

public interface ListenerManager<T> {
  void addListener(StateListener<T> listener);
  void removeListener(StateListener<T> listener);
  List<StateListener<T>> getListeners();
}

