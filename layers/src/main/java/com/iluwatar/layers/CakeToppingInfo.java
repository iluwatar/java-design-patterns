package com.iluwatar.layers;

import java.util.Optional;

/**
 * 
 * DTO for cake toppings
 *
 */
public class CakeToppingInfo {

  public final Optional<Long> id;
  public final String name;
  public final int calories;

  public CakeToppingInfo(Long id, String name, int calories) {
    this.id = Optional.of(id);
    this.name = name;
    this.calories = calories;
  }

  public CakeToppingInfo(String name, int calories) {
    this.id = Optional.empty();
    this.name = name;
    this.calories = calories;
  }

  @Override
  public String toString() {
    return String.format("CakeToppingInfo id=%d name=%s calories=%d", id.orElse(-1L), name, calories);
  }
}
