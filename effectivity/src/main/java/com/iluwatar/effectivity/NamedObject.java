package com.iluwatar.effectivity;

/**
 * Simple class for sub-classes to have String names and toString implemented.
 */
public class NamedObject {
  private final String name;

  public NamedObject(String name) {
    this.name = name;
  }

  public String toString() {
    return name;
  }
}
