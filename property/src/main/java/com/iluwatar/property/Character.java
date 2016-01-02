package com.iluwatar.property;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents Character in game and his abilities (base stats).
 */
public class Character implements Prototype {

  public enum Type {
    WARRIOR, MAGE, ROGUE
  }

  private final Prototype prototype;
  private final Map<Stats, Integer> properties = new HashMap<>();

  private String name;
  private Type type;

  /**
   * Constructor
   */
  public Character() {
    this.prototype = new Prototype() { // Null-value object
          @Override
          public Integer get(Stats stat) {
            return null;
          }

          @Override
          public boolean has(Stats stat) {
            return false;
          }

          @Override
          public void set(Stats stat, Integer val) {}

          @Override
          public void remove(Stats stat) {}
        };
  }

  public Character(Type type, Prototype prototype) {
    this.type = type;
    this.prototype = prototype;
  }

  /**
   * Constructor
   */
  public Character(String name, Character prototype) {
    this.name = name;
    this.type = prototype.type;
    this.prototype = prototype;
  }

  public String name() {
    return name;
  }

  public Type type() {
    return type;
  }

  @Override
  public Integer get(Stats stat) {
    boolean containsValue = properties.containsKey(stat);
    if (containsValue) {
      return properties.get(stat);
    } else {
      return prototype.get(stat);
    }
  }

  @Override
  public boolean has(Stats stat) {
    return get(stat) != null;
  }

  @Override
  public void set(Stats stat, Integer val) {
    properties.put(stat, val);
  }

  @Override
  public void remove(Stats stat) {
    properties.put(stat, null);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (name != null) {
      builder.append("Player: ").append(name).append("\n");
    }

    if (type != null) {
      builder.append("Character type: ").append(type.name()).append("\n");
    }

    builder.append("Stats:\n");
    for (Stats stat : Stats.values()) {
      Integer value = this.get(stat);
      if (value == null) {
        continue;
      }
      builder.append(" - ").append(stat.name()).append(":").append(value).append("\n");
    }
    return builder.toString();
  }

}
