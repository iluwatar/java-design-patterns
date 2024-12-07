package com.iluwatar.bloc;

public interface StateListener<T>
{
  void onStateChange(T state);
}
