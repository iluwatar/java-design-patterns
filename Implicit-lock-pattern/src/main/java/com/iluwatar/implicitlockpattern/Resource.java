package com.iluwatar.implicitlockpattern;

/**
 * Resource class represents a resource with a unique identifier.
 * This is used as the object that will be locked and processed.
 */
public class Resource {
  private final String id;  // Unique identifier for the resource

  // Constructor to initialize the resource with an ID
  public Resource(String id) {
    this.id = id;
  }

  // Getter for resource ID
  public String getId() {
    return id;
  }
}
