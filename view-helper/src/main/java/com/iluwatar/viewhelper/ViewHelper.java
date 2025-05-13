package com.iluwatar.viewhelper;

public interface ViewHelper<M, V> {
  V prepare(M source);
}
