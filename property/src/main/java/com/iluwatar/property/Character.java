/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.property;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents Character in game and his abilities (base stats).
 */
public class Character implements Prototype {

  /**
   * Enumeration of Character types
   */
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
      builder.append("Player: ").append(name).append('\n');
    }

    if (type != null) {
      builder.append("Character type: ").append(type.name()).append('\n');
    }

    builder.append("Stats:\n");
    for (Stats stat : Stats.values()) {
      Integer value = this.get(stat);
      if (value == null) {
        continue;
      }
      builder.append(" - ").append(stat.name()).append(':').append(value).append('\n');
    }
    return builder.toString();
  }

}
