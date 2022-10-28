/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.specification.creature;

import com.iluwatar.specification.property.Color;
import com.iluwatar.specification.property.Mass;
import com.iluwatar.specification.property.Movement;
import com.iluwatar.specification.property.Size;

/**
 * Base class for concrete creatures.
 */
public abstract class AbstractCreature implements Creature {

  private final String name;
  private final Size size;
  private final Movement movement;
  private final Color color;
  private final Mass mass;

  /**
   * Constructor.
   */
  public AbstractCreature(String name, Size size, Movement movement, Color color, Mass mass) {
    this.name = name;
    this.size = size;
    this.movement = movement;
    this.color = color;
    this.mass = mass;
  }

  @Override
  public String toString() {
    return String.format("%s [size=%s, movement=%s, color=%s, mass=%s]",
        name, size, movement, color, mass);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Size getSize() {
    return size;
  }

  @Override
  public Movement getMovement() {
    return movement;
  }

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public Mass getMass() {
    return mass;
  }
}
