package com.iluwatar.compositeentity;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * A coarse-grained object is an object with its own life cycle manages its own relationships to
 * other objects. It can be an object contained in the composite entity, or, composite entity itself
 * can be the coarse-grained object which holds dependent objects.
 */

public abstract class CoarseGrainedObject<T> {

  DependentObject<T>[] dependentObjects;

  public void setData(T... data) {
    IntStream.range(0, data.length).forEach(i -> dependentObjects[i].setData(data[i]));
  }

  public T[] getData() {
    return (T[]) Arrays.stream(dependentObjects).map(DependentObject::getData).toArray();
  }
}
