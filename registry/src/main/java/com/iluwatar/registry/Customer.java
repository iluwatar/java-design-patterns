package com.iluwatar.registry;

public class Customer {

  private final String id;
  private final String name;

  public Customer(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "Customer{"
        + "id='" + id + '\''
        + ", name='" + name + '\''
        + '}';
  }
}
