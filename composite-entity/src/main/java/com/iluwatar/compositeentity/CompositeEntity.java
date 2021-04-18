package com.iluwatar.compositeentity;

/**
 * Composite entity is the coarse-grained entity bean which may be the coarse-grained object, or may
 * contain a reference to the coarse-grained object.
 */

public class CompositeEntity {

  private final ConsoleCoarseGrainedObject console = new ConsoleCoarseGrainedObject();

  public void setData(String message, String signal) {
    console.setData(message, signal);
  }

  public String[] getData() {
    return console.getData();
  }

  public void init() {
    console.init();
  }
}