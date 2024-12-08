package com.iluwatar;

/**
 * Resource class represents a resource with a unique identifier.
 * This is used as the object that will be locked and processed.
 */
public class Resource {
  private final String id;  // Unique identifier for the resource

  /**
   * Constructs a Resource with the specified unique identifier.
   *
   * @param id the unique identifier for the resource
   */
  public Resource(String id) {
    this.id = id;
  }

  /**
   * Retrieves the unique identifier for the resource.
   *
   * @return the resource's unique identifier
   */
  public String getId() {
    return id;
  }
}
